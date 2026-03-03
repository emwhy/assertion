package org.emw.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.datetime.DateTimeAssertor;

/**
 * A list of <strong>expect</strong> assertion methods for numeric types and objects.
 *
 * @see org.emw.assertion.collection.CollectionAssertor
 * @see org.emw.assertion.string.StringAssertor
 * @see org.emw.assertion.bool.BooleanAssertor
 * @see org.emw.assertion.date.DateAssertor
 * @see DateTimeAssertor
 */
public interface NumberAssertor {
    default NumberTo expect(int actual) {
        return expect(Integer.valueOf(actual));
    }

    default NumberTo expect(long actual) {
        return expect(Long.valueOf(actual));
    }

    default NumberTo expect(float actual) {
        return expect(Float.valueOf(actual));
    }

    default NumberTo expect(double actual) {
        return expect(Double.valueOf(actual));
    }

    default NumberTo expect(@NonNull String labelForActual, int actual) {
        return expect(labelForActual, Integer.valueOf(actual));
    }

    default NumberTo expect(@NonNull String labelForActual, long actual) {
        return expect(labelForActual, Long.valueOf(actual));
    }

    default NumberTo expect(@NonNull String labelForActual, float actual) {
        return expect(labelForActual, Float.valueOf(actual));
    }

    default NumberTo expect(@NonNull String labelForActual, double actual) {
        return expect(labelForActual, Double.valueOf(actual));
    }

    default NumberTo expect(@NonNull Number actual) {
        return expect("", actual);
    }

    default NumberTo expect(@NonNull String labelForActual, @Nullable Number actual) {
        return new NumberTo(null, labelForActual, actual, false);
    }
}
