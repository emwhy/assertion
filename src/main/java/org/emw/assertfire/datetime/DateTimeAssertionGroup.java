package org.emw.assertfire.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.time.LocalDateTime;

public final class DateTimeAssertionGroup {
    private final AssertionGroup group;

    public DateTimeAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public DateTimeExpect expect(@Nullable LocalDateTime actualLocalDateTime) {
        return expect("", actualLocalDateTime);
    }

    public DateTimeExpect expect(@NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime) {
        return new DateTimeExpect(group, labelForActual, actualLocalDateTime, false);
    }
}
