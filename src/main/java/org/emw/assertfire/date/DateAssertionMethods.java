package org.emw.assertfire.date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Provides assertion methods for {@link LocalDate} objects.
 * <p>
 * This class handles standard date comparisons, allowing for equality checks
 * against both SQL {@link Date} and Java {@link LocalDate} types, while supporting
 * custom labels and negation.
 */
public class DateAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Accessor for specific state-based or relative date assertions (e.g., today, before, after).
     */
    public final DateBeAssertionMethods be;

    private final @Nullable LocalDate actualLocalDate;
    private final DateAssertorHelper helper;

    /**
     * Constructs date assertion methods with the specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the date being tested
     * @param actualLocalDate the actual local date to assert against
     * @param negated whether the assertion logic should be inverted
     */
    protected DateAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDate actualLocalDate, boolean negated) {
        super(group, labelForActual, negated, false);
        this.be = new DateBeAssertionMethods(group, labelForActual, actualLocalDate, negated);
        this.actualLocalDate = actualLocalDate;
        this.helper = new DateAssertorHelper(labelForActual, actualLocalDate, negated, FORMATTER);
    }

    /**
     * Assert that the actual date matches the provided SQL date.
     * @param expected a SQL date value
     */
    public void be(@NonNull Date expected) {
        this.be(expected.toLocalDate());
    }

    /**
     * Assert that the actual date matches the provided local date.
     * @param expected a local date value
     */
    public void be(@NonNull LocalDate expected) {
        assertCondition(() -> {
            if (actualLocalDate == null || actualLocalDate.isEqual(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be '" + expected.format(FORMATTER) + "'"));
            }
        });
    }

}
