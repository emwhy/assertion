package org.emw.assertflow.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for numeric types and objects.
 *
 * @see org.emw.assertflow.collection.CollectionAssertor
 * @see org.emw.assertflow.string.StringAssertor
 * @see org.emw.assertflow.bool.BooleanAssertor
 * @see org.emw.assertflow.date.DateAssertor
 * @see org.emw.assertflow.datetime.DateTimeAssertor
 * @see org.emw.assertflow.time.TimeAssertor
 * @see org.emw.assertflow.json.JsonAssertor
 */
public interface NumberAssertor {
    default NumberExpect expect(int actual) {
        return expect(Integer.valueOf(actual));
    }

    default NumberExpect expect(long actual) {
        return expect(Long.valueOf(actual));
    }

    default NumberExpect expect(float actual) {
        return expect(Float.valueOf(actual));
    }

    default NumberExpect expect(double actual) {
        return expect(Double.valueOf(actual));
    }

    default NumberExpect expect(@NonNull String labelForActual, int actual) {
        return expect(labelForActual, Integer.valueOf(actual));
    }

    default NumberExpect expect(@NonNull String labelForActual, long actual) {
        return expect(labelForActual, Long.valueOf(actual));
    }

    default NumberExpect expect(@NonNull String labelForActual, float actual) {
        return expect(labelForActual, Float.valueOf(actual));
    }

    default NumberExpect expect(@NonNull String labelForActual, double actual) {
        return expect(labelForActual, Double.valueOf(actual));
    }

    default NumberExpect expect(@NonNull Number actual) {
        return expect("", actual);
    }

    default NumberExpect expect(@NonNull String labelForActual, @Nullable Number actual) {
        return new NumberExpect(null, labelForActual, actual, false);
    }
}
