package org.emwhyware.assertion.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;

public class BooleanToOrNot extends BooleanTo {
    public final BooleanTo not;

    protected BooleanToOrNot(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual, actual, negated);
        this.not = new BooleanTo(group, labelForActual, actual, !negated);
    }
}
