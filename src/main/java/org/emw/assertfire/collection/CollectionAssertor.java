package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Collection;

/**
 * A list of <strong>expect</strong> assertion methods for Collections.
 *
 * @see org.emw.assertfire.bool.BooleanAssertor
 * @see org.emw.assertfire.string.StringAssertor
 * @see org.emw.assertfire.number.NumberAssertor
 * @see org.emw.assertfire.date.DateAssertor
 * @see org.emw.assertfire.datetime.DateTimeAssertor
 * @see org.emw.assertfire.time.TimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
 */
public interface CollectionAssertor {
    default CollectionExpect expect(@Nullable Object @Nullable[] actual) {
        return expect("", actual);
    }

    default CollectionExpect expect(@NonNull String labelForActual, @Nullable Object @Nullable[] actual) {
        return new CollectionExpect(null, labelForActual, actual == null ? null : Arrays.stream(actual).toList(), false, false, false);
    }

    default CollectionExpect expect(@Nullable Collection<?> actual) {
        return expect("", actual);
    }

    default CollectionExpect expect(@NonNull String labelForActual, @Nullable Collection<?> actual) {
        return new CollectionExpect(null, labelForActual, actual, false, false, false);
    }
}
