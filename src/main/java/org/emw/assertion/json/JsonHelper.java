package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

final class JsonHelper {
    static boolean isJson(@NonNull String text) {
        return text.trim().startsWith("{") || text.trim().startsWith("[");
    }

    static JsonType jsonType(@NonNull String jsonText) {
        if (jsonText.trim().startsWith("[")) {
            return JsonType.Array;
        } else if (jsonText.trim().startsWith("{")) {
            return JsonType.Object;
        } else {
            throw new JSONException("Invalid Json text: " + jsonText);
        }
    }

    static boolean containsJason(@NonNull JSONObject jsonObject, @NonNull String expectedJsonText, List<String> excludedNodes) {
        return containsJason(jsonObject, new JSONObject(expectedJsonText), excludedNodes);
    }

    static boolean containsJason(@NonNull JSONObject jsonObject, @NonNull JSONObject expectedJsonObject, List<String> excludedNodes) {
        final Map<String, Object> actual = getPointerValueMap(removeByJsonPointer(jsonObject, excludedNodes));
        final JSONObject containedJson = removeByJsonPointer(expectedJsonObject, excludedNodes);

        for (Object value : actual.values()) {
            if (value instanceof JSONObject) {
                if (jsonMatched((JSONObject) value, containedJson, List.of(), false).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    static String jsonMatched(@NonNull JSONObject jsonObject, @NonNull String expectedJsonText, List<String> excludedNodes, boolean ignoreCase) {
        return jsonMatched(jsonObject, new JSONObject(expectedJsonText), excludedNodes, ignoreCase);
    }

    static String jsonMatched(@NonNull JSONObject jsonObject, @NonNull JSONObject expectedJsonObject, List<String> excludedNodes, boolean ignoreCase) {
        final Map<String, Object> actual = getPointerValueMap(removeByJsonPointer(jsonObject, excludedNodes));
        final Map<String, Object> expected = getPointerValueMap(removeByJsonPointer(expectedJsonObject, excludedNodes));

        validateExcludedJsonPointerFragments(jsonObject, excludedNodes);

        if (actual.size() > expected.size()) {
            return "Expected Json size is smaller than the actual Json size.";
        } else if (actual.size() < expected.size()) {
            return "Expected Json size is larger than the actual Json size.";
        } else {
            for (Map.Entry<String, Object> entry : actual.entrySet()) {
                Object expectedValue = expected.get(entry.getKey());

                if (!(entry.getValue() instanceof JSONObject) && !(entry.getValue() instanceof JSONArray)) {
                    if (expectedValue == null) {
                        return "Expected name is missing: " + entry.getKey();
                    } else if (entry.getValue() instanceof String && ignoreCase) {
                        if (!expectedValue.toString().equalsIgnoreCase(entry.getValue().toString())) {
                            return "Expected value at " + entry.getKey() + " does not case-insensitively match actual value. actual: " + entry.getValue() + ", expected: " + expectedValue;
                        }
                    } else if (!entry.getValue().equals(expectedValue)) {
                        return "Expected value at " + entry.getKey() + " does not match actual value. actual: " + entry.getValue() + ", expected: " + expectedValue;
                    }
                }
            }
        }
        return "";
    }

    private static void validateExcludedJsonPointerFragments(@NonNull JSONObject jsonObject, @NonNull List<String> excludeJsonPointerFragments) {
        final Map<String, Object> map = getPointerValueMap(jsonObject);

        for (String excludedJsonPointerFragment : excludeJsonPointerFragments) {
            if (map.keySet().stream().noneMatch(key -> key.contains(excludedJsonPointerFragment))) {
                throw new AssertionError("Cannot find specified 'excluding' Json pointer node in asserted Json: " + excludedJsonPointerFragment);
            }
        }
    }

    private static JSONObject removeByJsonPointer(@NonNull JSONObject jsonObject, @NonNull List<String> excludeJsonPointerFragments) {
        if (excludeJsonPointerFragments.isEmpty()) {
            // Return a deep copy even if no fragments are provided to ensure a new instance
            return new JSONObject(jsonObject.toString());
        } else {
            return (JSONObject) removeByJsonPointerRecursively(jsonObject, "", excludeJsonPointerFragments);
        }
    }

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

    static Map<String, Object> getPointerValueMap(String jsonText) {
        final Map<String, Object> results = new TreeMap<>();
        final String trimmed = jsonText.trim();

        if (!trimmed.isEmpty()) {
            final Object root = trimmed.startsWith("[") ? new JSONArray(trimmed) : new JSONObject(trimmed);

            collectPointers(root, "", results);
        }
        return results;
    }

    static Map<String, Object> getPointerValueMap(JSONObject jsonObject) {
        final Map<String, Object> results = new TreeMap<>();

        collectPointers(jsonObject, "", results);
        return results;
    }

    static Map<String, Object> getPointerValueMap(JSONArray jsonArray) {
        final Map<String, Object> results = new TreeMap<>();

        collectPointers(jsonArray, "", results);
        return results;
    }

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
}
