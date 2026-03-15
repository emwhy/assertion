package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

import java.util.Collection;

public class CollectionExpect extends Connector {
    public final CollectionAllAssertionMethods to;

    protected CollectionExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual);
        this.to = new CollectionAllAssertionMethods(group, labelForActual, actual, negated, ignoreCase, anyOrder);
    }
}
