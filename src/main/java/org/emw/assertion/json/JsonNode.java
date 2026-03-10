package org.emw.assertion.json;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;

import java.util.List;

public sealed class JsonNode extends JsonAssertion permits ErroredJsonNode {
    public final JsonNodeAllAssertionMethods to;
    protected final @Nullable Object nodeObject;

    protected JsonNode(@NonNull JsonAssertionGroup group, @Nullable Object nodeObject) {
        super(group);
        this.nodeObject = nodeObject;
        this.to = new JsonNodeAllAssertionMethods(this.group, nodeObject, false, false, List.of());
    }

    public JsonNode node(String jsonPointer) {

        try {
            if (nodeObject instanceof JSONObject) {
                final Object o = ((JSONObject) nodeObject).query(jsonPointer);

                if (o == null) {
                    final Throwable th = new JSONException("Cannot find JSON node: '" + jsonPointer + "'");
                    this.to.setThrowable(th);
                    return new ErroredJsonNode(group, th);
                } else if (o instanceof JSONArray) {
                    final Throwable th = new JSONException("Expected reference to JSON Object, but got reference to JSON Array: '" + jsonPointer + "'");
                    this.to.setThrowable(th);
                    return new ErroredJsonNode(group, th);
                } else {
                    return new JsonNode(this.group, o);
                }
            } else {
                final Throwable th = new JSONException("Already at a data node. Cannot traverse further: data = '" + jsonPointer + "'");

                this.to.setThrowable(th);
                return new ErroredJsonNode(group, th);
            }
        } catch (JSONPointerException ex) {
            final Throwable th = new JSONException("Cannot find JSON node: '" + jsonPointer + "'");
            this.to.setThrowable(th);
            return new ErroredJsonNode(group, th);
        }
    }

    public JsonNodes nodes(String jsonPointer) {
        try {
            if (nodeObject instanceof JSONObject) {
                final Object o = ((JSONObject) nodeObject).query(jsonPointer);

                if (o == null) {
                    final Throwable th = new JSONException("Cannot find JSON node: '" + jsonPointer + "'");
                    this.to.setThrowable(th);
                    return new ErroredJsonNodes(group, th);
                } else if (o instanceof JSONArray) {
                    return new JsonNodes(this.group, (JSONArray) o);
                } else {
                    final Throwable th = new JSONException("Expected reference to JSON Array, but got reference to JSON Object: '" + jsonPointer + "'");
                    this.to.setThrowable(th);
                    return new ErroredJsonNodes(group, th);
                }
            } else {
                final Throwable th = new JSONException("At a data node. Cannot traverse further: data = '" + jsonPointer + "'");
                this.to.setThrowable(th);
                return new ErroredJsonNodes(group, th);
            }
        } catch (JSONException ex) {
            if (node(jsonPointer) instanceof ErroredJsonNode) {
                final Throwable th = new JSONException("Cannot find JSON node: '" + jsonPointer + "'", ex);
                this.to.setThrowable(th);
                return new ErroredJsonNodes(group, th);
            } else {
                final Throwable th = new JSONException("Expected reference to JSON Array, but got reference to JSON Object: '" + jsonPointer + "'");
                this.to.setThrowable(th);
                return new ErroredJsonNodes(group, th);
            }
        }
    }
}
