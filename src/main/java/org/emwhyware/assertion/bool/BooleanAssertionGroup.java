package org.emwhyware.assertion.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;

public final class BooleanAssertionGroup {
    private final AssertionGroup group;

    public BooleanAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public BooleanTo expect(boolean actual) {
        return expect("", actual);
    }

    public BooleanTo expect(@NonNull String labelForActual, boolean actual) {
        return new BooleanTo(group, labelForActual, actual, false);
    }

    public BooleanTo expect(@Nullable Boolean actual) {
        return expect("", actual);
    }

    public BooleanTo expect(@NonNull String labelForActual, @Nullable Boolean actual) {
        return new BooleanTo(null, labelForActual, actual, false);
    }

}
