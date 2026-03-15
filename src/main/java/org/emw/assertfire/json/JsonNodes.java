package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Represents a collection of JSON nodes, providing assertion capabilities and stream-based access.
 * This class facilitates operations on {@link JSONArray} structures within an assertion context.
 */
public sealed class JsonNodes extends JsonAssertion implements Iterable<JsonNode> permits ErroredJsonNodes {

    /**
     * Entry point for performing various assertions against this collection of nodes.
     */
    public final JsonNodesAllAssertionMethods to;

    /**
     * The underlying JSON array, may be null if the path did not resolve to an array.
     */
    protected final @Nullable JSONArray jsonArray;

    /**
     * Internal constructor to initialize the node collection and its assertion bridge.
     *
     * @param group     The assertion group context.
     * @param jsonArray The underlying {@link JSONArray} data.
     */
    protected JsonNodes(@NonNull JsonAssertionGroup group, @Nullable JSONArray jsonArray) {
        super(group);
        this.jsonArray = jsonArray;
        this.to = new JsonNodesAllAssertionMethods(this.group, jsonArray, false, false, false, List.of());
    }

    /**
     * Retrieves the node at the specified index.
     *
     * @param index The zero-based index of the node to retrieve.
     * @return The {@link JsonNode} at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public JsonNode get(int index) {
        return this.stream().toList().get(index);
    }

    /**
     * Retrieves the first node in the collection.
     *
     * @return The first {@link JsonNode}.
     * @throws IndexOutOfBoundsException if the collection is empty.
     */
    public JsonNode first() {
        return this.stream().toList().get(0);
    }

    /**
     * Retrieves the last node in the collection.
     *
     * @return The last {@link JsonNode}.
     * @throws IndexOutOfBoundsException if the collection is empty.
     */
    public JsonNode last() {
        final List<JsonNode> list = this.stream().toList();
        return list.get(list.size() - 1);
    }

    /**
     * Performs the given action for each element of the {@code Iterable} until all elements
     * have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    public void forEach(Consumer<? super JsonNode> action) {
        stream().forEach(action);
    }

    /**
     * Returns an iterator over elements of type {@link JsonNode}.
     *
     * @return An {@link Iterator}.
     */
    @Override
    public @NonNull Iterator<JsonNode> iterator() {
        return stream().iterator();
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * @return A {@link Stream} of {@link JsonNode} objects; returns an empty stream if the underlying array is null.
     */
    public Stream<JsonNode> stream() {
        if (this.jsonArray == null) {
            return Stream.empty();
        } else {
            return StreamSupport.stream(jsonArray.spliterator(), false).map(node -> new JsonNode(this.group, node));
        }
    }
}
