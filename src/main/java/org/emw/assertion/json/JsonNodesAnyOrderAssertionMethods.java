package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesAnyOrderAssertionMethods extends JsonNodesAssertionMethods {
    public final JsonNodesCaseInsensitivityOnlyAssertionMethods not;
    public final JsonNodesNotOnlyAssertionMethods caseInsensitively;

    JsonNodesAnyOrderAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray actual, boolean negated, boolean ignoreCase, boolean anyOrder, @NonNull List<String> excludedNodes) {
        super(group, actual, negated, ignoreCase, anyOrder, excludedNodes);
        this.not = new JsonNodesCaseInsensitivityOnlyAssertionMethods(group, actual, !negated, ignoreCase, anyOrder, excludedNodes);
        this.caseInsensitively = new JsonNodesNotOnlyAssertionMethods(group, actual, negated, true, anyOrder, excludedNodes);
    }

    public JsonNodesAnyOrderAssertionMethods excluding(@NonNull String jsonPointer) {
        this.addExcludedNode(jsonPointer);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.caseInsensitively.setThrowable(throwable);
        this.not.setThrowable(throwable);
    }
}
