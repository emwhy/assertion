package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;

import java.time.LocalDateTime;

public class DateTimeNotBe extends DateTimeConditions {
    public final DateTimeConditions not;

    protected DateTimeNotBe(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, actualLocalDateTime, negated);
        this.not = new DateTimeConditions(group, labelForActual, actualLocalDateTime, !negated);
    }
}
