package org.emw.assertflow.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertflow.AssertionGroup;
import org.emw.assertflow.Connector;

import java.time.LocalDateTime;

public class DateTimeExpect extends Connector {
    public final DateTimeNotBeAssertionMethods to;

    protected DateTimeExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual);
        this.to = new DateTimeNotBeAssertionMethods(group, labelForActual, actualLocalDateTime, negated);
    }
}
