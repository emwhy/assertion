package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Connector;

import java.time.LocalDate;

public class DateTo extends Connector {
    public final DateNotBe to;

    protected DateTo(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual);
        this.to = new DateNotBe(group, labelForActual, actualLocalDate, negated);
    }
}
