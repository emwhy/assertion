package org.emw.assertfire.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Provides specialized state-based and relative assertion methods for {@link LocalDateTime} objects.
 * <p>
 * This class includes support for chronological comparisons (before, after, between),
 * calendar-specific checks (today, year), and date-part comparisons while ignoring time components.
 */
public class DateTimeBeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final @Nullable LocalDateTime actualLocalDateTime;
    private final DateTimeAssertorHelper helper;

    /**
     * Constructs date-time state assertion methods.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the date-time being tested
     * @param actualLocalDateTime the actual local date-time to assert against
     * @param negated whether the assertion logic should be inverted
     */
    protected DateTimeBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDateTime = actualLocalDateTime;
        this.helper = new DateTimeAssertorHelper(labelForActual, actualLocalDateTime, negated, DATETIME_FORMATTER);
    }

    /**
     * Assert that the date part of the actual date-time is the same as the provided SQL date.
     * @param date a SQL date value
     */
    public void sameDateAs(@NonNull Date date) {
        this.sameDateAs(date.toLocalDate());
    }

    /**
     * Assert that the date part of the actual date-time is the same as the date part of the provided date-time.
     * @param localDateTime a local date-time value
     */
    public void sameDateAs(@NonNull LocalDateTime localDateTime) {
        this.sameDateAs(localDateTime.toLocalDate());
    }

    /**
     * Assert that the date part of the actual date-time is today.
     */
    public void today() {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isEqual(LocalDate.now()) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be today"));
            }
        });
    }

    /**
     * Assert that the date-time falls within the specified year.
     * @param year the year to check
     */
    public void year(int year) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || (actualLocalDateTime.getYear() == year) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be year " + year));
            }
        });
    }

    /**
     * Assert that the date part of the actual date-time matches the provided local date.
     * @param expected a local date value
     */
    public void sameDateAs(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isEqual(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same date as '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual date-time is before the specified date-time.
     * @param expected the reference local date-time
     */
    public void before(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isBefore(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be before '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual date-time is after the specified date-time.
     * @param expected the reference local date-time
     */
    public void after(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isAfter(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be after '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual date-time is the same or before the specified date-time.
     * @param expected the reference local date-time
     */
    public void sameOrBefore(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isBefore(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or before '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual date-time is the same or after the specified date-time.
     * @param expected the reference local date-time
     */
    public void sameOrAfter(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isAfter(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or after '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual date-time is within the range of two local date-times (inclusive).
     * @param start the lower bound local date-time
     * @param end the upper bound local date-time
     */
    public void between(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        assertCondition(() -> {
            boolean isBetween = (actualLocalDateTime != null) && (actualLocalDateTime.isEqual(start) || actualLocalDateTime.isAfter(start)) && (actualLocalDateTime.isBefore(end) || actualLocalDateTime.isEqual(end));
            if (actualLocalDateTime == null || isBetween == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be between '" + start.format(DATETIME_FORMATTER) + "' and '" + end.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date part of the actual date-time is between today and
     * the specified number of days in the future (inclusive).
     * @param days the number of days forward to check
     */
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

    /**
     * Assert that the date part of the actual date-time is between the
     * specified number of past days and today (inclusive).
     * @param days the number of days backward to check
     */
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

    /**
     * Assert that the date part of the actual date-time is strictly further
     * in the future than the specified number of days from now.
     * @param days the number of days threshold
     */
    public void moreThanDaysInFuture(int days) {
        final LocalDate targetDate = LocalDate.now().plusDays(days);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isAfter(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in future"));
            }
        });
    }

    /**
     * Assert that the date part of the actual date-time is strictly further
     * in the past than the specified number of days from now.
     * @param days the number of days threshold
     */
    public void moreThanDaysInPast(int days) {
        final LocalDate targetDate = LocalDate.now().minusDays(days);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.toLocalDate().isBefore(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in past"));
            }
        });
    }

    /**
     * Assert that the actual date-time is between now and the specified
     * number of hours in the future (inclusive).
     * @param hours the number of hours forward to check
     */
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

    /**
     * Assert that the actual date-time is between the specified number
     * of past hours and now (inclusive).
     * @param hours the number of hours backward to check
     */
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

    /**
     * Assert that the actual date-time is strictly further in the future
     * than the specified number of hours from now.
     * @param hours the number of hours threshold
     */
    public void moreThanHoursInFuture(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.plusHours(hours);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isAfter(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in future from '" + now.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual date-time is strictly further in the past
     * than the specified number of hours from now.
     * @param hours the number of hours threshold
     */
    public void moreThanHoursInPast(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.minusHours(hours);
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isBefore(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in past from '" + now.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date-time value is {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualLocalDateTime == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
