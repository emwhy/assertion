package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesCaseInsensitivelyAssertionMethods extends JsonNodesAssertionMethods {
    public final JsonNodesAnyOrderOnlyAssertionMethods not;
    public final JsonNodesNotOnlyAssertionMethods inAnyOrder;

    JsonNodesCaseInsensitivelyAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray actual, boolean negated, boolean ignoreCase, boolean anyOrder, @NonNull List<String> excludedNodes) {
        super(group, actual, negated, ignoreCase, anyOrder, excludedNodes);
        this.not = new JsonNodesAnyOrderOnlyAssertionMethods(group, actual, !negated, ignoreCase, anyOrder, excludedNodes);
        this.inAnyOrder = new JsonNodesNotOnlyAssertionMethods(group, actual, negated, ignoreCase, true, excludedNodes);
    }

    /**
     * Exclude specified node from part of assertions.
     * @param jsonPointers JSON pointer of node to be excluded
     * @return itself
     */
    public JsonNodesCaseInsensitivelyAssertionMethods excluding(@NonNull String... jsonPointers) {
        this.addExcludedNode(jsonPointers);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.not.setThrowable(throwable);
        this.inAnyOrder.setThrowable(throwable);
    }

}
