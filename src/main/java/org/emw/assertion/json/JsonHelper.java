package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    static boolean jsonMatched(@NonNull JSONObject jsonObject, @NonNull String expectedJsonText, List<String> excludedNodes, boolean ignoreCase) {
        return jsonMatched(jsonObject, new JSONObject(expectedJsonText), excludedNodes, ignoreCase);
    }

    static boolean jsonMatched(@NonNull JSONObject jsonObject, @NonNull JSONObject expectedJsonObject, List<String> excludedNodes, boolean ignoreCase) {
        final Map<String, Object> actual = removeExcludedNodes(getPointerValueMap(jsonObject), excludedNodes);
        final Map<String, Object> expected = removeExcludedNodes(getPointerValueMap(expectedJsonObject), excludedNodes);

        if (actual.size() != expected.size()) {
            return false;
        } else if (!actual.keySet().equals(expected.keySet())) {
            return false;
        } else {
            for (Map.Entry<String, Object> entry : actual.entrySet()) {
                Object expectedValue = expected.get(entry.getKey());

                if (excludedNodes.stream().anyMatch(s -> entry.getKey().startsWith(s))) {
                    continue;
                } else if (!(entry.getValue() instanceof JSONObject) && !(entry.getValue() instanceof JSONArray)) {
                    if (entry.getValue() instanceof String && ignoreCase) {
                        if (expectedValue == null || !expectedValue.toString().equalsIgnoreCase(entry.getValue().toString())) {
                            return false;
                        }
                    } else if (!entry.getValue().equals(expectedValue)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static Map<String, Object> removeExcludedNodes(Map<String, Object> map, List<String> excludedNodes) {
        List<String> removeKeys = map.keySet().stream().filter(key -> (excludedNodes.stream().anyMatch(key::startsWith))).map(String::toString).toList();

        for (String key : removeKeys) {
            map.remove(key);
        }
        return map;
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
