package org.emw.assertfire.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

/**
 * Provides assertion methods for {@link Boolean} values.
 * <p>
 * This class allows for fluent assertions on boolean states, including checks for
 * true values and nullity, with support for negated logic and custom labeling.
 */
public final class BooleanBeAssertionMethods extends AssertionMethods {
    private final @Nullable Boolean actual;

    /**
     * Constructs an instance to perform assertions on a specific boolean value.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the value being tested
     * @param actual the actual boolean value to assert against
     * @param negated whether the assertion logic should be inverted
     */
    BooleanBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
    }

    /**
     * Assert that the boolean value is {@code true}.
     * <p>
     * If negated, asserts that the value is {@code false} or {@code null}.
     */
    public void trueValue() {
        assertCondition(() -> {
            if (actual == null || negated == actual) {
                throw new AssertionError(partialAssertionErrorMessage() + "to be true.");
            }
        });
    }

    /**
     * Assert that the boolean value is {@code null}.
     * <p>
     * If negated, asserts that the value is not {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actual == null)) {
                throw new AssertionError(partialAssertionErrorMessage() + "to be null.");
            }
        });
    }

    /**
     * Generates a partial error message containing the actual value and label.
     *
     * @return a formatted string representing the start of an assertion error
     */
    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty()) {
            return "Expected '" + actual + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }
}
