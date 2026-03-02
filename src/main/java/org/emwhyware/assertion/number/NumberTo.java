package org.emwhyware.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Connector;

public class NumberTo extends Connector {
    public final NumberAllConditions to;

    protected NumberTo(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual);
        this.to = new NumberAllConditions(group, labelForActual, actual, negated);
    }
}
