package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class JsonObjectCaseInsensitivelyAssertionMethods extends JsonObjectAssertionMethods {
    public final JsonObjectAssertionMethods not;

    protected JsonObjectCaseInsensitivelyAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase, List<String> excludedNodes) {
        super(obj, negated, ignoreCase, excludedNodes);
        this.not =  new JsonObjectAssertionMethods(obj, !negated, ignoreCase, excludedNodes);
    }

    public JsonObjectCaseInsensitivelyAssertionMethods excluding(@NonNull String jsonPointer) {
        this.excludedNodes.add(jsonPointer);
        return this;
    }

}
