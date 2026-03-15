package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;

final class CollectionAssertorHelper {
    private final String labelForActual;
    private final @Nullable Collection<?> actualCollection;
    private final boolean negated;
    private final boolean ignoreCase;

    CollectionAssertorHelper(@NonNull String labelForActual, @Nullable Collection<?> actualCollection, boolean negated, boolean ignoreCase) {
        this.labelForActual = labelForActual;
        this.actualCollection = actualCollection;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
    }

    String assertionErrorMessage(@NonNull String message) {
        if (labelForActual.isEmpty() && ignoreCase) {
            return "Ignoring cases, expected " + join(actualCollection) + (negated?" not":"") + " " + message + ".";
        } else if (labelForActual.isEmpty() && !ignoreCase) {
            return "Expected " + join(actualCollection) + (negated?" not":"") + " " + message + ".";
        } else if (ignoreCase) {
            return "Ignoring cases, expected actual value(" + join(actualCollection) + ") of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        } else {
            return "Expected actual value(" + join(actualCollection) + ") of '" + labelForActual + "'" + (negated?" not":"") + " " + message + ".";
        }
    }

    String join(@Nullable Collection<?> collection) {
        if (collection == null) {
            return "null";
        } else {
            return "'" + String.join("', '", collection.stream().map(o -> o == null ? "null" : o.toString()).toList()) + "'";
        }
    }
}
