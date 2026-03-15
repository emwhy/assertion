package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.util.regex.Pattern;

/**
 * Provides assertion methods for {@link String} objects.
 * <p>
 * This class includes standard string comparisons such as equality, containment,
 * and prefix/suffix checks, with built-in support for case-insensitivity and
 * regular expression matching.
 */
public class StringAssertionMethods extends AssertionMethods {
    /**
     * Accessor for specific state-based string assertions (e.g., empty, null, numeric).
     */
    public final StringBeAssertionMethods be;
    private final @Nullable String actual;
    private final StringAssertorHelper helper;

    /**
     * Constructs string assertion methods with the specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the string being tested
     * @param actual the actual string value to assert against
     * @param negated whether the assertion logic should be inverted
     * @param ignoreCase whether comparisons should ignore case
     */
    StringAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual, negated, ignoreCase);
        this.actual = actual;
        this.be = new StringBeAssertionMethods(group, labelForActual, actual, negated, ignoreCase);
        this.helper = new StringAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }

    /**
     * Assert that the actual string is equal to the expected string.
     * @param expected the expected string value
     */
    public void be(@NonNull String expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to equal '" + expected + "'");
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? expected.toLowerCase() : expected;

            if (actual == null || negated == testedActual.equals(testedExpected)) {
                throw new AssertionError(message);
            }
        });
    }

    /**
     * Assert that the actual string contains the specified sequence.
     * @param expected the string sequence expected to be found
     */
    public void contain(@NonNull String expected) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to contain '" + expected + "'");
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? expected.toLowerCase() : expected;

            if (actual == null || negated == testedActual.contains(testedExpected)) {
                throw new AssertionError(message);
            }
        });
    }

    /**
     * Assert that the actual string starts with the specified prefix.
     * @param prefix the expected starting string sequence
     */
    public void startWith(@NonNull String prefix) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to start with '" + prefix + "'");
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? prefix.toLowerCase() : prefix;

            if (actual == null || negated == testedActual.startsWith(testedExpected)) {
                throw new AssertionError(message);
            }
        });
    }

    /**
     * Assert that the actual string ends with the specified suffix.
     * @param suffix the expected ending string sequence
     */
    public void endWith(@NonNull String suffix) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to end with '" + suffix + "'");
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? suffix.toLowerCase() : suffix;

            if (actual == null || negated == testedActual.endsWith(testedExpected)) {
                throw new AssertionError(message);
            }
        });
    }

    /**
     * Assert that the actual string matches the provided regular expression.
     * @param regex the regular expression pattern to match against
     */
    public void match(@NonNull String regex) {
        final Pattern pattern = Pattern.compile(regex);
        assertCondition(() -> {
            if (actual == null || negated == pattern.matcher(actual).matches()) {
                throw new AssertionError(helper.assertionErrorMessage("to match the pattern '" + regex + "'"));
            }
        });
    }

}
