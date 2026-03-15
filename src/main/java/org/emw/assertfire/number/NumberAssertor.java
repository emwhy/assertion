package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A list of <strong>expect</strong> assertion methods for numeric types and objects.
 *
 * @see org.emw.assertfire.collection.CollectionAssertor
 * @see org.emw.assertfire.string.StringAssertor
 * @see org.emw.assertfire.bool.BooleanAssertor
 * @see org.emw.assertfire.date.DateAssertor
 * @see org.emw.assertfire.datetime.DateTimeAssertor
 * @see org.emw.assertfire.time.TimeAssertor
 * @see org.emw.assertfire.json.JsonAssertor
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
