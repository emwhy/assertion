package org.emw.assertion.json;

import org.emw.assertion.exception.AssertionGroupError;
import org.json.JSONObject;

public final class JsonNodeAssertion {
    private final String json;

    protected JsonNodeAssertion(String json) {
        this.json = json;
    }

    public void expect(JsonAssertionObjectAction action) {
        final JsonAssertionGroup group = new JsonAssertionGroup();

        try {
            final JsonNode node = new JsonNode(group, new JSONObject(json));

            action.withJsonNode(node);
        } finally {
            if (!group.throwables().isEmpty()) {
                throw new AssertionGroupError("Json Assertion", group.throwables());
            }
        }
    }

    public interface JsonAssertionObjectAction {
        void withJsonNode(JsonNode json);
    }
}
