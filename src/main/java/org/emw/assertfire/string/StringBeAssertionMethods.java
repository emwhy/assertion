package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.util.Arrays;
import java.util.Collection;

/**
 * Provides specialized state-based assertion methods for {@link String} objects.
 * <p>
 * This class includes checks for membership within a collection of strings,
 * as well as validations for string emptiness and nullity.
 */
public final class StringBeAssertionMethods extends AssertionMethods {
    private final @Nullable String actual;
    private final StringAssertorHelper helper;

    /**
     * Constructs string state assertion methods.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the string being tested
     * @param actual the actual string value to assert against
     * @param negated whether the assertion logic should be inverted
     * @param ignoreCase whether comparisons should ignore case
     */
    StringBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual, negated, ignoreCase);
        this.actual = actual;
        this.helper = new StringAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }

    /**
     * Assert that the actual string is one of the provided expected strings.
     * @param expectedTexts varargs of allowed string values
     */
    public void oneOf(String... expectedTexts) {
        this.oneOf(Arrays.asList(expectedTexts));
    }

    /**
     * Assert that the actual string is contained within the specified collection of strings.
     * <p>
     * If {@code ignoreCase} is enabled, the membership check is performed
     * using lowercase representations of both the actual value and the collection.
     * @param expectedTexts the collection of allowed string values
     */
    public void oneOf(Collection<String> expectedTexts) {
        assertCondition(() -> {
            String message = helper.assertionErrorMessage("to be one of '" + String.join("', '", expectedTexts) + "'");
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final Collection<String> testedExpectedTexts = ignoreCase ? expectedTexts.stream().map(String::toLowerCase).toList() : expectedTexts;

            if (actual == null || (testedExpectedTexts.contains(testedActual)) == negated) {
                throw new AssertionError(message);
            }
        });
    }

    /**
     * Assert that the actual string is empty (length is 0).
     * <p>
     * If negated, asserts that the string contains at least one character.
     */
    public void empty() {
        assertCondition(() -> {
            if (actual == null || negated == actual.isEmpty()) {
                throw new AssertionError(helper.assertionErrorMessage("to be empty"));
            }
        });
    }

    /**
     * Assert that the actual string value is {@code null}.
     * <p>
     * If negated, asserts that the string is not {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actual == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
