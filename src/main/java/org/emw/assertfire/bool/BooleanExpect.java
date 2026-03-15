package org.emw.assertfire.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

public class BooleanExpect extends Connector {
    public final BooleanNotBe to;

    protected BooleanExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual);
        this.to = new BooleanNotBe(group, labelForActual, actual, negated);
    }
}
