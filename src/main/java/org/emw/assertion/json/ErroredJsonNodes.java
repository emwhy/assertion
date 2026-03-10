package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Iterator;
import java.util.stream.Stream;

public final class ErroredJsonNodes extends JsonNodes implements Iterable<JsonNode> {
    private final Throwable exception;

    protected ErroredJsonNodes(@NonNull JsonAssertionGroup group, @NonNull Throwable exception) {
        super(group, null);
        this.exception = exception;
        this.to.setThrowable(exception);
    }

    public JsonNode get(int index) {
        return new ErroredJsonNode(group, exception);
    }

    public JsonNode first() {
        return new ErroredJsonNode(group, exception);
    }

    public JsonNode last() {
        return new ErroredJsonNode(group, exception);
    }

    @Override
    public @NonNull Iterator<JsonNode> iterator() {
        return stream().iterator();
    }

    public Stream<JsonNode> stream() {
        return Stream.empty();
    }
}
