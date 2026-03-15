package org.emw.assertfire.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for Boolean values.
 *
 * @see org.emw.assertfire.collection.CollectionAssertor
 * @see org.emw.assertfire.string.StringAssertor
 * @see org.emw.assertfire.number.NumberAssertor
 * @see org.emw.assertfire.date.DateAssertor
 * @see org.emw.assertfire.datetime.DateTimeAssertor
 * @see org.emw.assertfire.time.TimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
 */
public interface BooleanAssertor {
    default BooleanExpect expect(boolean actual) {
        return expect("", actual);
    }

    default BooleanExpect expect(@NonNull String labelForActual, boolean actual) {
        return new BooleanExpect(null, labelForActual, actual, false);
    }

    default BooleanExpect expect(@Nullable Boolean actual) {
        return expect("", actual);
    }

    default BooleanExpect expect(@NonNull String labelForActual, @Nullable Boolean actual) {
        return new BooleanExpect(null, labelForActual, actual, false);
    }
}
