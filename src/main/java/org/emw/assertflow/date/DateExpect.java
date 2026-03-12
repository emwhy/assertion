package org.emw.assertflow.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertflow.AssertionGroup;
import org.emw.assertflow.Connector;

import java.time.LocalDate;

public class DateExpect extends Connector {
    public final DateNotBeAssertionMethods to;

    protected DateExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual);
        this.to = new DateNotBeAssertionMethods(group, labelForActual, actualLocalDate, negated);
    }
}
