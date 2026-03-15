package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public final class JsonNodeAllAssertionMethods extends JsonNodeAssertionMethods {
    public final JsonNodeNotAssertionMethods not;
    public final JsonNodeCaseInsensitivelyAssertionMethods caseInsensitively;

    protected JsonNodeAllAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable Object obj, boolean negated, boolean ignoreCase, List<String> excludedNodes) {
        super(group, obj, negated, ignoreCase, excludedNodes);
        this.not = new JsonNodeNotAssertionMethods(group, obj, !negated, ignoreCase, excludedNodes);
        this.caseInsensitively = new JsonNodeCaseInsensitivelyAssertionMethods(group, obj, negated, true, excludedNodes);
    }

    /**
     * Exclude specified node from part of assertions.
     * @param jsonPointers JSON pointer of node to be excluded
     * @return itself
     */
    public JsonNodeAllAssertionMethods excluding(@NonNull String... jsonPointers) {
        this.addExcludedNode(jsonPointers);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.not.setThrowable(throwable);
        this.caseInsensitively.setThrowable(throwable);
    }
}
