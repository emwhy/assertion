package org.emw.assertfire.datetime;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides assertion methods for {@link LocalDateTime} objects.
 * <p>
 * This class facilitates equality comparisons for date-time values and provides
 * access to extended state-based assertions through the {@link #be} property.
 */
public class DateTimeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final @Nullable LocalDateTime actualLocalDateTime;

    /**
     * Accessor for specific state-based or relative date-time assertions (e.g., today, within hours, before).
     */
    public final DateTimeBeAssertionMethods be;

    private final DateTimeAssertorHelper helper;

    /**
     * Constructs date-time assertion methods with the specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the date-time being tested
     * @param actualLocalDateTime the actual local date-time to assert against
     * @param negated whether the assertion logic should be inverted
     */
    protected DateTimeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalDateTime actualLocalDateTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalDateTime = actualLocalDateTime;
        this.be = new DateTimeBeAssertionMethods(group, labelForActual, actualLocalDateTime, negated);
        this.helper = new DateTimeAssertorHelper(labelForActual, actualLocalDateTime, negated, DATETIME_FORMATTER);
    }

    /**
     * Assert that the actual date-time matches the provided expected date-time.
     *
     * @param expected the expected {@link LocalDateTime}
     */
    public void be(@NonNull LocalDateTime expected) {
        assertCondition(() -> {
            if (actualLocalDateTime == null || actualLocalDateTime.isEqual(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be '" + expected.format(DATE_FORMATTER) + "'"));
            }
        });
    }

}
