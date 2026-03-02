package org.emwhyware.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Connector;

import java.time.LocalDate;

public class DateNotBe extends Connector {
    public final DateBeConditions be;
    public final DateNot not;

    protected DateNotBe(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actual, boolean negated) {
        super(group, labelForActual);
        this.be = new DateBeConditions(group, labelForActual, actual, negated);
        this.not = new DateNot(group, labelForActual, actual, !negated);
    }
}
