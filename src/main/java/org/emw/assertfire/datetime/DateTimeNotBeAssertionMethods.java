package org.emw.assertfire.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.time.LocalDateTime;

public class DateTimeNotBeAssertionMethods extends DateTimeAssertionMethods {
    public final DateTimeAssertionMethods not;

    protected DateTimeNotBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, actualLocalDateTime, negated);
        this.not = new DateTimeAssertionMethods(group, labelForActual, actualLocalDateTime, !negated);
    }
}
