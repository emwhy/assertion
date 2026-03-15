package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

public class StringExpect extends Connector {
    public final StringAllAssertionMethods to;

    protected StringExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual);
        this.to = new StringAllAssertionMethods(group, labelForActual, actual, negated, ignoreCase);
    }
}
