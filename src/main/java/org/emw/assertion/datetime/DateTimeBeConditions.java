package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.Conditions;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeBeConditions extends Conditions {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final @Nullable LocalDateTime actualLocalDateTime;

    protected DateTimeBeConditions(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDateTime = actualLocalDateTime;
    }

    public void sameDateAs(@NonNull Date date) {
        this.sameDateAs(date.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDateTime localDateTime) {
        this.sameDateAs(localDateTime.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDate expected) {
        assertCondition(partialAssertionErrorMessage() + "to be the same date as '" + expected.format(DATE_FORMATTER) + "'.", () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.toLocalDate().isEqual(expected) != negated;
            }
        });
    }

    public void before(@NonNull LocalDateTime expected) {
        assertCondition(partialAssertionErrorMessage() + "to be before '" + expected.format(DATETIME_FORMATTER) + "'.", () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isBefore(expected) != negated;
            }
        });
    }

    public void after(@NonNull LocalDateTime expected) {
        assertCondition(partialAssertionErrorMessage() + "to be after '" + expected.format(DATETIME_FORMATTER) + "'.", () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isAfter(expected) != negated;
            }
        });
    }

    public void sameOrBefore(@NonNull LocalDateTime expected) {
        assertCondition(partialAssertionErrorMessage() + "to be the same or before '" + expected.format(DATETIME_FORMATTER) + "'.", () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isBefore(expected);
            }
        });
    }

    public void sameOrAfter(@NonNull LocalDateTime expected) {
        assertCondition(partialAssertionErrorMessage() + "to be the same or after '" + expected.format(DATETIME_FORMATTER) + "'.", () -> {
            if  (actualLocalDateTime == null) {
                return false;
            } else {
                return (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isAfter(expected)) != negated;
            }
        });
    }
    
    public void between(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        assertCondition(partialAssertionErrorMessage() + "to be between '" + start.format(DATETIME_FORMATTER) + "' and '" + end.format(DATETIME_FORMATTER) + "'.", () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return (actualLocalDateTime.isEqual(start) || actualLocalDateTime.isAfter(start)) && (actualLocalDateTime.isBefore(end) || actualLocalDateTime.isEqual(end)) != negated;
            }
        });
    }

    public void nullValue() {
        assertCondition(partialAssertionErrorMessage() + "to be null.", () -> {
            return negated != (actualLocalDateTime == null);
        });
    }

    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty()) {
            return "Expected '" + (actualLocalDateTime == null ? "null" : actualLocalDateTime.format(DATETIME_FORMATTER)) + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value('" + (actualLocalDateTime == null ? "null" : actualLocalDateTime.format(DATETIME_FORMATTER)) + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }

}
