package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class StringAssertorHelper {
    private final String labelForActual;
    private final @Nullable String actual;
    private final boolean negated;
    private final boolean ignoreCase;

    StringAssertorHelper(@NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        this.labelForActual = labelForActual;
        this.actual = actual;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
    }

    String assertionErrorMessage(@NonNull String message) {
        if (labelForActual.isEmpty() && ignoreCase) {
            return "Ignoring cases, expected '" + actual + "'" + (negated?" not":"") + " " + message + ".";
        } else if (labelForActual.isEmpty() && !ignoreCase) {
            return "Expected '" + actual + "'" + (negated?" not":"") + " " + message + ".";
        } else if (ignoreCase) {
            return "Ignoring cases, expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        } else {
            return "Expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        }
    }
}
