package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.util.Collection;

public class CollectionCaseInsensitivelyAssertionMethods extends CollectionAssertionMethods {
    public final CollectionAnyOrderOnlyAssertionMethods not;
    public final CollectionNotOnlyAssertionMethods inAnyOrder;

    CollectionCaseInsensitivelyAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, actual, negated, ignoreCase, anyOrder);
        this.not = new CollectionAnyOrderOnlyAssertionMethods(group, labelForActual, actual, !negated, ignoreCase, anyOrder);
        this.inAnyOrder = new CollectionNotOnlyAssertionMethods(group, labelForActual, actual, negated, ignoreCase, true);
    }
}
