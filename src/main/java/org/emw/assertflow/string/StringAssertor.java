package org.emw.assertflow.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for string values.
 *
 * @see org.emw.assertflow.collection.CollectionAssertor
 * @see org.emw.assertflow.bool.BooleanAssertor
 * @see org.emw.assertflow.number.NumberAssertor
 * @see org.emw.assertflow.date.DateAssertor
 * @see org.emw.assertflow.datetime.DateTimeAssertor
 * @see org.emw.assertflow.time.TimeAssertor
 * @see org.emw.assertflow.json.JsonAssertor
 */
public interface StringAssertor {
    default StringExpect expect(@Nullable String actual) {
        return expect("", actual);
    }

    default StringExpect expect(@NonNull String labelForActual, @Nullable String actual) {
        return new StringExpect(null, labelForActual, actual, false, false);
    }
}
