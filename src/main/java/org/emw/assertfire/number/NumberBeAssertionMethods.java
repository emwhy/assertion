package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

/**
 * Provides specialized state-based and comparative assertion methods for {@link Number} objects.
 * <p>
 * This class supports relative numeric comparisons (greater than, less than, inclusive ranges)
 * and nullity checks, ensuring appropriate precision handling between floating-point and
 * integer-based numeric types.
 */
public final class NumberBeAssertionMethods extends AssertionMethods {
    private final @Nullable Number actual;
    private final NumberAssertorHelper helper;

    /**
     * Constructs number state assertion methods.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the number being tested
     * @param actual the actual numeric value to assert against
     * @param negated whether the assertion logic should be inverted
     */
    NumberBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
        this.helper = new NumberAssertorHelper(labelForActual, actual,  negated);
    }

    /**
     * Assert that the actual value is strictly greater than the expected integer.
     * @param expected the threshold value to be exceeded
     */
    public void moreThan(int expected) { moreThan(Integer.valueOf(expected)); }
    /**
     * Assert that the actual value is strictly greater than the expected long.
     * @param expected the threshold value to be exceeded
     */
    public void moreThan(long expected) { moreThan(Long.valueOf(expected)); }
    /**
     * Assert that the actual value is strictly greater than the expected float.
     * @param expected the threshold value to be exceeded
     */
    public void moreThan(float expected) { moreThan(Float.valueOf(expected)); }
    /**
     * Assert that the actual value is strictly greater than the expected double.
     * @param expected the threshold value to be exceeded
     */
    public void moreThan(double expected) { moreThan(Double.valueOf(expected)); }

    /**
     * Assert that the actual value is strictly less than the expected integer.
     * @param expected the threshold value that the actual value must be under
     */
    public void lessThan(int expected) { lessThan(Integer.valueOf(expected)); }
    /**
     * Assert that the actual value is strictly less than the expected long.
     * @param expected the threshold value that the actual value must be under
     */
    public void lessThan(long expected) { lessThan(Long.valueOf(expected)); }
    /**
     * Assert that the actual value is strictly less than the expected float.
     * @param expected the threshold value that the actual value must be under
     */
    public void lessThan(float expected) { lessThan(Float.valueOf(expected)); }
    /**
     * Assert that the actual value is strictly less than the expected double.
     * @param expected the threshold value that the actual value must be under
     */
    public void lessThan(double expected) { lessThan(Double.valueOf(expected)); }

    /**
     * Assert that the actual value is greater than or equal to the expected integer.
     * @param expected the minimum inclusive threshold
     */
    public void moreThanOrEqual(int expected) { moreThanOrEqual(Integer.valueOf(expected)); }
    /**
     * Assert that the actual value is greater than or equal to the expected long.
     * @param expected the minimum inclusive threshold
     */
    public void moreThanOrEqual(long expected) { moreThanOrEqual(Long.valueOf(expected)); }
    /**
     * Assert that the actual value is greater than or equal to the expected float.
     * @param expected the minimum inclusive threshold
     */
    public void moreThanOrEqual(float expected) { moreThanOrEqual(Float.valueOf(expected)); }
    /**
     * Assert that the actual value is greater than or equal to the expected double.
     * @param expected the minimum inclusive threshold
     */
    public void moreThanOrEqual(double expected) { moreThanOrEqual(Double.valueOf(expected)); }

    /**
     * Assert that the actual value is less than or equal to the expected integer.
     * @param expected the maximum inclusive threshold
     */
    public void lessThanOrEqual(int expected) { lessThanOrEqual(Integer.valueOf(expected)); }
    /**
     * Assert that the actual value is less than or equal to the expected long.
     * @param expected the maximum inclusive threshold
     */
    public void lessThanOrEqual(long expected) { lessThanOrEqual(Long.valueOf(expected)); }
    /**
     * Assert that the actual value is less than or equal to the expected float.
     * @param expected the maximum inclusive threshold
     */
    public void lessThanOrEqual(float expected) { lessThanOrEqual(Float.valueOf(expected)); }
    /**
     * Assert that the actual value is less than or equal to the expected double.
     * @param expected the maximum inclusive threshold
     */
    public void lessThanOrEqual(double expected) { lessThanOrEqual(Double.valueOf(expected)); }

    /**
     * Assert that the actual number is within the inclusive range.
     * @param lower the inclusive lower bound
     * @param upper the inclusive upper bound
     */
    public void between(double lower, double upper) {
        between(Double.valueOf(lower), Double.valueOf(upper));
    }

    /**
     * Assert that the actual number is strictly greater than the expected value.
     * @param expected the threshold {@link Number}
     */
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

    /**
     * Assert that the actual number is strictly less than the expected value.
     * @param expected the threshold {@link Number}
     */
    public void lessThan(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be less than '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            } else {
                final boolean match = (actual instanceof Float || expected instanceof Float)
                        ? actual.floatValue() < expected.floatValue()
                        : actual.doubleValue() < expected.doubleValue();
                if (match == negated) {
                    throw new AssertionError(message);
                }
            }
        });
    }

    /**
     * Assert that the actual number is greater than or equal to the expected value.
     * @param expected the threshold {@link Number}
     */
    public void moreThanOrEqual(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be more than or equal '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            } else {
                final boolean match = (actual instanceof Float || expected instanceof Float)
                        ? actual.floatValue() >= expected.floatValue()
                        : actual.doubleValue() >= expected.doubleValue();
                if (match == negated) {
                    throw new AssertionError(message);
                }
            }
        });
    }

    /**
     * Assert that the actual number is less than or equal to the expected value.
     * @param expected the threshold {@link Number}
     */
    public void lessThanOrEqual(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be less than or equal '" + expected + "'");
            if (actual == null) {
                throw new AssertionError(message);
            } else {
                final boolean match = (actual instanceof Float || expected instanceof Float)
                        ? actual.floatValue() <= expected.floatValue()
                        : actual.doubleValue() <= expected.doubleValue();
                if (match == negated) {
                    throw new AssertionError(message);
                }
            }
        });
    }

    /**
     * Assert that the actual number is within the specified inclusive range.
     * @param expectedLower the inclusive lower bound {@link Number}
     * @param expectedUpper the inclusive upper bound {@link Number}
     */
    public void between(@NonNull Number expectedLower, @NonNull Number expectedUpper) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to be between '" + expectedLower + "' and '" + expectedUpper + "'");
            if (actual == null) {
                throw new AssertionError(message);
            } else {
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
            }
        });
    }

    /**
     * Assert that the actual number is {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actual == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
