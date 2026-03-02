package org.emwhyware.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.AssertionGroup;

import java.sql.Date;
import java.time.LocalDate;

public final class DateAssertionGroup {
    private final AssertionGroup group;

    public DateAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public DateTo expect(@Nullable Date actual) {
        return expect("", actual == null ? null : actual.toLocalDate());
    }

    public DateTo expect(@Nullable LocalDate actual) {
        return expect("", actual);
    }

    public DateTo expect(@NonNull String labelForActual, @Nullable Date actual) {
        return expect(labelForActual, actual == null ? null : actual.toLocalDate());
    }

    public DateTo expect(@NonNull String labelForActual, @Nullable LocalDate actual) {
        return new DateTo(group, labelForActual, actual, false);
    }
}
