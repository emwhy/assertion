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


    public void today() {
        assertCondition(helper.assertionErrorMessage("to be today"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.toLocalDate().isEqual(LocalDate.now()) != negated;
            }
        });
    }

    public void year(int year) {
        assertCondition(helper.assertionErrorMessage("to be year " + year), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return (actualLocalDateTime.getYear() == year) != negated;
            }
        });
    }

    public void sameDateAs(@NonNull Date date) {
        this.sameDateAs(date.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDateTime localDateTime) {
        this.sameDateAs(localDateTime.toLocalDate());
    }

    public void sameDateAs(@NonNull LocalDate expected) {
        assertCondition(helper.assertionErrorMessage("to be the same date as '" + expected.format(DATE_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.toLocalDate().isEqual(expected) != negated;
            }
        });
    }

    public void before(@NonNull LocalDateTime expected) {
        assertCondition(helper.assertionErrorMessage("to be before '" + expected.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isBefore(expected) != negated;
            }
        });
    }

    public void after(@NonNull LocalDateTime expected) {
        assertCondition(helper.assertionErrorMessage("to be after '" + expected.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isAfter(expected) != negated;
            }
        });
    }

    public void sameOrBefore(@NonNull LocalDateTime expected) {
        assertCondition(helper.assertionErrorMessage("to be the same or before '" + expected.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isBefore(expected)) != negated;
            }
        });
    }

    public void sameOrAfter(@NonNull LocalDateTime expected) {
        assertCondition(helper.assertionErrorMessage("to be the same or after '" + expected.format(DATETIME_FORMATTER) + "'"), () -> {
            if  (actualLocalDateTime == null) {
                return false;
            } else {
                return (actualLocalDateTime.isEqual(expected) || actualLocalDateTime.isAfter(expected)) != negated;
            }
        });
    }
    
    public void between(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        assertCondition(helper.assertionErrorMessage("to be between '" + start.format(DATETIME_FORMATTER) + "' and '" + end.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return ((actualLocalDateTime.isEqual(start) || actualLocalDateTime.isAfter(start)) && (actualLocalDateTime.isBefore(end) || actualLocalDateTime.isEqual(end))) != negated;
            }
        });
    }

    public void withinDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.plusDays(days);

        assertCondition(helper.assertionErrorMessage("to be within " + days + " days from today"), () -> {
            if  (actualLocalDateTime == null) {
                return false;
            } else {
                final LocalDate actualLocalDate = actualLocalDateTime.toLocalDate();

                return ((actualLocalDate.isEqual(today) || actualLocalDate.isAfter(today)) && (actualLocalDate.isBefore(targetDate) || actualLocalDate.isEqual(targetDate))) != negated;
            }
        });
    }

    public void withinPastDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.minusDays(days);

        assertCondition(helper.assertionErrorMessage("to be within past " + days + " days from today"), () -> {
            if  (actualLocalDateTime == null) {
                return false;
            } else {
                final LocalDate actualLocalDate = actualLocalDateTime.toLocalDate();

                return ((actualLocalDate.isEqual(targetDate) || actualLocalDate.isAfter(targetDate)) && (actualLocalDate.isBefore(today) || actualLocalDate.isEqual(today))) != negated;
            }
        });
    }

    public void moreThanDaysInFuture(int days) {
        final LocalDate targetDate = LocalDate.now().plusDays(days);

        assertCondition(helper.assertionErrorMessage("to be more than " + days + " days in future"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                final LocalDate actualLocalDate = actualLocalDateTime.toLocalDate();

                return actualLocalDate.isAfter(targetDate) != negated;
            }
        });
    }

    public void moreThanDaysInPast(int days) {
        final LocalDate targetDate = LocalDate.now().minusDays(days);

        assertCondition(helper.assertionErrorMessage("to be more than " + days + " days in past"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                final LocalDate actualLocalDate = actualLocalDateTime.toLocalDate();

                return actualLocalDate.isBefore(targetDate) != negated;
            }
        });
    }

    public void withinHours(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.plusHours(hours);

        assertCondition(helper.assertionErrorMessage("to be within " + hours + " hours from '" +  now.format(DATETIME_FORMATTER) + "'"), () -> {
            if  (actualLocalDateTime == null) {
                return false;
            } else {
                return ((actualLocalDateTime.isEqual(now) || actualLocalDateTime.isAfter(now)) && (actualLocalDateTime.isBefore(targetDateTime) || actualLocalDateTime.isEqual(targetDateTime))) != negated;
            }
        });
    }

    public void withinPastHours(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.minusHours(hours);

        assertCondition(helper.assertionErrorMessage("to be within past " + hours + " hours from '" +  now.format(DATETIME_FORMATTER) + "'"), () -> {
            if  (actualLocalDateTime == null) {
                return false;
            } else {
                return ((actualLocalDateTime.isEqual(targetDateTime) || actualLocalDateTime.isAfter(targetDateTime)) && (actualLocalDateTime.isBefore(now) || actualLocalDateTime.isEqual(now))) != negated;
            }
        });
    }

    public void moreThanHoursInFuture(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.plusHours(hours);

        assertCondition(helper.assertionErrorMessage("to be more than " + hours + " hours in future from '" + now.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isAfter(targetDateTime) != negated;
            }
        });
    }

    public void moreThanHoursInPast(int hours) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetDateTime = now.minusHours(hours);

        assertCondition(helper.assertionErrorMessage("to be more than " + hours + " hours in past from '" + now.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDateTime == null) {
                return false;
            } else {
                return actualLocalDateTime.isBefore(targetDateTime) != negated;
            }
        });
    }

    public void nullValue() {
        assertCondition(helper.assertionErrorMessage("to be null"), () -> {
            return negated != (actualLocalDateTime == null);
        });
    }
}
