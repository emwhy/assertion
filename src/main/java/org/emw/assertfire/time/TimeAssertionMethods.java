package org.emw.assertfire.time;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides assertion methods for {@link LocalTime} objects.
 * <p>
 * This class handles standard time equality comparisons and provides access
 * to extended state-based assertions via the {@link #be} property.
 */
public class TimeAssertionMethods extends AssertionMethods {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final @Nullable LocalTime actualLocalTime;

    /**
     * Accessor for specific state-based or relative time assertions (e.g., before, after, between).
     */
    public final TimeBeAssertionMethods be;

    /**
     * Helper for generating formatted assertion error messages.
     */
    public final TimeAssertorHelper helper;

    /**
     * Constructs time assertion methods with the specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the time being tested
     * @param actualLocalTime the actual local time to assert against
     * @param negated whether the assertion logic should be inverted
     */
    protected TimeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable LocalTime actualLocalTime, boolean negated) {
        super(group, labelForActual, negated, false);
        this.actualLocalTime = actualLocalTime;
        this.be = new TimeBeAssertionMethods(group, labelForActual, actualLocalTime, negated);
        this.helper = new TimeAssertorHelper(labelForActual, actualLocalTime, negated, TIME_FORMATTER);
    }

    /**
     * Assert that the actual time is exactly equal to the provided expected time.
     *
     * @param expected the expected {@link LocalTime}
     */
    public void be(@NonNull LocalTime expected) {
        assertCondition(() -> {
            if (actualLocalTime == null || actualLocalTime.equals(expected) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to be '" + expected.format(TIME_FORMATTER) + "'"));
            }
        });
    }

}
