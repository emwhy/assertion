package org.emw.assertion.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for string values.
 *
 * @see org.emw.assertion.collection.CollectionAssertor
 * @see org.emw.assertion.bool.BooleanAssertor
 * @see org.emw.assertion.number.NumberAssertor
 * @see org.emw.assertion.date.DateAssertor
 */
public interface StringAssertor {
    default StringTo expect(@Nullable String actual) {
        return expect("", actual);
    }

    default StringTo expect(@NonNull String labelForActual, @Nullable String actual) {
        return new StringTo(null, labelForActual, actual, false, false);
    }
}
