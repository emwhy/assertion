package org.emw.assertion;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class Conditions extends Connector {
    protected final boolean negated;
    protected final boolean ignoreCase;

    protected Conditions(@Nullable AssertionGroup group, @NonNull String labelForActual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual);
        this.negated = negated;
        this.ignoreCase = ignoreCase;
    }

    protected interface Assertion {
        boolean getResult();
    }

    protected final void assertCondition(String errorMessage, Assertion assertion) {
        if (!assertion.getResult()) {
            if (group == null) {
                throw new AssertionError(errorMessage);
            } else  {
                this.addToGroup(new AssertionError(errorMessage));
            }
        }
    }

    protected final void addToGroup(Throwable throwable) {
        if (this.group != null) {
            this.group.addThrowable(throwable);
        }
    }
}
