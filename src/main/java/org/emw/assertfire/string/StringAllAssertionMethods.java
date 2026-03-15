package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

public class StringAllAssertionMethods extends StringAssertionMethods {
    public final StringCaseSensitivelyAssertionMethods caseInsensitively;
    public final StringNotAssertionMethods not;

    StringAllAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual, actual, negated, ignoreCase);
        this.caseInsensitively = new StringCaseSensitivelyAssertionMethods(group, labelForActual, actual, negated, true);
        this.not = new StringNotAssertionMethods(group, labelForActual, actual, !negated, ignoreCase);
    }
}
