package org.emw.assertfire;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AssertionMethods extends Connector {
    protected final boolean negated;
    protected final boolean ignoreCase;

    protected AssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, boolean negated, boolean ignoreCase) {
        super(group, labelForActual);
        this.negated = negated;
        this.ignoreCase = ignoreCase;
    }

    protected interface AssertionAction {
        void doAssertiveAction();
    }

    protected final void assertCondition(@NonNull AssertionAction assertion) {
        try {
            assertion.doAssertiveAction();
        } catch (Throwable th) {
            if (group == null) {
                throw th;
            } else {
                this.addToGroup(th);
            }
        }
    }

    protected final void addToGroup(@NonNull Throwable throwable) {
        if (this.group != null) {
            this.group.addThrowable(throwable);
        }
    }
}
