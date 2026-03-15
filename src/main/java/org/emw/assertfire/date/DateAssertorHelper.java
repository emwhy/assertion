package org.emw.assertfire.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

final class DateAssertorHelper {
    private final String labelForActual;
    private final @Nullable LocalDate actualLocalDate;
    private final boolean negated;
    private final DateTimeFormatter formatter;

    DateAssertorHelper(@NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated, @NonNull DateTimeFormatter formatter) {
        this.labelForActual = labelForActual;
        this.actualLocalDate = actualLocalDate;
        this.negated = negated;
        this.formatter = formatter;
    }

    String assertionErrorMessage(String message) {
        if (labelForActual.isEmpty()) {
            return "Expected '" + (actualLocalDate == null ? "null" : actualLocalDate.format(formatter)) + "'" + (negated?" not":"") + " " + message + ".";
        } else {
            return "Expected actual value('" + (actualLocalDate == null ? "null" : actualLocalDate.format(formatter)) + "') of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        }
    }
}
