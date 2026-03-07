package org.emw.assertion.json;

/*

assertJson(json).expect(json -> {
    json.to.contain(data);
    json.to.be("");
    json.node("").to.contain(data);
    json.node("").to.excludingNode("").caseInsensitively.not.contain(data);
    json.nodes("").to.inAnyOrder.have(data);
    json.nodes("").at(0).node("").to.inAnyOrder.have(data);
    json.nodes("").iterate((node, i) -> {
        node.to
    });
});

 */

import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonNode {
    public final JsonObjectAllAssertionMethods to;
    protected final @Nullable Object nodeObject;

    protected JsonNode(@Nullable Object nodeObject) {
        this.nodeObject = nodeObject;
        this.to = new JsonObjectAllAssertionMethods(nodeObject, false, false, List.of());
    }

    public JsonNode node(String jsonPointer) {
        if (nodeObject instanceof JSONObject) {
            final Object o = ((JSONObject) nodeObject).query(jsonPointer);

            return new JsonNode(o);
        } else {
            throw new JSONException("Already at a data node. Cannot traverse further: data = '" + nodeObject + "'");
        }
    }

    public JsonNodes nodes(String jsonPointer) {
        if (nodeObject instanceof JSONObject) {
            final Object o = ((JSONObject) nodeObject).query(jsonPointer);

            if (o == null) {
                throw new JSONException("Cannot find JSON node: '" + jsonPointer + "'");
            } else if (o instanceof JSONArray) {
                return new JsonNodes((JSONArray) o);
            } else {
                throw new JSONException("The node is not Json array.");
            }
        } else {
            throw new JSONException("Already at a data node. Cannot traverse further: data = '" + nodeObject + "'");
        }

    }
}
