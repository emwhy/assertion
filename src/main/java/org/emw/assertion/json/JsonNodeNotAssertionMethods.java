package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public final class JsonNodeNotAssertionMethods extends JsonNodeAssertionMethods {
    public final JsonNodeAssertionMethods caseInsensitively;

    protected JsonNodeNotAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable Object obj, boolean negated, boolean ignoreCase, List<String> excludedNodes) {
        super(group, obj, negated, ignoreCase, excludedNodes);
        this.caseInsensitively = new JsonNodeAssertionMethods(group, obj, negated, true, excludedNodes);
    }

    public JsonNodeNotAssertionMethods excluding(@NonNull String jsonPointer) {
        this.addExcludedNode(jsonPointer);
        return this;
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.caseInsensitively.setThrowable(throwable);
    }
}
