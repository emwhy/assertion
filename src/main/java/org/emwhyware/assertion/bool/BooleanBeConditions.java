package org.emwhyware.assertion.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Conditions;

public final class BooleanBeConditions extends Conditions {
    private final Boolean actual;

    BooleanBeConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
    }

    public void trueValue() {
        assertCondition(partialAssertionErrorMessage() + "to be true.", () -> {
            if (actual == null) {
                return false;
            } else {
                return negated != actual;
            }
        });
    }

    public void nullValue() {
        assertCondition(partialAssertionErrorMessage() + "to be null.", () -> {
            return negated != (actual == null);
        });
    }

    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty()) {
            return "Expected '" + actual + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }
}
