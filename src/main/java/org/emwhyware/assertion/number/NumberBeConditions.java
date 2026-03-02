package org.emwhyware.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Conditions;

public final class NumberBeConditions extends Conditions {
    private final Number actual;

    NumberBeConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @NonNull Number actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
    }

    // --- moreThan ---
    public void moreThan(int expected) { moreThan(Integer.valueOf(expected)); }
    public void moreThan(long expected) { moreThan(Long.valueOf(expected)); }
    public void moreThan(float expected) { moreThan(Float.valueOf(expected)); }
    public void moreThan(double expected) { moreThan(Double.valueOf(expected)); }

    public void moreThan(@NonNull Number expected) {
        assertCondition(partialAssertionErrorMessage() + "to be more than '" + expected + "'.", () -> {
            if (actual == null) {
                return false;
            } else if (actual instanceof Float || expected instanceof Float) {
                return (actual.floatValue() > expected.floatValue()) != negated;
            } else {
                return (actual.doubleValue() > expected.doubleValue()) != negated;
            }
        });
    }

    // --- lessThan ---
    public void lessThan(int expected) { lessThan(Integer.valueOf(expected)); }
    public void lessThan(long expected) { lessThan(Long.valueOf(expected)); }
    public void lessThan(float expected) { lessThan(Float.valueOf(expected)); }
    public void lessThan(double expected) { lessThan(Double.valueOf(expected)); }

    public void lessThan(@NonNull Number expected) {
        assertCondition(partialAssertionErrorMessage() + "to be less than '" + expected + "'.", () -> {
            if (actual == null) {
                return false;
            } else if (actual instanceof Float || expected instanceof Float) {
                return (actual.floatValue() < expected.floatValue()) != negated;
            } else {
                return (actual.doubleValue() < expected.doubleValue()) != negated;
            }
        });
    }

    // --- moreThanOrEqual ---
    public void moreThanOrEqual(int expected) { moreThanOrEqual(Integer.valueOf(expected)); }
    public void moreThanOrEqual(long expected) { moreThanOrEqual(Long.valueOf(expected)); }
    public void moreThanOrEqual(float expected) { moreThanOrEqual(Float.valueOf(expected)); }
    public void moreThanOrEqual(double expected) { moreThanOrEqual(Double.valueOf(expected)); }

    public void moreThanOrEqual(@NonNull Number expected) {
        assertCondition(partialAssertionErrorMessage() + "to be more than or equal '" + expected + "'.", () -> {
            if (actual == null) {
                return false;
            } else if (actual instanceof Float || expected instanceof Float) {
                return (actual.floatValue() >= expected.floatValue()) != negated;
            } else {
                return (actual.doubleValue() >= expected.doubleValue()) != negated;
            }
        });
    }

    // --- lessThanOrEqual ---
    public void lessThanOrEqual(int expected) { lessThanOrEqual(Integer.valueOf(expected)); }
    public void lessThanOrEqual(long expected) { lessThanOrEqual(Long.valueOf(expected)); }
    public void lessThanOrEqual(float expected) { lessThanOrEqual(Float.valueOf(expected)); }
    public void lessThanOrEqual(double expected) { lessThanOrEqual(Double.valueOf(expected)); }

    public void lessThanOrEqual(@NonNull Number expected) {
        assertCondition(partialAssertionErrorMessage() + "to be less than or equal '" + expected + "'.", () -> {
            if (actual == null) {
                return false;
            }
            else if (actual instanceof Float || expected instanceof Float) {
                return (actual.floatValue() <= expected.floatValue()) != negated;
            } else {
                return (actual.doubleValue() <= expected.doubleValue()) != negated;
            }
        });
    }

    // --- between ---
    public void between(double lower, double upper) {
        between(Double.valueOf(lower), Double.valueOf(upper));
    }

    public void between(@NonNull Number expectedLower, @NonNull Number expectedUpper) {
        assertCondition(partialAssertionErrorMessage() + "to be between '" + expectedLower + "' and '" + expectedUpper + "'.", () -> {
            if (actual == null) {
                return false;
            } else if (actual instanceof Float || expectedLower instanceof Float || expectedUpper instanceof Float) {
                final float val = actual.floatValue();
                return (val >= expectedLower.floatValue() && val <= expectedUpper.floatValue()) != negated;
            } else {
                final double val = actual.doubleValue();
                return (val >= expectedLower.doubleValue() && val <= expectedUpper.doubleValue()) != negated;
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
