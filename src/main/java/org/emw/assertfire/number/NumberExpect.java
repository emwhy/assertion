package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

public class NumberExpect extends Connector {
    public final NumberAllAssertionMethods to;

    protected NumberExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual);
        this.to = new NumberAllAssertionMethods(group, labelForActual, actual, negated);
    }
}
