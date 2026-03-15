package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.util.Collection;

public class CollectionNotOnlyAssertionMethods extends CollectionAssertionMethods {
    public final CollectionAssertionMethods not;

    CollectionNotOnlyAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, actual, negated, ignoreCase, anyOrder);
        this.not = new CollectionAssertionMethods(group, labelForActual, actual, !negated, ignoreCase, anyOrder);
    }
}
