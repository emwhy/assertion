package org.emw.assertfire.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.time.LocalTime;

public final class TimeAssertionGroup {
    private final AssertionGroup group;

    public TimeAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public TimeExpect expect(@Nullable LocalTime actualLocalTime) {
        return expect("", actualLocalTime);
    }

    public TimeExpect expect(@NonNull String labelForActual, @Nullable LocalTime actualLocalTime) {
        return new TimeExpect(group, labelForActual, actualLocalTime, false);
    }
}
