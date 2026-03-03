package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Connector;

import java.time.LocalDate;

public class DateNotBe extends Connector {
    public final DateBeConditions be;
    public final DateNot not;

    protected DateNotBe(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual);
        this.be = new DateBeConditions(group, labelForActual, actualLocalDate, negated);
        this.not = new DateNot(group, labelForActual, actualLocalDate, !negated);
    }
}
