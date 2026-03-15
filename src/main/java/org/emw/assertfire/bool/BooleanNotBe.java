package org.emw.assertfire.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

public class BooleanNotBe extends Connector {
    public final BooleanBeAssertionMethods be;
    public final BooleanNot not;

    protected BooleanNotBe(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual);
        this.be = new BooleanBeAssertionMethods(group, labelForActual, actual, negated);
        this.not = new BooleanNot(group, labelForActual, actual, !negated);
    }
}
