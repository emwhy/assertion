package org.emw.assertion.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Connector;

public class StringTo extends Connector {
    public final StringAllAssertionMethods to;

    protected StringTo(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual);
        this.to = new StringAllAssertionMethods(group, labelForActual, actual, negated, ignoreCase);
    }
}
