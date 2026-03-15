package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class JsonAssertionMethods extends JsonAssertion {
    private final @Nullable Object object;
    protected final boolean negated;
    protected final boolean ignoreCase;
    protected final boolean inAnyOrder;
    private final @NonNull List<String> excludedNodes = new ArrayList<>();
    private @Nullable Throwable throwable;

    protected JsonAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable Object object, boolean negated, boolean ignoreCase, boolean inAnyOrder, @NonNull List<String> excludedNodes) {
        super(group);

        this.object = object;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
        this.inAnyOrder = inAnyOrder;
        this.excludedNodes.addAll(excludedNodes);
    }

    protected @Nullable Throwable throwable() {
        return throwable;
    }

    protected void setThrowable(@NonNull Throwable throwable) {
        this.throwable = throwable;
    }

    protected void addExcludedNode(@NonNull String... jsonPointers) {
        this.excludedNodes.addAll(Arrays.stream(jsonPointers).toList());
    }

    protected List<String> excludedNodes() {
        return List.of(excludedNodes.toArray(new String[0]));
    }

    protected @NonNull Object object() {
        if (object == null) {
            // Should not get here because "throwable" value should be populated and handled before getting here.
            throw new AssertionError("Node is not found.");
        }
        return object;
    }

    protected @NonNull JSONObject jsonObject() {
        if (object() instanceof JSONObject) {
            return (JSONObject) object();
        } else {
            throw new JSONException("Not JSONObject.");
        }
    }

    protected @NonNull JSONArray jsonArray() {
        if (object() instanceof JSONArray) {
            return (JSONArray) object();
        } else  {
            throw new JSONException("Not JSONArray.");
        }
    }

    protected interface AssertionAction {
        void doAssertiveAction();
    }

    protected final void assertCondition(@NonNull AssertionAction assertion) {
        if (throwable == null) {
            try {
                assertion.doAssertiveAction();
            } catch (Throwable th) {
                this.addToGroup(th);
            }
        } else {
            this.addToGroup(throwable);
        }
    }
}
