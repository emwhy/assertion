package org.emw.assertfire.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalTime;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalTime}.
 *
 * @see org.emw.assertfire.collection.CollectionAssertor
 * @see org.emw.assertfire.string.StringAssertor
 * @see org.emw.assertfire.number.NumberAssertor
 * @see org.emw.assertfire.bool.BooleanAssertor
 * @see org.emw.assertfire.date.DateAssertor
 * @see org.emw.assertfire.datetime.DateTimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
 */
public interface TimeAssertor {
    default TimeExpect expect(@NonNull LocalTime actualLocalTime) {
        return expect("", actualLocalTime);
    }

    default TimeExpect expect(@NonNull String labelForActual, @Nullable LocalTime actualLocalTime) {
        return new TimeExpect(null, labelForActual, actualLocalTime, false);
    }
}
