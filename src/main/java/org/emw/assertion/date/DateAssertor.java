package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.datetime.DateTimeAssertor;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDate} and {@link Date}.
 *
 * @see org.emw.assertion.collection.CollectionAssertor
 * @see org.emw.assertion.string.StringAssertor
 * @see org.emw.assertion.number.NumberAssertor
 * @see org.emw.assertion.bool.BooleanAssertor
 * @see DateTimeAssertor
 */
public interface DateAssertor {
    default DateTo expect(@NonNull Date actualDate) {
        return expect("", actualDate.toLocalDate());
    }

    default DateTo expect(@NonNull LocalDate actualLocalDate) {
        return expect("", actualLocalDate);
    }

    default DateTo expect(@NonNull String labelForActual, @Nullable Date actualDate) {
        return expect(labelForActual, actualDate == null ? null : actualDate.toLocalDate());
    }

    default DateTo expect(@NonNull String labelForActual, @Nullable LocalDate actualLocalDate) {
        return new DateTo(null, labelForActual, actualLocalDate, false);
    }
}
