package org.emw.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

public class NumberAssertionMethods extends AssertionMethods {
    public final NumberBeAssertionMethods be;
    private final @Nullable Number actual;
    private final NumberAssertorHelper helper;

    NumberAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actual = actual;
        this.be = new NumberBeAssertionMethods(group, labelForActual, actual, negated);
        this.helper = new NumberAssertorHelper(labelForActual, actual, negated);
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
