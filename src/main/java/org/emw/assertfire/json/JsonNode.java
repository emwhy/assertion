package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;

import java.util.List;

/**
 * Represents a single JSON node within an assertion context.
 * <p>
 * This class allows for traversing JSON structures using JSON Pointers and provides
 * an entry point for performing assertions on the underlying data.
 */
public sealed class JsonNode extends JsonAssertion permits ErroredJsonNode {

    /**
     * Entry point for performing assertions against the value of this JSON node.
     */
    public final JsonNodeAllAssertionMethods to;

    /**
     * The underlying JSON object or value. May be a {@link JSONObject}, a primitive, or null.
     */
    protected final @Nullable Object nodeObject;

    /**
     * Internal constructor to initialize the node and its assertion bridge.
     *
     * @param group      The assertion group context.
     * @param nodeObject The actual data object represented by this node.
     */
    protected JsonNode(@NonNull JsonAssertionGroup group, @Nullable Object nodeObject) {
        super(group);
        this.nodeObject = nodeObject;
        this.to = new JsonNodeAllAssertionMethods(this.group, nodeObject, false, false, List.of());
    }

    /**
     * Retrieves a JSON node via pointer and performs a scoped action on it.
     *
     * @param jsonPointer JSON pointer string to the target node.
     * @param action      A functional interface to process the resulting node.
     */
    public void node(String jsonPointer, JsonNodeAction action) {
        action.inNode(this.node(jsonPointer));
    }

    /**
     * Navigates to a specific JSON node using a JSON pointer.
     * <p>
     * If the pointer cannot be resolved or points to a {@link JSONArray}, an
     * {@link ErroredJsonNode} is returned to capture the failure context.
     *
     * @param jsonPointer JSON pointer string to the target node.
     * @return A new {@link JsonNode} representing the value, or {@link ErroredJsonNode} if navigation fails.
     */
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

    /**
     * Navigates to a JSON array using a JSON pointer and returns a collection of nodes.
     * <p>
     * If the pointer points to a {@link JSONObject} or a primitive instead of an array,
     * an {@link ErroredJsonNodes} is returned.
     *
     * @param jsonPointer JSON pointer string to the target array.
     * @return A {@link JsonNodes} collection, or {@link ErroredJsonNodes} if navigation fails.
     */
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

    /**
     * Functional interface for performing operations within a specific JSON node scope.
     */
    public interface JsonNodeAction {
        /**
         * Invoked with the resolved JSON node.
         * @param node The node to operate on.
         */
        void inNode(@NonNull JsonNode node);
    }
}
