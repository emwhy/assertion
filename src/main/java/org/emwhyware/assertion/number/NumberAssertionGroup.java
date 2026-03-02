package org.emwhyware.assertion.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.assertion.AssertionGroup;

public final class NumberAssertionGroup {
    private final AssertionGroup group;

    public NumberAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public NumberTo expect(int actual) {
        return expect(Integer.valueOf(actual));
    }

    public NumberTo expect(long actual) {
        return expect(Long.valueOf(actual));
    }

    public NumberTo expect(float actual) {
        return expect(Float.valueOf(actual));
    }

    public NumberTo expect(double actual) {
        return expect(Double.valueOf(actual));
    }

    public NumberTo expect(@NonNull String labelForActual, int actual) {
        return expect(labelForActual, Integer.valueOf(actual));
    }

    public NumberTo expect(@NonNull String labelForActual, long actual) {
        return expect(labelForActual, Long.valueOf(actual));
    }

    public NumberTo expect(@NonNull String labelForActual, float actual) {
        return expect(labelForActual, Float.valueOf(actual));
    }

    public NumberTo expect(@NonNull String labelForActual, double actual) {
        return expect(labelForActual, Double.valueOf(actual));
    }

    public NumberTo expect(@NonNull Number actual) {
        return expect("", actual);
    }

    public NumberTo expect(@NonNull String labelForActual, @NonNull Number actual) {
        return new NumberTo(group, labelForActual, actual, false);
    }
}
