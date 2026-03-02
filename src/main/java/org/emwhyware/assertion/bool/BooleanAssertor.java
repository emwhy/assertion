package org.emwhyware.assertion.bool;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BooleanAssertor {
    default BooleanTo expect(boolean actual) {
        return expect("", actual);
    }

    default BooleanTo expect(@NonNull String labelForActual, boolean actual) {
        return new BooleanToOrNot(null, labelForActual, actual, false);
    }

    default BooleanTo expect(@Nullable Boolean actual) {
        return expect("", actual);
    }

    default BooleanTo expect(@NonNull String labelForActual, @Nullable Boolean actual) {
        return new BooleanTo(null, labelForActual, actual, false);
    }
}
