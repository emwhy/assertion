package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

final class JsonHelper {
    /**
     * Load JSON file.
     * @param jsonUrl URL to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSONObject or JSONArray
     */
    static Object loadJsonFile(@Nullable URL jsonUrl, @NonNull String... formatterArgs) {
        try {
            if (jsonUrl == null) {
                throw new JSONException("Cannot find JSON file.");
            }
            return loadJsonFile(Paths.get(jsonUrl.toURI()), formatterArgs);
        } catch (URISyntaxException ex) {
            throw new JSONException("Error while trying to read JSON file.", ex);
        }
    }

    /**
     * Load JSON file.
     * @param jsonPath Path to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSONObject or JSONArray
     */
    static Object loadJsonFile(@NonNull Path jsonPath, @NonNull String... formatterArgs) {
        return loadJsonFile(jsonPath.toFile(), formatterArgs);
    }

    /**
     * Load JSON file.
     * @param jsonFile JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSONObject or JSONArray
     */
    static Object loadJsonFile(@NonNull File jsonFile, @NonNull String... formatterArgs) {
        try {
            if (jsonFile.exists()) {
                final String jsonContent = Files.readString(jsonFile.toPath()).trim();

                if (jsonContent.isEmpty()) {
                    throw new JSONException("JSON file is empty: " + jsonFile.getAbsolutePath());
                } else if (isJson(jsonContent)) {
                    return new JSONObject(String.format(jsonContent, (Object[]) formatterArgs));
                } else if (isJsonArray(jsonContent)) {
                    return new JSONArray(String.format(jsonContent, (Object[]) formatterArgs));
                } else {
                    throw new JSONException("The file is not in JSON format: " + jsonFile.getAbsolutePath());
                }
            } else {
                throw new JSONException("Cannot find JSON file: " + jsonFile.getAbsolutePath());
            }
        } catch (IOException ex) {
            throw new JSONException("Cannot read JSON file: " + jsonFile.getAbsolutePath(), ex);
        }
    }

    /**
     * Check if the provided text is a JSON object.
     * @param text the string to check
     * @return {@code true} if the trimmed string starts with '{'
     */
    static boolean isJson(@NonNull String text) {
        return text.trim().startsWith("{");
    }

    /**
     * Check if the provided text is a JSON array.
     * @param text the string to check
     * @return {@code true} if the trimmed string starts with '['
     */
    static boolean isJsonArray(@NonNull String text) {
        return text.trim().startsWith("[");
    }

    /**
     * Identify the {@link JsonType} of the provided JSON text.
     * @param jsonText the string to analyze
     * @return {@link JsonType#Array} or {@link JsonType#Object}
     * @throws JSONException if the text does not start with valid JSON delimiters
     */
    static JsonType jsonType(@NonNull String jsonText) {
        if (jsonText.trim().startsWith("[")) {
            return JsonType.Array;
        } else if (jsonText.trim().startsWith("{")) {
            return JsonType.Object;
        } else {
            throw new JSONException("Invalid JSON text: " + jsonText);
        }
    }

    /**
     * Search for a specific JSON structure within a larger JSON object or array.
     * @param json the actual JSON object or array to search within
     * @param expectedJson the JSON structure to find
     * @param excludedNodes list of JSON pointers to ignore during the search
     * @param ignoreCase whether to ignore case when comparing string values
     * @return {@code true} if the expected JSON is found within the actual JSON
     */
    static boolean findJson(@NonNull Object json, @NonNull Object expectedJson, List<String> excludedNodes, boolean ignoreCase) {
        final Map<String, Object> actual = getPointerValueMap(removeByJsonPointer(json, excludedNodes));
        final Object containedJson = removeByJsonPointer(expectedJson, excludedNodes);

        for (Object value : actual.values()) {
            if (value instanceof JSONObject || value instanceof JSONArray) {
                if (matchJson(value, containedJson, List.of(), ignoreCase).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Compare two JSON structures for equality, excluding specified nodes.
     * @param json the actual JSON object or array
     * @param expectedJson the expected JSON object or array
     * @param excludedNodes list of JSON pointers to exclude from the comparison
     * @param ignoreCase whether to ignore case during string value comparisons
     * @return an empty string if they match, or a descriptive error message explaining the mismatch
     * @throws AssertionError if the input parameters are not JSON objects or arrays
     */
    static String matchJson(@NonNull Object json, @NonNull Object expectedJson, List<String> excludedNodes, boolean ignoreCase) {
        if ((json instanceof JSONObject || json instanceof JSONArray) && (expectedJson instanceof JSONObject || expectedJson instanceof JSONArray)) {
            final Map<String, Object> actual = getPointerValueMap(removeByJsonPointer(json, excludedNodes));
            final Map<String, Object> expected = getPointerValueMap(removeByJsonPointer(expectedJson, excludedNodes));

            validateExcludedJsonPointerFragments(json, excludedNodes);

            if (actual.size() > expected.size()) {
                return "Expected JSON size is smaller than the actual JSON size.";
            } else if (actual.size() < expected.size()) {
                return "Expected JSON size is larger than the actual JSON size.";
            } else {
                for (Map.Entry<String, Object> entry : actual.entrySet()) {
                    Object expectedValue = expected.get(entry.getKey());

                    if (!(entry.getValue() instanceof JSONObject) && !(entry.getValue() instanceof JSONArray)) {
                        if (expectedValue == null) {
                            return "Expected name is missing: " + entry.getKey();
                        } else if (entry.getValue() instanceof String && ignoreCase) {
                            if (!expectedValue.toString().equalsIgnoreCase(entry.getValue().toString())) {
                                return "Expected value at '" + entry.getKey() + "' does not case-insensitively match actual value. actual: '" + entry.getValue() + "', expected: '" + expectedValue + "'";
                            }
                        } else if (!entry.getValue().equals(expectedValue)) {
                            return "Expected value at '" + entry.getKey() + "' does not match actual value. actual: '" + entry.getValue() + "', expected: '" + expectedValue + "'";
                        }
                    }
                }
            }
            return "";
        } else {
            throw new AssertionError("Invalid parameter type.");
        }
    }

    /**
     * Validate that all specified exclusion fragments exist within the JSON structure.
     * @param obj the JSON object or array to validate against
     * @param excludeJsonPointerFragments the fragments to look for
     * @throws AssertionError if a fragment cannot be found or the object type is invalid
     */
    private static void validateExcludedJsonPointerFragments(@NonNull Object obj, @NonNull List<String> excludeJsonPointerFragments) {
        if (obj instanceof JSONObject || obj instanceof JSONArray) {
            final Map<String, Object> map = getPointerValueMap(obj);

            for (String excludedJsonPointerFragment : excludeJsonPointerFragments) {
                if (map.keySet().stream().noneMatch(key -> key.contains(excludedJsonPointerFragment))) {
                    throw new AssertionError("Cannot find specified 'excluding' JSON pointer node in asserted JSON: '" + excludedJsonPointerFragment + "'");
                }
            }
        } else {
            throw new AssertionError("Invalid parameter type.");
        }
    }
    /**
     * Removes nodes from a JSON object or array based on specified JSON pointer fragments.
     *
     * @param obj the JSON object or array to process
     * @param excludeJsonPointerFragments the list of pointer fragments to exclude
     * @return a new JSON structure with the specified nodes removed
     * @throws AssertionError if the input type is not a JSONObject or JSONArray
     */
    private static Object removeByJsonPointer(@NonNull Object obj, @NonNull List<String> excludeJsonPointerFragments) {
        if (obj instanceof JSONObject || obj instanceof JSONArray) {
            if (excludeJsonPointerFragments.isEmpty()) {
                // Return a deep copy even if no fragments are provided to ensure a new instance
                return obj instanceof JSONObject ? new JSONObject(obj.toString()) : new JSONArray(obj.toString());
            } else {
                return removeByJsonPointerRecursively(obj, "", excludeJsonPointerFragments);
            }
        } else {
            throw new AssertionError("Invalid parameter type.");
        }
    }

    /**
     * Recursively traverses the JSON structure to rebuild it while omitting paths
     * that match the exclusion targets.
     *
     * @param current the current node being processed
     * @param currentPath the accumulated JSON pointer path
     * @param targets the list of path fragments to exclude
     * @return the filtered JSON node
     */
    private static Object removeByJsonPointerRecursively(Object current, String currentPath, List<String> targets) {
        if (current instanceof JSONObject originalObj) {
            final JSONObject newObj = new JSONObject();

            for (String key : originalObj.keySet()) {
                final String escapedKey = key.replace("~", "~0").replace("/", "~1");
                final String childPath = currentPath + "/" + escapedKey;

                // Only add to the new object if the path matches NONE of the targets
                if (targets.stream().noneMatch(childPath::contains)) {
                    Object value = originalObj.get(key);
                    if (value instanceof JSONObject || value instanceof JSONArray) {
                        newObj.put(key, removeByJsonPointerRecursively(value, childPath, targets));
                    } else {
                        newObj.put(key, value);
                    }
                }
            }
            return newObj;

        } else if (current instanceof JSONArray originalArr) {
            final JSONArray newArr = new JSONArray();

            for (int i = 0; i < originalArr.length(); i++) {
                final String childPath = currentPath + "/" + i;

                // Only add to the new array if the path matches NONE of the targets
                if (targets.stream().noneMatch(childPath::contains)) {
                    final Object value = originalArr.get(i);

                    if (value instanceof JSONObject || value instanceof JSONArray) {
                        newArr.put(removeByJsonPointerRecursively(value, childPath, targets));
                    } else {
                        newArr.put(value);
                    }
                }
            }
            return newArr;
        }

        return current;
    }

    /**
     * Parses a JSON string and generates a map of all JSON pointers and their corresponding values.
     *
     * @param jsonText the JSON string to parse
     * @return a sorted map of JSON pointer strings to node values
     */
    static Map<String, Object> getPointerValueMap(String jsonText) {
        final Map<String, Object> results = new TreeMap<>();
        final String trimmed = jsonText.trim();

        if (!trimmed.isEmpty()) {
            final Object root = trimmed.startsWith("[") ? new JSONArray(trimmed) : new JSONObject(trimmed);

            collectPointers(root, "", results);
        }
        return results;
    }

    /**
     * Generates a map of all JSON pointers and their corresponding values from a JSON object or array.
     *
     * @param obj the JSON object or array to process
     * @return a sorted map of JSON pointer strings to node values
     */
    static Map<String, Object> getPointerValueMap(@NonNull Object obj) {
        final Map<String, Object> results = new TreeMap<>();

        collectPointers(obj, "", results);
        return results;
    }

    /**
     * Recursively collects all JSON pointers and their values into a map,
     * following RFC 6901 escaping rules.
     *
     * @param node the current node
     * @param pointer the current pointer path
     * @param results the map to populate
     */
    private static void collectPointers(Object node, String pointer, Map<String, Object> results) {
        final String currentPath = pointer.isEmpty() ? "/" : pointer;

        // Store the current node in the map
        results.put(currentPath, node);

        if (node instanceof JSONObject obj) {
            for (Iterator<String> keys = obj.keys(); keys.hasNext(); ) {
                final String key = keys.next();
                // RFC 6901 Escaping
                final String escapedKey = key.replace("~", "~0").replace("/", "~1");
                collectPointers(obj.get(key), pointer + "/" + escapedKey, results);
            }
        } else if (node instanceof JSONArray array) {
            for (int i = 0; i < array.length(); i++) {
                collectPointers(array.get(i), pointer + "/" + i, results);
            }
        }
    }

    /**
     * Maps an object to its appropriate JSON representation (JSONObject, JSONArray, or primitive).
     *
     * @param obj the object to map
     * @return the mapped JSON-compatible object
     * @throws AssertionError if the object type is not supported
     */
    static @NonNull Object jsonMapper(@Nullable Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof JSONArray || obj instanceof JSONObject || obj instanceof Number) {
            return obj;
        } else if (obj instanceof String) {
            final String s = String.valueOf(obj);
            if (JsonHelper.isJson(s)) {
                return new JSONObject(s);
            } else if (JsonHelper.isJsonArray(s)) {
                return new JSONArray(s);
            } else {
                return s;
            }
        } else {
            throw new AssertionError("Invalid types in parameters.");
        }
    }

    /**
     * A comparator for JSON nodes based on their string representation.
     */
    static class JsonComparator implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }


}
