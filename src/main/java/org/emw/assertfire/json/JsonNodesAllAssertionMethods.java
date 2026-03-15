package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesAllAssertionMethods extends JsonNodesAssertionMethods {
    public final JsonNodesNotAssertionMethods not;
    public final JsonNodesAnyOrderAssertionMethods inAnyOrder;
    public final JsonNodesCaseInsensitivelyAssertionMethods caseInsensitively;

    JsonNodesAllAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray actual, boolean negated, boolean ignoreCase, boolean anyOrder, @NonNull List<String> excludedNodes) {
        super(group, actual, negated, ignoreCase, anyOrder, excludedNodes);

        this.not = new JsonNodesNotAssertionMethods(group, actual, !negated, ignoreCase, anyOrder, excludedNodes);
        this.inAnyOrder = new JsonNodesAnyOrderAssertionMethods(group, actual, negated, ignoreCase, true, excludedNodes);
        this.caseInsensitively = new JsonNodesCaseInsensitivelyAssertionMethods(group, actual, negated, true, anyOrder, excludedNodes);
    }

    /**
     * Exclude specified node from part of assertions.
     * @param jsonPointers JSON pointer of node to be excluded
     * @return itself
     */
    public JsonNodesAllAssertionMethods excluding(@NonNull String... jsonPointers) {
        this.addExcludedNode(jsonPointers);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.caseInsensitively.setThrowable(throwable);
        this.not.setThrowable(throwable);
        this.inAnyOrder.setThrowable(throwable);
    }

}
