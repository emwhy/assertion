package org.emw.assertion.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.util.Collection;

public final class CollectionBeAssertionMethods extends AssertionMethods {
    private final @Nullable Collection<?> actualCollection;
    private final CollectionAssertorHelper helper;

    CollectionBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, negated, ignoreCase);
        this.actualCollection = actual;
        this.helper = new CollectionAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }

    public void empty() {
        assertCondition(() -> {
            if (actualCollection == null || negated == actualCollection.isEmpty()) {
                throw new AssertionError(helper.assertionErrorMessage("to be empty"));
            }
        });
    }

    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualCollection == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }
}
