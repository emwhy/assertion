package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

public class StringCaseSensitivelyAssertionMethods extends StringAssertionMethods {
    public final StringAssertionMethods not;

    StringCaseSensitivelyAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual, actual, negated, ignoreCase);
        this.not = new StringAssertionMethods(group, labelForActual, actual, !negated, ignoreCase);
    }
}
