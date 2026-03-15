package org.emw.assertfire.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides specialized state-based and relative assertion methods for {@link LocalTime} objects.
 * <p>
 * This class includes support for chronological comparisons (before, after, between) and
 * relative time-window assertions based on the current system time (within hours, more than hours).
 */
public class TimeBeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final @Nullable LocalTime actualLocalTime;
    private final TimeAssertorHelper helper;

    /**
     * Constructs time state assertion methods.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the time being tested
     * @param actualLocalTime the actual local time to assert against
     * @param negated whether the assertion logic should be inverted
     */
    protected TimeBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalTime actualLocalTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalTime = actualLocalTime;
        this.helper = new TimeAssertorHelper(labelForActual, actualLocalTime, negated, TIME_FORMATTER);
    }

    /**
     * Assert that the actual time is strictly before the specified time.
     * @param expected the reference local time
     */
    public void before(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.isBefore(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be before '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time is strictly after the specified time.
     * @param expected the reference local time
     */
    public void after(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.isAfter(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be after '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time is the same as or before the specified time.
     * @param expected the reference local time
     */
    public void sameOrBefore(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || (actualLocalTime.equals(expected) || actualLocalTime.isBefore(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or before '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time is the same as or after the specified time.
     * @param expected the reference local time
     */
    public void sameOrAfter(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || (actualLocalTime.equals(expected) || actualLocalTime.isAfter(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or after '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time is within the specified range (inclusive).
     * @param start the lower bound local time
     * @param end the upper bound local time
     */
    public void between(@NonNull LocalTime start, @NonNull LocalTime end) {
        assertCondition(() -> {
            boolean isBetween = (actualLocalTime != null) && (actualLocalTime.equals(start) || actualLocalTime.isAfter(start)) && (actualLocalTime.isBefore(end) || actualLocalTime.equals(end));
            if (actualLocalTime == null || isBetween == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be between '" + start.format(TIME_FORMATTER) + "' and '" + end.format(TIME_FORMATTER) + "'"));
            }
        });
    }


    /**
     * Assert that the actual time falls within a specified number of hours past the current time.
     * @param hours the number of hours to check
     */
    public void withinHours(int hours) {
        final LocalDateTime now = LocalTime.now().atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime actualLocalDateTime = actualLocalTime == null ? null : actualLocalTime.atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime targetDateTime = now.plusHours(hours);

        // Because the time wraps when adding hours, convert to date/time to get correct comparison.
        assertCondition(() -> {
            boolean isWithin = (actualLocalDateTime != null) && (actualLocalDateTime.isEqual(targetDateTime) || actualLocalDateTime.isBefore(targetDateTime)) && (actualLocalDateTime.isAfter(now) || actualLocalDateTime.isEqual(now));
            if (actualLocalDateTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within " + hours + " hours from '" +  now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time falls within a specified number of hours prior to the current time.
     * @param hours the number of hours to check
     */
    public void withinPastHours(int hours) {
        final LocalDateTime now = LocalTime.now().atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime actualLocalDateTime = actualLocalTime == null ? null : actualLocalTime.atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime targetDateTime = now.minusHours(hours);

        // Because the time wraps when adding hours, convert to date/time to get correct comparison.
        assertCondition(() -> {
            final boolean isWithin = (actualLocalDateTime != null) && (actualLocalDateTime.isEqual(targetDateTime) || actualLocalDateTime.isAfter(targetDateTime)) && (actualLocalDateTime.isBefore(now) || actualLocalDateTime.isEqual(now));

            if (actualLocalTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within past " + hours + " hours from '" +  now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time is strictly further in the future than the specified number of hours from now.
     * @param hours the number of hours threshold
     */
    public void moreThanHoursInFuture(int hours) {
        final LocalDateTime now = LocalTime.now().atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime actualLocalDateTime = actualLocalTime == null ? null : actualLocalTime.atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime targetDateTime = now.plusHours(hours);

        // Because the time wraps when adding hours, convert to date/time to get correct comparison.
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isAfter(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in future from '" + now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the actual time is strictly further in the past than the specified number of hours from now.
     * @param hours the number of hours threshold
     */
    public void moreThanHoursInPast(int hours) {
        final LocalDateTime now = LocalTime.now().atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime actualLocalDateTime = actualLocalTime == null ? null : actualLocalTime.atDate(LocalDate.of(2000, 1, 1));
        final LocalDateTime targetDateTime = now.minusHours(hours);

        // Because the time wraps when adding hours, convert to date/time to get correct comparison.
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isBefore(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in past from '" + now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    /**
     * Assert that the time value is {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualLocalTime == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
