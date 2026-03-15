package org.emw.assertfire.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

public class BooleanNot extends Connector {
    public final BooleanBeAssertionMethods be;

    protected BooleanNot(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual);
        this.be = new BooleanBeAssertionMethods(group, labelForActual, actual, negated);
    }
}
