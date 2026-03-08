package org.emw.assertion.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.util.Arrays;
import java.util.Collection;

public final class StringBeAssertionMethods extends AssertionMethods {
    private final @Nullable String actual;
    private final StringAssertorHelper helper;

    StringBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual, negated, ignoreCase);
        this.actual = actual;
        this.helper = new StringAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }


    public void oneOf(String... expectedTexts) {
        this.oneOf(Arrays.asList(expectedTexts));
    }

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

    public void empty() {
        assertCondition(() -> {
            if (actual == null || negated == actual.isEmpty()) {
                throw new AssertionError(helper.assertionErrorMessage("to be empty"));
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
