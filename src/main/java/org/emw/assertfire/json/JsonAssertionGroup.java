package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertfire.exception.AssertionGroupError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JsonAssertionGroup {
    private final List<Throwable> throwables = new ArrayList<>();

    JsonAssertionGroup() {
    }

    protected static void group(@NonNull GroupAction action) {
        final JsonAssertionGroup assertionGroup = new JsonAssertionGroup();

        try {
            action.doAssertions(assertionGroup);
        } finally {
            if (!assertionGroup.throwables.isEmpty()) {
                throw new AssertionGroupError("JSON Assertion", assertionGroup.throwables);
            }
        }
    }

    protected List<Throwable> throwables() {
        return Collections.unmodifiableList(throwables);
    }

    protected void addThrowable(@NonNull Throwable throwable) {
        this.throwables.add(throwable);
    }

    public interface GroupAction {
        void doAssertions(JsonAssertionGroup g);
    }
}
