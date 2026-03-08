package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionMethods;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectAssertionMethods extends AssertionMethods {
    public final JsonObjectBeAssertionMethods be;
    private final @Nullable Object obj;
    private final boolean negated;
    private final boolean ignoreCase;
    protected final @NonNull List<String> excludedNodes = new ArrayList<>();

    protected JsonObjectAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase, @NonNull List<String> excludedNodes) {
        super(null, "", negated, ignoreCase);
        this.obj = obj;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
        this.excludedNodes.addAll(excludedNodes);
        this.be = new JsonObjectBeAssertionMethods(obj, negated, ignoreCase);
    }

    public void be(@NonNull Object expected) {
        assertCondition(() -> {
            final Object expectedObject = expected instanceof String && JsonHelper.isJson((String) expected) ? new JSONObject((String) expected) : expected;

            if (this.obj == null) {
                throw new AssertionError("Node does not exist.");
            } else if (expectedObject instanceof JSONObject && this.obj instanceof JSONObject) {
                final String errorMessage = JsonHelper.jsonMatched((JSONObject) this.obj, (JSONObject) expectedObject, this.excludedNodes, this.ignoreCase);

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
                if ((Double.parseDouble(expectedObject.toString()) == ((Number) this.obj).doubleValue()) == negated) {
                    throw new AssertionError(String.format("Expected value '%s' to%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), this.obj));
                }
            } else if (expectedObject instanceof Number) {
                if ((((Number) expectedObject).doubleValue() == ((Number) this.obj).doubleValue()) == negated) {
                    throw new AssertionError(String.format("Expected value '%s' to%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), this.obj));
                }
            } else if (ignoreCase && expectedObject instanceof String) {
                if (this.obj.toString().trim().equalsIgnoreCase(expectedObject.toString().trim()) == negated) {
                    throw new AssertionError(String.format("Expected value '%s' to case-insensitively%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), this.obj));
                }
            } else if (this.obj.toString().trim().equals(expectedObject.toString().trim()) == negated) {
                throw new AssertionError(String.format("Expected value '%s' to%s be actual value '%s'.", expectedObject, (negated ? " not" : ""), this.obj));
            }
        });
    }

    public void exists() {
        assertCondition(() -> {
            if ((this.obj == null) != negated) {
                throw new AssertionError("Node does not exist.");
            }
        });
    }

    public void containJson(@NonNull String containedJsonText) {
        containJson(new JSONObject(containedJsonText));
    }

    public void containJson(@NonNull JSONObject containedJson) {
        assertCondition(() -> {
            if (this.obj == null) {
                throw new AssertionError("Node does not exist.");
            } else if (JsonHelper.containsJason((JSONObject) this.obj, containedJson, this.excludedNodes) == negated) {
                throw new AssertionError("Expected the actual Json data to contain the expected Json data.");
            }
        });
    }

}
