package org.emw.assertflow.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for Boolean values.
 *
 * @see org.emw.assertflow.collection.CollectionAssertor
 * @see org.emw.assertflow.string.StringAssertor
 * @see org.emw.assertflow.number.NumberAssertor
 * @see org.emw.assertflow.date.DateAssertor
 * @see org.emw.assertflow.datetime.DateTimeAssertor
 * @see org.emw.assertflow.time.TimeAssertor
 * @see org.emw.assertflow.json.JsonAssertor
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
