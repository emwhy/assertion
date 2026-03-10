package org.emw.assertion.json;

import org.emw.assertion.exception.AssertionGroupError;
import org.json.JSONArray;

public final class JsonNodesAssertion {
    private final String json;

    protected JsonNodesAssertion(String json) {
        this.json = json;
    }

    public void expect(JsonAssertionArrayAction action) {
        final JsonAssertionGroup group = new JsonAssertionGroup();

        try {
            final JsonNodes nodes = new JsonNodes(new JsonAssertionGroup(), new JSONArray(json));

            action.withJsonNodes(nodes);
        } finally {
            if (!group.throwables().isEmpty()) {
                throw new AssertionGroupError("Json Assertion", group.throwables());
            }
        }
    }

    public interface JsonAssertionArrayAction {
        void withJsonNodes(JsonNodes nodes);
    }
}
