package org.emw.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Connector;

public class NumberTo extends Connector {
    public final NumberAllAssertionMethods to;

    protected NumberTo(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual);
        this.to = new NumberAllAssertionMethods(group, labelForActual, actual, negated);
    }
}
