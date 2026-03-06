package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final DateBeAssertionMethods be;
    private final @Nullable LocalDate actualLocalDate;
    private final DateAssertorHelper helper;

    protected DateAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual, negated, false);
        this.be = new DateBeAssertionMethods(group, labelForActual, actualLocalDate, negated);
        this.actualLocalDate = actualLocalDate;
        this.helper = new DateAssertorHelper(labelForActual, actualLocalDate, negated,  FORMATTER);
    }

    public void be(@NonNull Date expected) {
        this.be(expected.toLocalDate());
    }

    public void be(@NonNull LocalDate expected) {
        assertCondition(helper.assertionErrorMessage("to be '" + expected.format(FORMATTER) + "'"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isEqual(expected) != negated;
            }
        });
    }

}
