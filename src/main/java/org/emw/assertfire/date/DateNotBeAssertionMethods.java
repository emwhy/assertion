package org.emw.assertfire.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.time.LocalDate;

public class DateNotBeAssertionMethods extends DateAssertionMethods {
    public final DateAssertionMethods not;

    protected DateNotBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual,  actualLocalDate, negated);
        this.not = new DateAssertionMethods(group, labelForActual, actualLocalDate, !negated);
    }
}
