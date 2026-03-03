package org.emw.assertion.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Conditions;

import java.util.Collection;

public final class CollectionBeConditions extends Conditions {
    private final @Nullable Collection<?> actualCollection;
    private final boolean anyOrder;

    CollectionBeConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, negated, ignoreCase);
        this.actualCollection = actual;
        this.anyOrder = anyOrder;
    }

    public void empty() {
        assertCondition(partialAssertionErrorMessage() + "to be empty.", () -> {
            return actualCollection != null && negated != actualCollection.isEmpty();
        });
    }

    public void nullValue() {
        assertCondition(partialAssertionErrorMessage() + "to be null.", () -> {
            return negated != (actualCollection == null);
        });
    }


    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty() && ignoreCase) {
            return "Ignoring cases, expected " + join(actualCollection) + (negated?" not":"") + " ";
        } else if (labelForActual.isEmpty() && !ignoreCase) {
            return "Expected " + join(actualCollection) + (negated?" not":"") + " ";
        } else if (ignoreCase) {
            return "Ignoring cases, expected actual value(" + join(actualCollection) + ") of '" + labelForActual + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value(" + join(actualCollection) + ") of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }

    private String join(@Nullable Collection<?> collection) {
        if (collection == null) {
            return "null";
        } else {
            return "'" + String.join(", ", collection.stream().map(o -> o == null ? "null" : o.toString()).toList()) + "'";
        }
    }
}
