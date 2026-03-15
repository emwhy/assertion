package org.emw.assertfire.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDateTime}.
 *
 * @see org.emw.assertfire.collection.CollectionAssertor
 * @see org.emw.assertfire.string.StringAssertor
 * @see org.emw.assertfire.number.NumberAssertor
 * @see org.emw.assertfire.bool.BooleanAssertor
 * @see org.emw.assertfire.date.DateAssertor
 * @see org.emw.assertfire.time.TimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
 */
public interface DateTimeAssertor {
    default DateTimeExpect expect(@NonNull LocalDateTime actualLocalDateTime) {
        return expect("", actualLocalDateTime);
    }

    default DateTimeExpect expect(@NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime) {
        return new DateTimeExpect(null, labelForActual, actualLocalDateTime, false);
    }
}
