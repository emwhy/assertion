package org.emw.assertfire.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.Connector;

import java.time.LocalTime;

public class TimeExpect extends Connector {
    public final TimeNotBeAssertionMethods to;

    protected TimeExpect(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalTime actualLocalTime, boolean negated) {
        super(group, labelForActual);
        this.to = new TimeNotBeAssertionMethods(group, labelForActual, actualLocalTime, negated);
    }
}
