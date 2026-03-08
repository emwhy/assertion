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

    public void before(@NonNull Date date) {
        this.before(date.toLocalDate());
    }

    public void after(@NonNull Date date) {
        this.after(date.toLocalDate());
    }

    public void sameOrBefore(@NonNull Date date) {
        this.sameOrBefore(date.toLocalDate());
    }

    public void sameOrAfter(@NonNull Date date) {
        this.sameOrAfter(date.toLocalDate());
    }

    public void between(@NonNull Date date1, @NonNull Date date2) {
        this.between(date1.toLocalDate(), date2.toLocalDate());
    }

    public void today() {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isEqual(LocalDate.now()) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be today"));
            }
        });
    }

    public void year(int year) {
        assertCondition(() -> {
            if (actualLocalDate == null || (actualLocalDate.getYear() == year) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be year " + year));
            }
        });
    }

    public void sameDateAs(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isEqual(expected.toLocalDate()) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same date as '" + expected.format(DATETIME_FORMATTER) + "'"));
            }
        });
    }

    public void before(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isBefore(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be before '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    public void after(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isAfter(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be after '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    public void sameOrBefore(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || (actualLocalDate.isEqual(expected) || actualLocalDate.isBefore(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or before '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    public void sameOrAfter(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || (actualLocalDate.isEqual(expected) || actualLocalDate.isAfter(expected)) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be the same or after '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

    public void between(@NonNull LocalDate start, @NonNull LocalDate end) {
        assertCondition(() -> {
            final boolean isBetween = (actualLocalDate != null) && (actualLocalDate.isEqual(start) || actualLocalDate.isAfter(start)) && (actualLocalDate.isBefore(end) || actualLocalDate.isEqual(end));
            if (actualLocalDate == null || isBetween == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be between '" + start.format(DATE_FORMATTER) + "' and '" + end.format(DATE_FORMATTER) + "'"));
            }
        });
    }

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

    public void moreThanDaysInFuture(int days) {
        final LocalDate targetDate = LocalDate.now().plusDays(days);
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isAfter(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in future"));
            }
        });
    }

    public void moreThanDaysInPast(int days) {
        final LocalDate targetDate = LocalDate.now().minusDays(days);
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isBefore(targetDate) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be more than " + days + " days in past"));
            }
        });
    }

    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualLocalDate == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

}
