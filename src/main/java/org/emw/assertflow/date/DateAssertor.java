package org.emw.assertflow.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDate} and {@link Date}.
 *
 * @see org.emw.assertflow.collection.CollectionAssertor
 * @see org.emw.assertflow.string.StringAssertor
 * @see org.emw.assertflow.number.NumberAssertor
 * @see org.emw.assertflow.bool.BooleanAssertor
 * @see org.emw.assertflow.datetime.DateTimeAssertor
 * @see org.emw.assertflow.time.TimeAssertor
 * @see org.emw.assertflow.json.JsonAssertor
 */
public interface DateAssertor {
    default DateExpect expect(@NonNull Date actualDate) {
        return expect("", actualDate.toLocalDate());
    }

    default DateExpect expect(@NonNull LocalDate actualLocalDate) {
        return expect("", actualLocalDate);
    }

    default DateExpect expect(@NonNull String labelForActual, @Nullable Date actualDate) {
        return expect(labelForActual, actualDate == null ? null : actualDate.toLocalDate());
    }

    default DateExpect expect(@NonNull String labelForActual, @Nullable LocalDate actualLocalDate) {
        return new DateExpect(null, labelForActual, actualLocalDate, false);
    }
}
