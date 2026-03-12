package org.emw.assertflow.json;

import org.emw.assertflow.exception.AssertionGroupError;
import org.json.JSONArray;

public final class JsonNodesAssertion {
    private final String json;

    protected JsonNodesAssertion(String json) {
        this.json = json;
    }

    /**
     * Predicate that allows assertion to the JSON content.
     * @param action predicate
     */
    public void expect(JsonAssertionArrayAction action) {
        final JsonAssertionGroup group = new JsonAssertionGroup();

        try {
            final JsonNodes nodes = new JsonNodes(new JsonAssertionGroup(), new JSONArray(json));

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
