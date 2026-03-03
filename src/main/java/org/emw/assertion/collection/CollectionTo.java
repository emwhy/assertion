package org.emw.assertion.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Connector;

import java.util.Collection;

public class CollectionTo extends Connector {
    public final CollectionAllAssertionMethods to;

    protected CollectionTo(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual);
        this.to = new CollectionAllAssertionMethods(group, labelForActual, actual, negated, ignoreCase, anyOrder);
    }
}
