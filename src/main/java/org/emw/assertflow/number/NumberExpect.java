package org.emw.assertflow.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertflow.AssertionGroup;
import org.emw.assertflow.Connector;

public class NumberExpect extends Connector {
    public final NumberAllAssertionMethods to;

    protected NumberExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual);
        this.to = new NumberAllAssertionMethods(group, labelForActual, actual, negated);
    }
}
