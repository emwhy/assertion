package org.emw.assertion.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.util.regex.Pattern;

public class StringAssertionMethods extends AssertionMethods {
    public final StringBeAssertionMethods be;
    private final @Nullable String actual;
    private final StringAssertorHelper helper;

    StringAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual, negated, ignoreCase);
        this.actual = actual;
        this.be = new StringBeAssertionMethods(group, labelForActual, actual, negated, ignoreCase);
        this.helper = new StringAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }

    public void be(@NonNull String expected) {
        assertCondition(helper.assertionErrorMessage("to equal '" + expected + "'"), () -> {
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? expected.toLowerCase() : expected;

            return actual != null && negated != testedActual.equals(testedExpected);
        });
    }

    public void contain(@NonNull String expected) {
        assertCondition(helper.assertionErrorMessage("to contain '" + expected + "'"), () -> {
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? expected.toLowerCase() : expected;

            return actual != null && negated != testedActual.contains(testedExpected);
        });

    }

    public void startWith(@NonNull String prefix) {
        assertCondition(helper.assertionErrorMessage("to start with '" + prefix + "'"), () -> {
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? prefix.toLowerCase() : prefix;

            return actual != null && negated != testedActual.startsWith(testedExpected);
        });

    }

    public void endWith(@NonNull String suffix) {
        assertCondition(helper.assertionErrorMessage("to end with '" + suffix + "'"), () -> {
            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
            final String testedExpected = ignoreCase ? suffix.toLowerCase() : suffix;

            return actual != null && negated != testedActual.endsWith(testedExpected);
        });

    }

    public void match(@NonNull String regex) {
        final Pattern pattern = Pattern.compile(regex);

        assertCondition(helper.assertionErrorMessage("to match the pattern '" + regex + "'"), () -> {
            return actual != null && negated != pattern.matcher(actual).matches();
        });
    }
}
