package org.emw.assertion.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateBeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final @Nullable LocalDate actualLocalDate;
    private final DateAssertorHelper helper;

    protected DateBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDate = actualLocalDate;
        this.helper = new DateAssertorHelper(labelForActual, actualLocalDate, negated, DATE_FORMATTER);
    }

    public void today() {
        assertCondition(helper.assertionErrorMessage("to be today"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isEqual(LocalDate.now()) != negated;
            }
        });
    }

    public void year(int year) {
        assertCondition(helper.assertionErrorMessage("to be year " + year), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return (actualLocalDate.getYear() == year) != negated;
            }
        });
    }

    public void sameDateAs(@NonNull LocalDateTime expected) {
        assertCondition(helper.assertionErrorMessage("to be the same date as '" + expected.format(DATETIME_FORMATTER) + "'"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isEqual(expected.toLocalDate()) != negated;
            }
        });
    }

    public void before(@NonNull Date date) {
        this.before(date.toLocalDate());
    }

    public void before(@NonNull LocalDate expected) {
        assertCondition(helper.assertionErrorMessage("to be before '" + expected.format(DATE_FORMATTER) + "'"), () -> {
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
        assertCondition(helper.assertionErrorMessage("to be after '" + expected.format(DATE_FORMATTER) + "'"), () -> {
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
        assertCondition(helper.assertionErrorMessage("to be the same or before '" + expected.format(DATE_FORMATTER) + "'"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return (actualLocalDate.isEqual(expected) || actualLocalDate.isBefore(expected)) != negated;
            }
        });
    }

    public void sameOrAfter(@NonNull Date date) {
        this.sameOrAfter(date.toLocalDate());
    }

    public void sameOrAfter(@NonNull LocalDate expected) {
        assertCondition(helper.assertionErrorMessage("to be the same or after '" + expected.format(DATE_FORMATTER) + "'"), () -> {
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
        assertCondition(helper.assertionErrorMessage("to be between '" + start.format(DATE_FORMATTER) + "' and '" + end.format(DATE_FORMATTER) + "'"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return ((actualLocalDate.isEqual(start) || actualLocalDate.isAfter(start)) && (actualLocalDate.isBefore(end) || actualLocalDate.isEqual(end))) != negated;
            }
        });
    }

    public void withinDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.plusDays(days);

        assertCondition(helper.assertionErrorMessage("to be within " + days + " days from today"), () -> {
            if  (actualLocalDate == null) {
                return false;
            } else {
                return ((actualLocalDate.isEqual(today) || actualLocalDate.isAfter(today)) && (actualLocalDate.isBefore(targetDate) || actualLocalDate.isEqual(targetDate))) != negated;
            }
        });
    }

    public void withinPastDays(int days) {
        final LocalDate today = LocalDate.now();
        final LocalDate targetDate = today.minusDays(days);

        assertCondition(helper.assertionErrorMessage("to be within past " + days + " days from today"), () -> {
            if  (actualLocalDate == null) {
                return false;
            } else {
                return ((actualLocalDate.isEqual(targetDate) || actualLocalDate.isAfter(targetDate)) && (actualLocalDate.isBefore(today) || actualLocalDate.isEqual(today))) != negated;
            }
        });
    }

    public void moreThanDaysInFuture(int days) {
        final LocalDate targetDate = LocalDate.now().plusDays(days);

        assertCondition(helper.assertionErrorMessage("to be more than " + days + " days in future"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isAfter(targetDate) != negated;
            }
        });
    }

    public void moreThanDaysInPast(int days) {
        final LocalDate targetDate = LocalDate.now().minusDays(days);

        assertCondition(helper.assertionErrorMessage("to be more than " + days + " days in past"), () -> {
            if (actualLocalDate == null) {
                return false;
            } else {
                return actualLocalDate.isBefore(targetDate) != negated;
            }
        });
    }

    public void nullValue() {
        assertCondition(helper.assertionErrorMessage("to be null"), () -> {
            return negated != (actualLocalDate == null);
        });
    }

}
