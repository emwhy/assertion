package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDate} and {@link Date}.
 *
 * @see org.emw.assertion.collection.CollectionAssertor
 * @see org.emw.assertion.string.StringAssertor
 * @see org.emw.assertion.number.NumberAssertor
 * @see org.emw.assertion.bool.BooleanAssertor
 */
public interface DateAssertor {
    default DateTo expect(@NonNull Date actual) {
        return expect("", actual.toLocalDate());
    }

    default DateTo expect(@NonNull LocalDate actual) {
        return expect("", actual);
    }

    default DateTo expect(@NonNull String labelForActual, @Nullable Date actual) {
        return expect(labelForActual, actual == null ? null : actual.toLocalDate());
    }

    default DateTo expect(@NonNull String labelForActual, @Nullable LocalDate actual) {
        return new DateTo(null, labelForActual, actual, false);
    }
}
