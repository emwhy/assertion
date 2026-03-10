package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesCaseInsensitivityOnlyAssertionMethods extends JsonNodesAssertionMethods {
    public final JsonNodesAssertionMethods caseInsensitively;

    JsonNodesCaseInsensitivityOnlyAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray actual, boolean negated, boolean ignoreCase, boolean anyOrder, @NonNull List<String> excludedNodes) {
        super(group, actual, negated, ignoreCase, anyOrder,  excludedNodes);
        this.caseInsensitively = new JsonNodesAssertionMethods(group, actual, negated, true, anyOrder,  excludedNodes);
    }

    public JsonNodesCaseInsensitivityOnlyAssertionMethods excluding(@NonNull String jsonPointer) {
        this.addExcludedNode(jsonPointer);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.caseInsensitively.setThrowable(throwable);
    }

}
