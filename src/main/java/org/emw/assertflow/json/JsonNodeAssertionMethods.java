package org.emw.assertflow.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

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
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding). It can be JSON
     * content or endpoint values (string, number, etc.).
     * @param expected expected data
     */
    public void be(@NonNull Object expected) {
        assertCondition(() -> {
            final Object expectedObject = expected instanceof String && JsonHelper.isJson((String) expected) ? new JSONObject((String) expected) : expected;
            final Object object = this.object();

            if (expectedObject instanceof JSONObject && object instanceof JSONObject) {
                final String errorMessage = JsonHelper.matchJson((JSONObject) object, (JSONObject) expectedObject, this.excludedNodes(), this.ignoreCase);

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
     * Assert that JSON segment can be found somewhere within the current JSON context. The expected JSON does not need to be
     * a direct child node of the JSON context.
     * @param jsonObjectToBeFound JSONObject to be found
     */
    public void findJson(@NonNull JSONObject jsonObjectToBeFound) {
        assertCondition(() -> {
            final Object object = this.object();

            if (JsonHelper.findJson(object, jsonObjectToBeFound, this.excludedNodes(), this.ignoreCase) == negated) {
                throw new AssertionError("Expected the actual JSON data to contain the expected JSON data.");
            }
        });
    }

    /**
     * Assert that JSON segment can be found somewhere within the current JSON context. The expected JSON does not need to be
     * a direct child node of the JSON context.
     * @param jsonArrayToBeFound JSONArray to be found
     */
    public void findJson(@NonNull JSONArray jsonArrayToBeFound) {
        assertCondition(() -> {
            final Object object = this.object();

            if (JsonHelper.findJson(object, jsonArrayToBeFound, this.excludedNodes(), this.ignoreCase) == negated) {
                throw new AssertionError("Expected the actual JSON data to contain the expected JSON data.");
            }
        });
    }
}
