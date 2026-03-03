package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDateTime}.
 *
 * @see org.emw.assertion.collection.CollectionAssertor
 * @see org.emw.assertion.string.StringAssertor
 * @see org.emw.assertion.number.NumberAssertor
 * @see org.emw.assertion.bool.BooleanAssertor
 * @see org.emw.assertion.date.DateAssertor
 */
public interface DateTimeAssertor {
    default DateTimeTo expect(@NonNull LocalDateTime actualLocalDateTime) {
        return expect("", actualLocalDateTime);
    }

    default DateTimeTo expect(@NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime) {
        return new DateTimeTo(null, labelForActual, actualLocalDateTime, false);
    }
}
