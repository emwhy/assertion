package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.util.Collection;

public class CollectionNotAssertionMethods extends CollectionAssertionMethods {
    public final CollectionCaseInsensitivityOnlyAssertionMethods inAnyOrder;
    public final CollectionAnyOrderOnlyAssertionMethods caseInsensitively;

    CollectionNotAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, actual, negated, ignoreCase, anyOrder);
        this.inAnyOrder = new CollectionCaseInsensitivityOnlyAssertionMethods(group, labelForActual, actual, negated, ignoreCase, true);
        this.caseInsensitively = new CollectionAnyOrderOnlyAssertionMethods(group, labelForActual, actual, negated, true, anyOrder);
    }
}
