package org.emw.assertfire.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

final class TimeAssertorHelper {
    private final String labelForActual;
    private final @Nullable LocalTime actualLocalTime;
    private final boolean negated;
    private final DateTimeFormatter formatter;

    TimeAssertorHelper(@NonNull String labelForActual, @Nullable LocalTime actualLocalTime, boolean negated, @NonNull DateTimeFormatter formatter) {
        this.labelForActual = labelForActual;
        this.actualLocalTime = actualLocalTime;
        this.negated = negated;
        this.formatter = formatter;
    }

    String assertionErrorMessage(String message) {
        if (labelForActual.isEmpty()) {
            return "Expected '" + (actualLocalTime == null ? "null" : actualLocalTime.format(formatter)) + "'" + (negated?" not":"") + " " + message + ".";
        } else {
            return "Expected actual value('" + (actualLocalTime == null ? "null" : actualLocalTime.format(formatter)) + "') of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        }
    }

}
