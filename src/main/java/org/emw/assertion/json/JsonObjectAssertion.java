package org.emw.assertion.json;

import org.json.JSONObject;

public class JsonObjectAssertion {
    private final String json;

    protected JsonObjectAssertion(String json) {
        this.json = json;
    }

    public void expect(JsonAssertionObjectAction action) {
        JsonNode node = new JsonNode(new JSONObject(json));

        action.withJsonNode(node);
    }

    public interface JsonAssertionObjectAction {
        void withJsonNode(JsonNode json);
    }
}
