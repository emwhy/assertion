package org.emw.assertfire.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.sql.Date;
import java.time.LocalDate;

public final class DateAssertionGroup {
    private final AssertionGroup group;

    public DateAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public DateExpect expect(@Nullable Date actualDate) {
        return expect("", actualDate == null ? null : actualDate.toLocalDate());
    }

    public DateExpect expect(@Nullable LocalDate actualLocalDate) {
        return expect("", actualLocalDate);
    }

    public DateExpect expect(@NonNull String labelForActual, @Nullable Date actualDate) {
        return expect(labelForActual, actualDate == null ? null : actualDate.toLocalDate());
    }

    public DateExpect expect(@NonNull String labelForActual, @Nullable LocalDate actualLocalDate) {
        return new DateExpect(group, labelForActual, actualLocalDate, false);
    }
}
