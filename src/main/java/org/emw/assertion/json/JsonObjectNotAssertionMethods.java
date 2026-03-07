package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class JsonObjectNotAssertionMethods extends JsonObjectAssertionMethods {
    public final JsonObjectAssertionMethods caseInsensitively;

    protected JsonObjectNotAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase, List<String> excludedNodes) {
        super(obj, negated, ignoreCase, excludedNodes);
        this.caseInsensitively = new JsonObjectAssertionMethods(obj, negated, true, excludedNodes);
    }

    public JsonObjectNotAssertionMethods excluding(@NonNull String jsonPointer) {
        this.excludedNodes.add(jsonPointer);
        return this;
    }
}
