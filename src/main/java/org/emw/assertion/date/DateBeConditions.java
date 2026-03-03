package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Conditions;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateBeConditions extends Conditions {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final @Nullable LocalDate actualLocalDate;

    protected DateBeConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDate = actualLocalDate;
    }

    public void sameDateAs(@NonNull Date date) {
        this.sameDateAs(date.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDateTime localDateTime) {
        this.sameDateAs(localDateTime.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDate expected) {
        assertCondition(partialAssertionErrorMessage() + "to be the same date as '" + expected.format(FORMATTER) + "'.", () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isEqual(expected) != negated;
            }
        });
    }

    public void before(@NonNull Date date) {
        this.before(date.toLocalDate());
    }

    public void before(@NonNull LocalDate expected) {
        assertCondition(partialAssertionErrorMessage() + "to be before '" + expected.format(FORMATTER) + "'.", () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isBefore(expected) != negated;
            }
        });
    }

    public void after(@NonNull Date date) {
        this.after(date.toLocalDate());
    }

    public void after(@NonNull LocalDate expected) {
        assertCondition(partialAssertionErrorMessage() + "to be after '" + expected.format(FORMATTER) + "'.", () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isAfter(expected) != negated;
            }
        });
    }

    public void sameOrBefore(@NonNull Date date) {
        this.sameOrBefore(date.toLocalDate());
    }

    public void sameOrBefore(@NonNull LocalDate expected) {
        assertCondition(partialAssertionErrorMessage() + "to be the same or before '" + expected.format(FORMATTER) + "'.", () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isEqual(expected) || actualLocalDate.isBefore(expected);
            }
        });
    }

    public void sameOrAfter(@NonNull Date date) {
        this.sameOrAfter(date.toLocalDate());
    }

    public void sameOrAfter(@NonNull LocalDate expected) {
        assertCondition(partialAssertionErrorMessage() + "to be the same or after '" + expected.format(FORMATTER) + "'.", () -> {
            if  (actualLocalDate == null) {
                return false;
            } else {
                return (actualLocalDate.isEqual(expected) || actualLocalDate.isAfter(expected)) != negated;
            }
        });
    }

    public void between(@NonNull Date date1, @NonNull Date date2) {
        this.between(date1.toLocalDate(), date2.toLocalDate());
    }

    public void between(@NonNull LocalDate start, @NonNull LocalDate end) {
        assertCondition(partialAssertionErrorMessage() + "to be between '" + start.format(FORMATTER) + "' and '" + end.format(FORMATTER) + "'.", () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return (actualLocalDate.isEqual(start) || actualLocalDate.isAfter(start)) && (actualLocalDate.isBefore(end) || actualLocalDate.isEqual(end)) != negated;
            }
        });
    }

    public void nullValue() {
        assertCondition(partialAssertionErrorMessage() + "to be null.", () -> {
            return negated != (actualLocalDate == null);
        });
    }


    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty()) {
            return "Expected '" + (actualLocalDate == null ? "null" : actualLocalDate.format(FORMATTER)) + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value('" + (actualLocalDate == null ? "null" : actualLocalDate.format(FORMATTER)) + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }

}
