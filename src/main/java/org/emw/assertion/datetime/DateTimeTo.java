package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Connector;

import java.time.LocalDateTime;

public class DateTimeTo extends Connector {
    public final DateTimeNotBeAssertionMethods to;

    protected DateTimeTo(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual);
        this.to = new DateTimeNotBeAssertionMethods(group, labelForActual, actualLocalDateTime, negated);
    }
}
