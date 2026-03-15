package org.emw.assertfire;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class Connector {
    protected final @NonNull String labelForActual;
    protected final @Nullable AssertionGroup group;

    protected Connector(@Nullable AssertionGroup group, @NonNull String labelForActual) {
        this.group = group;
        this.labelForActual = labelForActual;
    }
}
