package org.emw.assertion.json;

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

    public void be(@NonNull Object expected) {
        assertCondition(() -> {
            final Object expectedObject = expected instanceof String && JsonHelper.isJson((String) expected) ? new JSONObject((String) expected) : expected;
            final Object object = this.object();

            if (expectedObject instanceof JSONObject && object instanceof JSONObject) {
                final String errorMessage = JsonHelper.matchJson((JSONObject) object, (JSONObject) expectedObject, this.excludedNodes(), this.ignoreCase);

                if (negated) {
                    if (errorMessage.isEmpty()) {
                        throw new AssertionError("Expected Json to not match, but they match.");
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

    public void containJson(@NonNull String containedJsonText) {
        if (JsonHelper.isJson(containedJsonText)) {
            containJson(new JSONObject(containedJsonText));
        } else if (JsonHelper.isJsonArray(containedJsonText)) {
            containJson(new JSONArray(containedJsonText));
        } else {
            throw new AssertionError("Not Json.");
        }
    }

    public void containJson(@NonNull JSONObject containedJson) {
        assertCondition(() -> {
            final Object object = this.object();

            if (JsonHelper.findJson(object, containedJson, this.excludedNodes(), this.ignoreCase) == negated) {
                throw new AssertionError("Expected the actual Json data to contain the expected Json data.");
            }
        });
    }

    public void containJson(@NonNull JSONArray containedJson) {
        assertCondition(() -> {
            final Object object = this.object();

            if (JsonHelper.findJson(object, containedJson, this.excludedNodes(), this.ignoreCase) == negated) {
                throw new AssertionError("Expected the actual Json data to contain the expected Json data.");
            }
        });
    }
}
