package org.emwhyware.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;

public class NumberAllConditions extends NumberConditions {
    public final NumberConditions not;

    NumberAllConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Number actual, boolean negated) {
        super(group, labelForActual, actual, negated);
        this.not =  new NumberConditions(group, labelForActual, actual, !negated);
    }
}
