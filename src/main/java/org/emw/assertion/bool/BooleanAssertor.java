package org.emw.assertion.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for Boolean values.
 *
 * @see org.emw.assertion.collection.CollectionAssertor
 * @see org.emw.assertion.string.StringAssertor
 * @see org.emw.assertion.number.NumberAssertor
 * @see org.emw.assertion.date.DateAssertor
 */
public interface BooleanAssertor {
    default BooleanTo expect(boolean actual) {
        return expect("", actual);
    }

    default BooleanTo expect(@NonNull String labelForActual, boolean actual) {
        return new BooleanTo(null, labelForActual, actual, false);
    }

    default BooleanTo expect(@Nullable Boolean actual) {
        return expect("", actual);
    }

    default BooleanTo expect(@NonNull String labelForActual, @Nullable Boolean actual) {
        return new BooleanTo(null, labelForActual, actual, false);
    }
}
