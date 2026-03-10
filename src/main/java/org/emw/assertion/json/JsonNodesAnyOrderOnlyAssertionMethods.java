package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesAnyOrderOnlyAssertionMethods extends JsonNodesAssertionMethods {
    public final JsonNodesAssertionMethods inAnyOrder;

    JsonNodesAnyOrderOnlyAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray actual, boolean negated, boolean ignoreCase, boolean anyOrder, @NonNull List<String> excludedNodes) {
        super(group, actual, negated, ignoreCase, anyOrder, excludedNodes);
        this.inAnyOrder = new JsonNodesAssertionMethods(group, actual, negated, ignoreCase, true, excludedNodes);
    }

    public JsonNodesAnyOrderOnlyAssertionMethods excluding(@NonNull String jsonPointer) {
        this.addExcludedNode(jsonPointer);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.inAnyOrder.setThrowable(throwable);
    }

}
