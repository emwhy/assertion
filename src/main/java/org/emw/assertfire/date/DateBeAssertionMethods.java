package org.emw.assertfire.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides specialized state-based and relative assertion methods for {@link LocalDate} objects.
 * <p>
 * This class includes support for common date comparisons (before, after, between),
 * calendar-specific checks (today, year), and relative time-window assertions (within days,
 * more than days in past/future).
 */
public class DateBeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final @Nullable LocalDate actualLocalDate;
    private final DateAssertorHelper helper;

    /**
     * Constructs date state assertion methods.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the date being tested
     * @param actualLocalDate the actual local date to assert against
     * @param negated whether the assertion logic should be inverted
     */
    protected DateBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDate = actualLocalDate;
        this.helper = new DateAssertorHelper(labelForActual, actualLocalDate, negated, DATE_FORMATTER);
    }

    /**
     * Assert that the date is before the specified SQL date.
     * @param date the reference SQL date
     */
    public void before(@NonNull Date date) {
        this.before(date.toLocalDate());
    }

    /**
     * Assert that the date is after the specified SQL date.
     * @param date the reference SQL date
     */
    public void after(@NonNull Date date) {
        this.after(date.toLocalDate());
    }

    /**
     * Assert that the date is the same or before the specified SQL date.
     * @param date the reference SQL date
     */
    public void sameOrBefore(@NonNull Date date) {
        this.sameOrBefore(date.toLocalDate());
    }

    /**
     * Assert that the date is the same or after the specified SQL date.
     * @param date the reference SQL date
     */
    public void sameOrAfter(@NonNull Date date) {
        this.sameOrAfter(date.toLocalDate());
    }

    /**
     * Assert that the date is within the range of two SQL dates (inclusive).
     * @param date1 the lower bound SQL date
     * @param date2 the upper bound SQL date
     */
    public void between(@NonNull Date date1, @NonNull Date date2) {
        this.between(date1.toLocalDate(), date2.toLocalDate());
    }

    /**
     * Assert that the date is today's date.
     */
    public void today() {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isEqual(LocalDate.now()) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be today"));
            }
        });
    }

    /**
     * Assert that the date falls within the specified year.
     * @param year the year to check
     */
    public void year(int year) {
        assertCondition(() -> {
            if (actualLocalDate == null || (actualLocalDate.getYear() == year) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be year " + year));
            }
        });
    }

    /**
     * Assert that the date is the same as the date component of the provided {@link LocalDateTime}.
     * @param expected the reference date time
     */
    public void sameDateAs(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isEqual(expected.toLocalDate()) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same date as '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date is before the specified local date.
     * @param expected the reference local date
     */
    public void before(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isBefore(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be before '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date is after the specified local date.
     * @param expected the reference local date
     */
    public void after(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isAfter(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be after '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date is the same or before the specified local date.
     * @param expected the reference local date
     */
    public void sameOrBefore(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || (actualLocalDate.isEqual(expected) || actualLocalDate.isBefore(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or before '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date is the same or after the specified local date.
     * @param expected the reference local date
     */
    public void sameOrAfter(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || (actualLocalDate.isEqual(expected) || actualLocalDate.isAfter(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or after '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date is within the range of two local dates (inclusive).
     * @param start the lower bound local date
     * @param end the upper bound local date
     */
    public void between(@NonNull LocalDate start, @NonNull LocalDate end) {
        assertCondition(() -> {
            final boolean isBetween = (actualLocalDate != null) && (actualLocalDate.isEqual(start) || actualLocalDate.isAfter(start)) && (actualLocalDate.isBefore(end) || actualLocalDate.isEqual(end));
            if (actualLocalDate == null || isBetween == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be between '" + start.format(DATE_FORMATTER) + "' and '" + end.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the date is between today and the specified number of days in the future (inclusive).
     * @param days the number of days forward to check
     */
    public void withinDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.plusDays(days);
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDate != null) && (actualLocalDate.isEqual(today) || actualLocalDate.isAfter(today)) && (actualLocalDate.isBefore(targetDate) || actualLocalDate.isEqual(targetDate));
            if (actualLocalDate == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within " + days + " days from today"));
            }
        });
    }

    /**
     * Assert that the date is between the specified number of past days and today (inclusive).
     * @param days the number of days backward to check
     */
    public void withinPastDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.minusDays(days);
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDate != null) && (actualLocalDate.isEqual(targetDate) || actualLocalDate.isAfter(targetDate)) && (actualLocalDate.isBefore(today) || actualLocalDate.isEqual(today));
            if (actualLocalDate == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within past " + days + " days from today"));
            }
        });
    }

    /**
     * Assert that the date is strictly further in the future than the specified number of days from now.
     * @param days the number of days threshold
     */
    public void moreThanDaysInFuture(int days) {
        final LocalDate targetDate = LocalDate.now().plusDays(days);
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isAfter(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in future"));
            }
        });
    }

    /**
     * Assert that the date is strictly further in the past than the specified number of days from now.
     * @param days the number of days threshold
     */
    public void moreThanDaysInPast(int days) {
        final LocalDate targetDate = LocalDate.now().minusDays(days);
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isBefore(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in past"));
            }
        });
    }

    /**
     * Assert that the date value is {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualLocalDate == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
