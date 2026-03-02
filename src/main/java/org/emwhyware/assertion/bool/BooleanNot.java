package org.emwhyware.assertion.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;
import org.emwhyware.assertion.Connector;
import org.emwhyware.assertion.date.DateBeConditions;

import java.time.LocalDate;

public class BooleanNot extends Connector {
    public final BooleanBeConditions be;

    protected BooleanNot(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Boolean actual, boolean negated) {
        super(group, labelForActual);
        this.be = new BooleanBeConditions(group, labelForActual, actual, negated);
    }
}
