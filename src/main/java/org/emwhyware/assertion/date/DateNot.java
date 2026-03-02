package org.emwhyware.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Connector;

import java.time.LocalDate;

public class DateNot extends Connector {
    public final DateBeConditions be;

    protected DateNot(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actual, boolean negated) {
        super(group, labelForActual);
        this.be = new DateBeConditions(group, labelForActual, actual, negated);
    }
}
