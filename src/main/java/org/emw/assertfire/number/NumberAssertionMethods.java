package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

/**
 * Provides assertion methods for {@link Number} objects.
 * <p>
 * This class handles numeric equality comparisons across different primitive wrappers
 * (Integer, Long, Float, Double), ensuring precision is maintained during evaluation.
 */
public class NumberAssertionMethods extends AssertionMethods {
    /**
     * Accessor for specific state-based or relative numeric assertions (e.g., positive, negative, greater than).
     */
    public final NumberBeAssertionMethods be;
    private final @Nullable Number actual;
    private final NumberAssertorHelper helper;

    /**
     * Constructs number assertion methods with the specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the number being tested
     * @param actual the actual numeric value to assert against
     * @param negated whether the assertion logic should be inverted
     */
    NumberAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
        this.be = new NumberBeAssertionMethods(group, labelForActual, actual, negated);
        this.helper = new NumberAssertorHelper(labelForActual, actual, negated);
    }

    /**
     * Assert that the actual number is equal to the expected integer.
     * @param expected the expected int value
     */
    public void be(int expected) {
        be(Integer.valueOf(expected));
    }

    /**
     * Assert that the actual number is equal to the expected long.
     * @param expected the expected long value
     */
    public void be(long expected) {
        be(Long.valueOf(expected));
    }

    /**
     * Assert that the actual number is equal to the expected float.
     * @param expected the expected float value
     */
    public void be(float expected) {
        be(Float.valueOf(expected));
    }

    /**
     * Assert that the actual number is equal to the expected double.
     * @param expected the expected double value
     */
    public void be(double expected) {
        be(Double.valueOf(expected));
    }

    /**
     * Assert that the actual number is equal to the provided {@link Number}.
     * <p>
     * Performs comparison using float values if either input is a {@link Float},
     * otherwise defaults to double-precision comparison.
     * @param expected the expected {@link Number} value
     */
    public void be(@NonNull Number expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to equal '" + expected + "'");
            boolean match;

            if (actual == null) {
                throw new AssertionError(message);
            }

            if (actual instanceof Float || expected instanceof Float) {
                match = actual.floatValue() == expected.floatValue();
            } else {
                match = actual.doubleValue() == expected.doubleValue();
            }

            if (match == negated) {
                throw new AssertionError(message);
            }
        });
    }
}
