package org.emw.assertion.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeBeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final @Nullable LocalDateTime actualLocalDateTime;
    private final DateTimeAssertorHelper helper;

    protected DateTimeBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDateTime = actualLocalDateTime;
        this.helper = new DateTimeAssertorHelper(labelForActual, actualLocalDateTime, negated, DATETIME_FORMATTER);
    }

    public void sameDateAs(@NonNull Date date) {
        this.sameDateAs(date.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDateTime localDateTime) {
        this.sameDateAs(localDateTime.toLocalDate());
    }

    public void today() {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isEqual(LocalDate.now()) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be today"));
            }
        });
    }

    public void year(int year) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || (actualLocalDateTime.getYear() == year) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be year " + year));
            }
        });
    }

    public void sameDateAs(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isEqual(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same date as '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    public void before(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isBefore(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be before '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void after(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isAfter(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be after '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void sameOrBefore(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isBefore(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or before '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void sameOrAfter(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isAfter(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or after '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void between(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        assertCondition(() -> {
            boolean isBetween = (actualLocalDateTime != null) && (actualLocalDateTime.isEqual(start) || actualLocalDateTime.isAfter(start)) && (actualLocalDateTime.isBefore(end) || actualLocalDateTime.isEqual(end));
            if (actualLocalDateTime == null || isBetween == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be between '" + start.format(DATETIME_FORMATTER) + "' and '" + end.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void withinDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.plusDays(days);
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDateTime != null) && (actualLocalDateTime.toLocalDate().isEqual(today) || actualLocalDateTime.toLocalDate().isAfter(today)) && (actualLocalDateTime.toLocalDate().isBefore(targetDate) || actualLocalDateTime.toLocalDate().isEqual(targetDate));
            if (actualLocalDateTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within " + days + " days from today"));
            }
        });
    }

    public void withinPastDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.minusDays(days);
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDateTime != null) && (actualLocalDateTime.toLocalDate().isEqual(targetDate) || actualLocalDateTime.toLocalDate().isAfter(targetDate)) && (actualLocalDateTime.toLocalDate().isBefore(today) || actualLocalDateTime.toLocalDate().isEqual(today));
            if (actualLocalDateTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within past " + days + " days from today"));
            }
        });
    }

    public void moreThanDaysInFuture(int days) {
        final LocalDate targetDate = LocalDate.now().plusDays(days);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isAfter(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in future"));
            }
        });
    }

    public void moreThanDaysInPast(int days) {
        final LocalDate targetDate = LocalDate.now().minusDays(days);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isBefore(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in past"));
            }
        });
    }

    public void withinHours(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.plusHours(hours);
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDateTime != null) && (actualLocalDateTime.isEqual(now) || actualLocalDateTime.isAfter(now)) && (actualLocalDateTime.isBefore(targetDateTime) || actualLocalDateTime.isEqual(targetDateTime));
            if (actualLocalDateTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within " + hours + " hours from '" + now.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void withinPastHours(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.minusHours(hours);
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDateTime != null) && (actualLocalDateTime.isEqual(targetDateTime) || actualLocalDateTime.isAfter(targetDateTime)) && (actualLocalDateTime.isBefore(now) || actualLocalDateTime.isEqual(now));
            if (actualLocalDateTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within past " + hours + " hours from '" + now.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void moreThanHoursInFuture(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.plusHours(hours);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isAfter(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in future from '" + now.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void moreThanHoursInPast(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.minusHours(hours);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isBefore(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in past from '" + now.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualLocalDateTime == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }
}
