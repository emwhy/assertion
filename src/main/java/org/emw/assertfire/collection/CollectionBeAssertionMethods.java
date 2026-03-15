package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.util.Collection;

/**
 * Provides state-based assertion methods for {@link Collection} objects.
 * <p>
 * This class handles assertions regarding the collection's structure and metadata,
 * such as whether it is empty, its nullity, or its exact size.
 */
public final class CollectionBeAssertionMethods extends AssertionMethods {
    private final @Nullable Collection<?> actualCollection;
    private final CollectionAssertorHelper helper;

    /**
     * Constructs collection state assertion methods with specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the collection being tested
     * @param actual the actual collection to assert against
     * @param negated whether the assertion logic should be inverted
     * @param ignoreCase whether string comparisons (if applicable) should ignore case
     * @param anyOrder whether the order of elements (if applicable) should be ignored
     */
    CollectionBeAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, negated, ignoreCase);
        this.actualCollection = actual;
        this.helper = new CollectionAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }

    /**
     * Assert that the collection is empty.
     * <p>
     * If negated, asserts that the collection contains at least one element.
     */
    public void empty() {
        assertCondition(() -> {
            if (actualCollection == null || negated == actualCollection.isEmpty()) {
                throw new AssertionError(helper.assertionErrorMessage("to be empty"));
            }
        });
    }

    /**
     * Assert that the collection is {@code null}.
     * <p>
     * If negated, asserts that the collection is not {@code null}.
     */
    public void nullValue() {
        assertCondition(() -> {
            if (negated == (actualCollection == null)) {
                throw new AssertionError(helper.assertionErrorMessage("to be null"));
            }
        });
    }

    /**
     * Assert that the collection has the exact expected number of elements.
     *
     * @param expectedSize the expected number of elements in the collection
     */
    public void sizeOf(int expectedSize) {
        assertCondition(() -> {
            if (actualCollection == null || (actualCollection.size() == expectedSize) == negated) {
                throw new AssertionError(helper.assertionErrorMessage("to have size of " + expectedSize + ", but was " + (actualCollection == null ? "null collection" : actualCollection.size())));
            }
        });
    }
}
