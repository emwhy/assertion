package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.List;

public final class JsonNodesBeAssertionMethods extends JsonAssertionMethods {

    protected JsonNodesBeAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray jsonArray, boolean negated, boolean ignoreCase, boolean inAnyOrder, @NonNull List<String> excludedNodes) {
        super(group, jsonArray, negated, ignoreCase, inAnyOrder, excludedNodes);
    }
    /**
     * Assert that the JSON array is empty.
     */
    public void empty() {
        assertCondition(() -> {
            final JSONArray jsonArray = this.jsonArray();

            if (negated) {
                if (jsonArray.isEmpty()) {
                    throw new AssertionError("Expected non-empty JSON array.");
                }
            } else {
                if (!jsonArray.isEmpty()) {
                    throw new AssertionError("Expected empty JSON array.");
                }
            }
        });
    }

    /**
     * Assert that the JSON array has the expected number of nodes.
     * @param expectedSize the expected size of the array
     */
    public void sizeOf(int expectedSize) {
        assertCondition(() -> {
            final JSONArray jsonArray = this.jsonArray();

            if (negated) {
                if (expectedSize == jsonArray.length()) {
                    throw new AssertionError("Expected JSON array size to not be equal to JSON array size.");
                }
            } else {
                if (expectedSize != jsonArray.length()) {
                    throw new AssertionError("Expected JSON array size to be equal to JSON array size.");
                }
            }
        });
    }
}
