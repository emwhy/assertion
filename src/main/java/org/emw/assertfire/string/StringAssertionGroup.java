package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

public final class StringAssertionGroup {
    private final AssertionGroup group;

    public StringAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public StringExpect expect(@Nullable String actual) {
        return expect("", actual);
    }

    public StringExpect expect(@NonNull String labelForActual, @Nullable String actual) {
        return new StringExpect(group, labelForActual, actual, false, false);
    }
}
