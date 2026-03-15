package org.emw.assertfire.json;

import org.emw.assertfire.exception.AssertionGroupError;
import org.json.JSONArray;

public final class JsonNodesAssertion {
    private final JSONArray jsonArray;

    protected JsonNodesAssertion(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    /**
     * Predicate that allows assertion to the JSON content.
     * @param action predicate
     */
    public void expect(JsonAssertionArrayAction action) {
        final JsonAssertionGroup group = new JsonAssertionGroup();

        try {
            final JsonNodes nodes = new JsonNodes(group, jsonArray);

            action.withJsonNodes(nodes);
        } finally {
            if (!group.throwables().isEmpty()) {
                throw new AssertionGroupError("JSON Assertion", group.throwables());
            }
        }
    }

    public interface JsonAssertionArrayAction {
        void withJsonNodes(JsonNodes nodes);
    }
}
