package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ErroredJsonNode extends JsonNode {
    private final Throwable exception;

    protected ErroredJsonNode(@NonNull JsonAssertionGroup group, @NonNull Throwable exception) {
        super(group, null);
        this.exception = exception;
        this.to.setThrowable(exception);
    }

    public JsonNode node(String jsonPointer) {
        return new ErroredJsonNode(group, exception);
    }

    public JsonNodes nodes(String jsonPointer) {
        return new ErroredJsonNodes(group, exception);
    }
}
