package org.emwhyware.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Conditions;

public class NumberConditions extends Conditions {
    public final NumberBeConditions be;
    private final Number actual;

    NumberConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
        this.be = new NumberBeConditions(group, labelForActual, actual, negated);
    }

    public void be(int expected) {
        be(Integer.valueOf(expected));
    }

    public void be(long expected) {
        be(Long.valueOf(expected));
    }

    public void be(float expected) {
        be(Float.valueOf(expected));
    }

    public void be(double expected) {
        be(Double.valueOf(expected));
    }

    public void be(@NonNull Number expected) {
        assertCondition(partialAssertionErrorMessage() + "to equal '" + expected + "'.", () -> {
            if (actual instanceof Float || expected instanceof Float) {
                return (actual.floatValue() == expected.floatValue()) != negated;
            } else {
                return (actual.doubleValue() == expected.doubleValue()) != negated;
            }
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
