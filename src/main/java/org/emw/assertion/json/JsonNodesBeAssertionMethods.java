package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesBeAssertionMethods extends JsonAssertionMethods {

    protected JsonNodesBeAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray jsonArray, boolean negated, boolean ignoreCase, boolean inAnyOrder, @NonNull List<String> excludedNodes) {
        super(group, jsonArray, negated, ignoreCase, inAnyOrder, excludedNodes);
    }

    public void empty() {
        assertCondition(() -> {
            final JSONArray jsonArray = this.jsonArray();

            if (jsonArray == null) {
                throw new AssertionError("Node does not exist.");
            } else {
                if (negated) {
                    if (jsonArray.isEmpty()) {
                        throw new AssertionError("Expected non-empty Json array.");
                    }
                } else {
                    if (!jsonArray.isEmpty()) {
                        throw new AssertionError("Expected empty Json array.");
                    }
                }
            }
        });
    }

    public void sizeOf(int expectedSize) {
        assertCondition(() -> {
            final JSONArray jsonArray = this.jsonArray();

            if (jsonArray == null) {
                throw new AssertionError("Node does not exist.");
            } else {
                if (negated) {
                    if (expectedSize == jsonArray.length()) {
                        throw new AssertionError("Expected Json array size to not be equal to Json array size.");
                    }
                } else {
                    if (expectedSize != jsonArray.length()) {
                        throw new AssertionError("Expected Json array size to be equal to Json array size.");
                    }
                }
            }
        });
    }
}
