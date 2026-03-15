package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;

import java.util.Arrays;
import java.util.Collection;

public final class CollectionAssertionGroup {
    private final AssertionGroup group;

    public CollectionAssertionGroup(@NonNull AssertionGroup group) {
        this.group = group;
    }

    public CollectionExpect expect(@Nullable Object[] actual) {
        return expect("", actual);
    }

    public CollectionExpect expect(@NonNull String labelForActual, @Nullable Object[] actual) {
        return new CollectionExpect(group, labelForActual, actual == null ? null : Arrays.stream(actual).toList(), false, false, false);
    }

    public CollectionExpect expect(@Nullable Collection<?> actual) {
        return expect("", actual);
    }

    public CollectionExpect expect(@NonNull String labelForActual, @Nullable Collection<?> actual) {
        return new CollectionExpect(group, labelForActual, actual, false, false, false);
    }
}
