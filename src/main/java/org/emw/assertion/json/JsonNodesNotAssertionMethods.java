package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesNotAssertionMethods extends JsonNodesAssertionMethods {
    public final JsonNodesCaseInsensitivityOnlyAssertionMethods inAnyOrder;
    public final JsonNodesAnyOrderOnlyAssertionMethods caseInsensitively;

    JsonNodesNotAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray actual, boolean negated, boolean ignoreCase, boolean anyOrder, @NonNull List<String> excludedNodes) {
        super(group, actual, negated, ignoreCase, anyOrder, excludedNodes);
        this.inAnyOrder = new JsonNodesCaseInsensitivityOnlyAssertionMethods(group, actual, negated, ignoreCase, true, excludedNodes);
        this.caseInsensitively = new JsonNodesAnyOrderOnlyAssertionMethods(group, actual, negated, true, anyOrder, excludedNodes);
    }

    public JsonNodesNotAssertionMethods excluding(@NonNull String jsonPointer) {
        this.addExcludedNode(jsonPointer);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.caseInsensitively.setThrowable(throwable);
        this.inAnyOrder.setThrowable(throwable);
    }

}
