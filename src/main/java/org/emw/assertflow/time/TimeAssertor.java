package org.emw.assertflow.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalTime;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalTime}.
 *
 * @see org.emw.assertflow.collection.CollectionAssertor
 * @see org.emw.assertflow.string.StringAssertor
 * @see org.emw.assertflow.number.NumberAssertor
 * @see org.emw.assertflow.bool.BooleanAssertor
 * @see org.emw.assertflow.date.DateAssertor
 * @see org.emw.assertflow.datetime.DateTimeAssertor
 * @see org.emw.assertflow.json.JsonAssertor
 */
public interface TimeAssertor {
    default TimeExpect expect(@NonNull LocalTime actualLocalTime) {
        return expect("", actualLocalTime);
    }

    default TimeExpect expect(@NonNull String labelForActual, @Nullable LocalTime actualLocalTime) {
        return new TimeExpect(null, labelForActual, actualLocalTime, false);
    }
}
