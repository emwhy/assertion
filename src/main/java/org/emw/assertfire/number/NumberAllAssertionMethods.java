package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

public class NumberAllAssertionMethods extends NumberAssertionMethods {
    public final NumberAssertionMethods not;

    NumberAllAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, actual, negated);
        this.not =  new NumberAssertionMethods(group, labelForActual, actual, !negated);
    }
}
