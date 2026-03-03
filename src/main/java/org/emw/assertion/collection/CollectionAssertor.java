package org.emw.assertion.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.datetime.DateTimeAssertor;

import java.util.Arrays;
import java.util.Collection;

/**
 * A list of <strong>expect</strong> assertion methods for Collections.
 *
 * @see org.emw.assertion.bool.BooleanAssertor
 * @see org.emw.assertion.string.StringAssertor
 * @see org.emw.assertion.number.NumberAssertor
 * @see org.emw.assertion.date.DateAssertor
 * @see DateTimeAssertor
 */
public interface CollectionAssertor {
    default CollectionTo expect(@Nullable Object @Nullable[] actual) {
        return expect("", actual);
    }

    default CollectionTo expect(@NonNull String labelForActual, @Nullable Object @Nullable[] actual) {
        return new CollectionTo(null, labelForActual, actual == null ? null : Arrays.stream(actual).toList(), false, false, false);
    }

    default CollectionTo expect(@Nullable Collection<?> actual) {
        return expect("", actual);
    }

    default CollectionTo expect(@NonNull String labelForActual, @Nullable Collection<?> actual) {
        return new CollectionTo(null, labelForActual, actual, false, false, false);
    }
}
