package org.emw.assertfire.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for string values.
 *
 * @see org.emw.assertfire.collection.CollectionAssertor
 * @see org.emw.assertfire.bool.BooleanAssertor
 * @see org.emw.assertfire.number.NumberAssertor
 * @see org.emw.assertfire.date.DateAssertor
 * @see org.emw.assertfire.datetime.DateTimeAssertor
 * @see org.emw.assertfire.time.TimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
 */
public interface StringAssertor {
    default StringExpect expect(@Nullable String actual) {
        return expect("", actual);
    }

    default StringExpect expect(@NonNull String labelForActual, @Nullable String actual) {
        return new StringExpect(null, labelForActual, actual, false, false);
    }
}
