package org.emw.assertfire.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

public final class BooleanAssertionGroup {
    private final AssertionGroup group;

    public BooleanAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public BooleanExpect expect(boolean actual) {
        return expect("", actual);
    }

    public BooleanExpect expect(@NonNull String labelForActual, boolean actual) {
        return new BooleanExpect(group, labelForActual, actual, false);
    }

    public BooleanExpect expect(@Nullable Boolean actual) {
        return expect("", actual);
    }

    public BooleanExpect expect(@NonNull String labelForActual, @Nullable Boolean actual) {
        return new BooleanExpect(null, labelForActual, actual, false);
    }

}
