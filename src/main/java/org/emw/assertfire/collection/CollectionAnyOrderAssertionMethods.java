package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.util.Collection;

public class CollectionAnyOrderAssertionMethods extends CollectionAssertionMethods {
    public final CollectionCaseInsensitivityOnlyAssertionMethods not;
    public final CollectionNotOnlyAssertionMethods caseInsensitively;

    CollectionAnyOrderAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, actual, negated, ignoreCase, anyOrder);
        this.not = new CollectionCaseInsensitivityOnlyAssertionMethods(group, labelForActual, actual, !negated, ignoreCase, anyOrder);
        this.caseInsensitively = new CollectionNotOnlyAssertionMethods(group, labelForActual, actual, negated, true, anyOrder);
    }
}
