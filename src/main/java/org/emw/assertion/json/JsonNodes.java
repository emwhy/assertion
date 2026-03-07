package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JsonNodes implements Iterable<JsonNode> {
    public final JsonArrayAssertionMethods to;
    protected final JSONArray jsonArray;

    protected JsonNodes(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.to = new JsonArrayAssertionMethods(jsonArray, false, false);
    }

    public JsonNode get(int index) {
        return this.stream().toList().get(index);
    }

    public JsonNode first() {
        return this.get(0);
    }

    public JsonNode last() {
        return this.get(this.jsonArray.length() - 1);
    }

    @Override
    public @NonNull Iterator<JsonNode> iterator() {
        return stream().iterator();
    }

    public Stream<JsonNode> stream() {
        return StreamSupport.stream(jsonArray.spliterator(), false).map(JsonNode::new);
    }
}
