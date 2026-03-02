package org.emwhyware.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Date;
import java.time.LocalDate;

public interface DateAssertor {
    default DateTo expect(@NonNull Date actual) {
        return expect("", actual.toLocalDate());
    }

    default DateTo expect(@NonNull LocalDate actual) {
        return expect("", actual);
    }

    default DateTo expect(@NonNull String labelForActual, @NonNull Date actual) {
        return expect(labelForActual, actual.toLocalDate());
    }

    default DateTo expect(@NonNull String labelForActual, @NonNull LocalDate actual) {
        return new DateTo(null, labelForActual, actual, false);
    }
}
