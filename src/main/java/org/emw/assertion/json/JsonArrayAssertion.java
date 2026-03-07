package org.emw.assertion.json;

import org.json.JSONArray;

public class JsonArrayAssertion {
    private final String json;

    protected JsonArrayAssertion(String json) {
        this.json = json;
    }

    public void expect(JsonAssertionArrayAction action) {
        JsonNodes nodes = new JsonNodes(new JSONArray(json));

        action.withJsonNodes(nodes);
    }

    public interface JsonAssertionArrayAction {
        void withJsonNodes(JsonNodes nodes);
    }
}
