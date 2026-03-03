package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final @Nullable LocalDateTime actualLocalDateTime;
    public final DateTimeBeAssertionMethods be;

    protected DateTimeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDateTime = actualLocalDateTime;
        this.be = new DateTimeBeAssertionMethods(group, labelForActual, actualLocalDateTime, negated);
    }

    public void be(@NonNull LocalDateTime expected) {
        assertCondition(partialAssertionErrorMessage() + "to be '" + expected.format(DATE_FORMATTER) + "'.", () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isEqual(expected) != negated;
            }
        });
    }

    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty()) {
            return "Expected '" + (actualLocalDateTime == null ? "null" : actualLocalDateTime.format(DATETIME_FORMATTER)) + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value('" + (actualLocalDateTime == null ? "null" : actualLocalDateTime.format(DATETIME_FORMATTER)) + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }

}
