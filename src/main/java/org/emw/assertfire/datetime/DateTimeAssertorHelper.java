package org.emw.assertfire.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final class DateTimeAssertorHelper {
    private final String labelForActual;
    private final @Nullable LocalDateTime actualLocalDateTime;
    private final boolean negated;
    private final DateTimeFormatter formatter;

    DateTimeAssertorHelper(@NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated, @NonNull DateTimeFormatter formatter) {
        this.labelForActual = labelForActual;
        this.actualLocalDateTime = actualLocalDateTime;
        this.negated = negated;
        this.formatter = formatter;
    }

    String assertionErrorMessage(String message) {
        if (labelForActual.isEmpty()) {
            return "Expected '" + (actualLocalDateTime == null ? "null" : actualLocalDateTime.format(formatter)) + "'" + (negated?" not":"") + " " + message + ".";
        } else {
            return "Expected actual value('" + (actualLocalDateTime == null ? "null" : actualLocalDateTime.format(formatter)) + "') of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        }
    }

}
