package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class NumberAssertorHelper {
    private final String labelForActual;
    private final @Nullable Number actual;
    private final boolean negated;

    NumberAssertorHelper(@NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        this.labelForActual = labelForActual;
        this.actual = actual;
        this.negated = negated;
    }

    String assertionErrorMessage(String message) {
        if (labelForActual.isEmpty()) {
            return "Expected '" + actual + "'" + (negated?" not":"") + " " + message + ".";
        } else {
            return "Expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        }
    }
}
