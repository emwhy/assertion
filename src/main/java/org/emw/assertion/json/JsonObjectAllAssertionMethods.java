package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class JsonObjectAllAssertionMethods extends JsonObjectAssertionMethods {
    public final JsonObjectNotAssertionMethods not;
    public final JsonObjectCaseInsensitivelyAssertionMethods caseInsensitively;

    protected JsonObjectAllAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase, List<String> excludedNodes) {
        super(obj, negated, ignoreCase, excludedNodes);
        this.not = new JsonObjectNotAssertionMethods(obj, !negated, ignoreCase, excludedNodes);
        this.caseInsensitively = new JsonObjectCaseInsensitivelyAssertionMethods(obj, negated, true, excludedNodes);
    }

    public JsonObjectAllAssertionMethods excluding(@NonNull String jsonPointer) {
        this.excludedNodes.add(jsonPointer);
        return this;
    }
}
