package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public sealed class JsonNodeAssertionMethods extends JsonAssertionMethods permits JsonNodeAllAssertionMethods, JsonNodeCaseInsensitivelyAssertionMethods, JsonNodeNotAssertionMethods {
    public final JsonNodeBeAssertionMethods be;

    protected JsonNodeAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable Object obj, boolean negated, boolean ignoreCase, @NonNull List<String> excludedNodes) {
        super(group, obj, negated, ignoreCase, false, excludedNodes);
        this.be = new JsonNodeBeAssertionMethods(group, obj, negated, ignoreCase);
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.be.setThrowable(throwable);
    }

    /**
     * Assert that the selected JSON node exists.
     */
    public void exist() {
        if (negated) {
            if (throwable() == null) {
                addToGroup(new AssertionError("Expected node to not exist."));
            }
        } else {
            final Throwable th = throwable();

            if (th != null) {
                if (th.getMessage() == null) {
                    addToGroup(new AssertionError("Expected node to exist."));
                } else {
                    addToGroup(new AssertionError("Expected node to exist: " + th.getMessage()));
                }
            }
        }
    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding).
     * @param jsonUrl URL to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void be(@Nullable URL jsonUrl, String... formatterArgs) {
        be(JsonHelper.loadJsonFile(jsonUrl, formatterArgs));
    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding).
     * @param jsonPath Path to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void be(@NonNull Path jsonPath, String... formatterArgs) {
        be(JsonHelper.loadJsonFile(jsonPath, formatterArgs));
    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding).
     * @param jsonFile JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void be(@NonNull File jsonFile, String... formatterArgs) {
        be(JsonHelper.loadJsonFile(jsonFile, formatterArgs));
    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding). It can be JSON
     * content or endpoint values (string, number, etc.).
     * @param expected expected data
     */
    public void be(@NonNull Object expected) {
        assertCondition(() -> {
            final Object expectedObject = expected instanceof String && JsonHelper.isJson((String) expected) ? new JSONObject((String) expected) : expected;
            final Object object = this.object();

            if (expectedObject instanceof JSONObject && object instanceof JSONObject) {
                final String errorMessage = JsonHelper.matchJson(object, expectedObject, this.excludedNodes(), this.ignoreCase);

                if (negated) {
                    if (errorMessage.isEmpty()) {
                        throw new AssertionError("Expected JSON to not match, but they match.");
                    }
                } else {
                    if (!errorMessage.isEmpty()) {
                        throw new AssertionError(errorMessage);
                    }
                }
            } else if (expectedObject instanceof Float) {
                if ((Double.parseDouble(expectedObject.toString()) == ((Number) object).doubleValue()) == negated) {
                    throw new AssertionError(String.format("Expected value '%s' to%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), object));
                }
            } else if (expectedObject instanceof Number) {
                if ((((Number) expectedObject).doubleValue() == ((Number) object).doubleValue()) == negated) {
                    throw new AssertionError(String.format("Expected value '%s' to%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), object));
                }
            } else if (ignoreCase && expectedObject instanceof String) {
                if (object.toString().trim().equalsIgnoreCase(expectedObject.toString().trim()) == negated) {
                    throw new AssertionError(String.format("Expected value '%s' to case-insensitively%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), object));
                }
            } else if (object.toString().trim().equals(expectedObject.toString().trim()) == negated) {
                throw new AssertionError(String.format("Expected value '%s' to%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), object));
            }
        });
    }

    /**
     * Assert that JSON segment can be found somewhere within the current JSON context. The expected JSON does not need to be
     * a direct child node of the JSON context.
     * @param jsonToBeFoundText JSON string to be found
     */
    public void findJson(@NonNull String jsonToBeFoundText) {
        if (JsonHelper.isJson(jsonToBeFoundText)) {
            findJson(new JSONObject(jsonToBeFoundText));
        } else if (JsonHelper.isJsonArray(jsonToBeFoundText)) {
            findJson(new JSONArray(jsonToBeFoundText));
        } else {
            throw new AssertionError("Not JSON.");
        }
    }

    /**
     * Assert that expected JSON data is found within the actual JSON object.
     * @param jsonUrl URL to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void findJson(@Nullable URL jsonUrl, String... formatterArgs) {
        findJson(JsonHelper.loadJsonFile(jsonUrl, formatterArgs));
    }

    /**
     * Assert that expected JSON data is found within the actual JSON object.
     * @param jsonPath Path to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void findJson(@NonNull Path jsonPath, String... formatterArgs) {
        findJson(JsonHelper.loadJsonFile(jsonPath, formatterArgs));
    }

    /**
     * Assert that expected JSON data is found within the actual JSON object.
     * @param jsonFile JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void findJson(@NonNull File jsonFile, String... formatterArgs) {
        findJson(JsonHelper.loadJsonFile(jsonFile, formatterArgs));
    }

    /**
     * Assert that expected JSON data is found within the actual JSON object.
     * @param jsonToFind JSONObject or JSONArray to find
     */
    public void findJson(@NonNull Object jsonToFind) {
        assertCondition(() -> {
            final JSONObject jsonObject = jsonObject();

            if (negated) {
                if (JsonHelper.findJson(jsonObject, jsonToFind, this.excludedNodes(), this.ignoreCase)) {
                    throw new AssertionError("Expected to not find JSON data within actual JSON data.");
                }
            } else {
                if (!JsonHelper.findJson(jsonObject, jsonToFind, this.excludedNodes(), this.ignoreCase)) {
                    throw new AssertionError("Expected to find JSON data within actual JSON data.");
                }
            }
        });
    }
}
