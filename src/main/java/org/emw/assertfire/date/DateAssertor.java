package org.emw.assertfire.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A list of <strong>expect</strong> assertion methods for {@link LocalDate} and {@link Date}.
 *
 * @see org.emw.assertfire.collection.CollectionAssertor
 * @see org.emw.assertfire.string.StringAssertor
 * @see org.emw.assertfire.number.NumberAssertor
 * @see org.emw.assertfire.bool.BooleanAssertor
 * @see org.emw.assertfire.datetime.DateTimeAssertor
 * @see org.emw.assertfire.time.TimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
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
