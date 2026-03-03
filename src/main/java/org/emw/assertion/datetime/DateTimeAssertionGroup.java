package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;

import java.time.LocalDateTime;

public final class DateTimeAssertionGroup {
    private final AssertionGroup group;

    public DateTimeAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public DateTimeTo expect(@Nullable LocalDateTime actualLocalDateTime) {
        return expect("", actualLocalDateTime);
    }

    public DateTimeTo expect(@NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime) {
        return new DateTimeTo(group, labelForActual, actualLocalDateTime, false);
    }
}
