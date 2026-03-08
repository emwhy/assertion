package org.emw.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

public final class NumberBeAssertionMethods extends AssertionMethods {
    private final @Nullable Number actual;
    private final NumberAssertorHelper helper;

    NumberBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
        this.helper = new NumberAssertorHelper(labelForActual, actual,  negated);
    }

    // --- moreThan ---
    public void moreThan(int expected) { moreThan(Integer.valueOf(expected)); }
    public void moreThan(long expected) { moreThan(Long.valueOf(expected)); }
    public void moreThan(float expected) { moreThan(Float.valueOf(expected)); }
    public void moreThan(double expected) { moreThan(Double.valueOf(expected)); }

    // --- lessThan ---
    public void lessThan(int expected) { lessThan(Integer.valueOf(expected)); }
    public void lessThan(long expected) { lessThan(Long.valueOf(expected)); }
    public void lessThan(float expected) { lessThan(Float.valueOf(expected)); }
    public void lessThan(double expected) { lessThan(Double.valueOf(expected)); }

    // --- moreThanOrEqual ---
    public void moreThanOrEqual(int expected) { moreThanOrEqual(Integer.valueOf(expected)); }
    public void moreThanOrEqual(long expected) { moreThanOrEqual(Long.valueOf(expected)); }
    public void moreThanOrEqual(float expected) { moreThanOrEqual(Float.valueOf(expected)); }
    public void moreThanOrEqual(double expected) { moreThanOrEqual(Double.valueOf(expected)); }

    // --- lessThanOrEqual ---
    public void lessThanOrEqual(int expected) { lessThanOrEqual(Integer.valueOf(expected)); }
    public void lessThanOrEqual(long expected) { lessThanOrEqual(Long.valueOf(expected)); }
    public void lessThanOrEqual(float expected) { lessThanOrEqual(Float.valueOf(expected)); }
    public void lessThanOrEqual(double expected) { lessThanOrEqual(Double.valueOf(expected)); }

    // --- between ---
    public void between(double lower, double upper) {
        between(Double.valueOf(lower), Double.valueOf(upper));
    }

    public void moreThan(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be more than '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            }
            final boolean match = (actual instanceof Float || expected instanceof Float)
                    ? actual.floatValue() > expected.floatValue()
                    : actual.doubleValue() > expected.doubleValue();
            if (match == negated) {
                throw new AssertionError(message);
            }
        });
    }

    public void lessThan(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be less than '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            }
            final boolean match = (actual instanceof Float || expected instanceof Float)
                    ? actual.floatValue() < expected.floatValue()
                    : actual.doubleValue() < expected.doubleValue();
            if (match == negated) {
                throw new AssertionError(message);
            }
        });
    }

    public void moreThanOrEqual(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be more than or equal '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            }
            final boolean match = (actual instanceof Float || expected instanceof Float)
                    ? actual.floatValue() >= expected.floatValue()
                    : actual.doubleValue() >= expected.doubleValue();
            if (match == negated) {
                throw new AssertionError(message);
            }
        });
    }

    public void lessThanOrEqual(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be less than or equal '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            }
            final boolean match = (actual instanceof Float || expected instanceof Float)
                    ? actual.floatValue() <= expected.floatValue()
                    : actual.doubleValue() <= expected.doubleValue();
            if (match == negated) {
                throw new AssertionError(message);
            }
        });
    }

    public void between(@NonNull Number expectedLower, @NonNull Number expectedUpper) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be between '" + expectedLower + "' and '" + expectedUpper + "'");
            if (actual == null) {
                throw new AssertionError(message);
            }
            boolean match;
            if (actual instanceof Float || expectedLower instanceof Float || expectedUpper instanceof Float) {
                final float val = actual.floatValue();
                match = (val >= expectedLower.floatValue() && val <= expectedUpper.floatValue());
            } else {
                final double val = actual.doubleValue();
                match = (val >= expectedLower.doubleValue() && val <= expectedUpper.doubleValue());
            }
            if (match == negated) {
                throw new AssertionError(message);
            }
        });
    }

    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actual == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
