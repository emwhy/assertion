package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public final class JsonNodeCaseInsensitivelyAssertionMethods extends JsonNodeAssertionMethods {
    public final JsonNodeAssertionMethods not;

    protected JsonNodeCaseInsensitivelyAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable Object obj, boolean negated, boolean ignoreCase, List<String> excludedNodes) {
        super(group, obj, negated, ignoreCase, excludedNodes);
        this.not =  new JsonNodeAssertionMethods(group, obj, !negated, ignoreCase, excludedNodes);
    }

    /**
     * Exclude specified node from part of assertions.
     * @param jsonPointers JSON pointer of node to be excluded
     * @return itself
     */
    public JsonNodeCaseInsensitivelyAssertionMethods excluding(@NonNull String... jsonPointers) {
        this.addExcludedNode(jsonPointers);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.be.setThrowable(throwable);
    }
}
