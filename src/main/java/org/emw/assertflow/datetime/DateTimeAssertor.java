package org.emw.assertflow.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDateTime}.
 *
 * @see org.emw.assertflow.collection.CollectionAssertor
 * @see org.emw.assertflow.string.StringAssertor
 * @see org.emw.assertflow.number.NumberAssertor
 * @see org.emw.assertflow.bool.BooleanAssertor
 * @see org.emw.assertflow.date.DateAssertor
 * @see org.emw.assertflow.time.TimeAssertor
 * @see org.emw.assertflow.json.JsonAssertor
 */
public interface DateTimeAssertor {
    default DateTimeExpect expect(@NonNull LocalDateTime actualLocalDateTime) {
        return expect("", actualLocalDateTime);
    }

    default DateTimeExpect expect(@NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime) {
        return new DateTimeExpect(null, labelForActual, actualLocalDateTime, false);
    }
}
