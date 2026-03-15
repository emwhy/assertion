package org.emw.assertfire.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertfire.AssertionGroup;

public final class NumberAssertionGroup {
    private final AssertionGroup group;

    public NumberAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public NumberExpect expect(int actual) {
        return expect(Integer.valueOf(actual));
    }

    public NumberExpect expect(long actual) {
        return expect(Long.valueOf(actual));
    }

    public NumberExpect expect(float actual) {
        return expect(Float.valueOf(actual));
    }

    public NumberExpect expect(double actual) {
        return expect(Double.valueOf(actual));
    }

    public NumberExpect expect(@NonNull String labelForActual, int actual) {
        return expect(labelForActual, Integer.valueOf(actual));
    }

    public NumberExpect expect(@NonNull String labelForActual, long actual) {
        return expect(labelForActual, Long.valueOf(actual));
    }

    public NumberExpect expect(@NonNull String labelForActual, float actual) {
        return expect(labelForActual, Float.valueOf(actual));
    }

    public NumberExpect expect(@NonNull String labelForActual, double actual) {
        return expect(labelForActual, Double.valueOf(actual));
    }

    public NumberExpect expect(@NonNull Number actual) {
        return expect("", actual);
    }

    public NumberExpect expect(@NonNull String labelForActual, @NonNull Number actual) {
        return new NumberExpect(group, labelForActual, actual, false);
    }
}
