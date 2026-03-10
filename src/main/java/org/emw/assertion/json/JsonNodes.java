package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONArray;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public sealed class JsonNodes extends JsonAssertion implements Iterable<JsonNode> permits ErroredJsonNodes {
    public final JsonNodesAllAssertionMethods to;
    protected final @Nullable JSONArray jsonArray;

    protected JsonNodes(@NonNull JsonAssertionGroup group, @Nullable JSONArray jsonArray) {
        super(group);
        this.jsonArray = jsonArray;
        this.to = new JsonNodesAllAssertionMethods(this.group, jsonArray, false, false, false, List.of());
    }

    public JsonNode get(int index) {
        return this.stream().toList().get(index);
    }

    public JsonNode first() {
        return this.stream().toList().getFirst();
    }

    public JsonNode last() {
        return this.stream().toList().getLast();
    }

    @Override
    public @NonNull Iterator<JsonNode> iterator() {
        return stream().iterator();
    }

    public Stream<JsonNode> stream() {
        if (this.jsonArray == null) {
            return Stream.empty();
        } else {
            return StreamSupport.stream(jsonArray.spliterator(), false).map(node -> new JsonNode(this.group, node));
        }
    }
}
