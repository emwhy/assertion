package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.util.Collection;

public class CollectionAllAssertionMethods extends CollectionAssertionMethods {
    public final CollectionNotAssertionMethods not;
    public final CollectionAnyOrderAssertionMethods inAnyOrder;
    public final CollectionCaseInsensitivelyAssertionMethods caseInsensitively;

    CollectionAllAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, actual, negated, ignoreCase, anyOrder);
        this.not = new CollectionNotAssertionMethods(group, labelForActual, actual, !negated, ignoreCase, anyOrder);
        this.inAnyOrder = new CollectionAnyOrderAssertionMethods(group, labelForActual, actual, negated, ignoreCase, true);
        this.caseInsensitively = new CollectionCaseInsensitivelyAssertionMethods(group, labelForActual, actual, negated, true, anyOrder);
    }
}
