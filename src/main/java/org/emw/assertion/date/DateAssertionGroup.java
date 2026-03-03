package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;

import java.sql.Date;
import java.time.LocalDate;

public final class DateAssertionGroup {
    private final AssertionGroup group;

    public DateAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public DateTo expect(@Nullable Date actualDate) {
        return expect("", actualDate == null ? null : actualDate.toLocalDate());
    }

    public DateTo expect(@Nullable LocalDate actualLocalDate) {
        return expect("", actualLocalDate);
    }

    public DateTo expect(@NonNull String labelForActual, @Nullable Date actualDate) {
        return expect(labelForActual, actualDate == null ? null : actualDate.toLocalDate());
    }

    public DateTo expect(@NonNull String labelForActual, @Nullable LocalDate actualLocalDate) {
        return new DateTo(group, labelForActual, actualLocalDate, false);
    }
}
