package org.emw.assertion.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeBeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final @Nullable LocalTime actualLocalTime;
    private final TimeAssertorHelper helper;

    protected TimeBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalTime actualLocalTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalTime = actualLocalTime;
        this.helper = new TimeAssertorHelper(labelForActual, actualLocalTime, negated, TIME_FORMATTER);
    }

    public void before(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.isBefore(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be before '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void after(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.isAfter(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be after '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void sameOrBefore(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || (actualLocalTime.equals(expected) || actualLocalTime.isBefore(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or before '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void sameOrAfter(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || (actualLocalTime.equals(expected) || actualLocalTime.isAfter(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or after '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void between(@NonNull LocalTime start, @NonNull LocalTime end) {
        assertCondition(() -> {
            boolean isBetween = (actualLocalTime != null) && (actualLocalTime.equals(start) || actualLocalTime.isAfter(start)) && (actualLocalTime.isBefore(end) || actualLocalTime.equals(end));
            if (actualLocalTime == null || isBetween == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be between '" + start.format(TIME_FORMATTER) + "' and '" + end.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void withinPastHours(int hours) {
        final LocalTime now = LocalTime.now();
        final LocalTime targetDateTime = now.minusHours(hours);
        assertCondition(() -> {
            boolean isWithin = (actualLocalTime != null) && (actualLocalTime.equals(targetDateTime) || actualLocalTime.isAfter(targetDateTime)) && (actualLocalTime.isBefore(now) || actualLocalTime.equals(now));
            if (actualLocalTime == null || isWithin == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be within past " + hours + " hours from '" +  now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void moreThanHoursInFuture(int hours) {
        final LocalTime now = LocalTime.now();
        final LocalTime targetDateTime = now.plusHours(hours);
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.isAfter(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in future from '" + now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void moreThanHoursInPast(int hours) {
        final LocalTime now = LocalTime.now();
        final LocalTime targetDateTime = now.minusHours(hours);
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.isBefore(targetDateTime) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + hours + " hours in past from '" + now.format(TIME_FORMATTER) + "'"));
            }
        });
    }

    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualLocalTime == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
