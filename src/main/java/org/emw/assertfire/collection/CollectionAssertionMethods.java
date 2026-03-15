package org.emw.assertfire.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.AssertionMethods;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Provides assertion methods for {@link Collection} objects.
 * <p>
 * This class supports standard collection assertions such as matching all elements
 * or checking for containment, with additional support for case-insensitivity
 * and order-independent comparisons.
 */
public class CollectionAssertionMethods extends AssertionMethods {
    /**
     * Accessor for specific state-based assertions (e.g., empty, null, size).
     */
    public final CollectionBeAssertionMethods be;

    private final @Nullable Collection<?> actualCollection;
    private final boolean anyOrder;
    private CollectionAssertorHelper helper;

    /**
     * Constructs collection assertion methods with specified configuration.
     *
     * @param group the assertion group to track results
     * @param labelForActual a descriptive label for the collection being tested
     * @param actual the actual collection to assert against
     * @param negated whether the assertion logic should be inverted
     * @param ignoreCase whether string comparisons should ignore case
     * @param anyOrder whether the order of elements should be ignored during matching
     */
    CollectionAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, negated, ignoreCase);
        this.actualCollection = actual;
        this.anyOrder = anyOrder;
        this.be = new CollectionBeAssertionMethods(group, labelForActual, actual, negated, ignoreCase, anyOrder);
        this.helper = new CollectionAssertorHelper(labelForActual, actual, negated, ignoreCase);
    }

    /**
     * Assert that the collection matches exactly the expected elements.
     * @param expected varargs of expected elements
     */
    public void allMatch(Object... expected) {
        this.allMatch(Arrays.asList(expected));
    }

    /**
     * Assert that the collection matches exactly the elements in the expected collection.
     * <p>
     * If {@code anyOrder} is true, the elements are sorted before comparison.
     * If {@code ignoreCase} is true, elements are converted to lowercase strings before comparison.
     * @param expectedCollection the collection of expected elements
     */
    public void allMatch(Collection<?> expectedCollection) {
        if (this.anyOrder) {
            assertCondition(() -> {
                String message = helper.assertionErrorMessage("to all match (in any order) as " + helper.join(expectedCollection));
                if (actualCollection == null) {
                    throw new AssertionError(message);
                } else {
                    final List<String> testActualList = ignoreCase ? actualCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).sorted().toList() : actualCollection.stream().map(o -> o == null ? "null" : o.toString()).sorted().toList();
                    final List<String> testedExpectedList = ignoreCase ? expectedCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).sorted().toList() : expectedCollection.stream().map(o -> o == null ? "null" : o.toString()).sorted().toList();

                    boolean match = true;
                    if (testActualList.size() != testedExpectedList.size()) {
                        match = false;
                    } else {
                        for (int i = 0; i < testActualList.size(); i++) {
                            if (!testActualList.get(i).equals(testedExpectedList.get(i))) {
                                match = false;
                                break;
                            }
                        }
                    }

                    if (negated == match) {
                        throw new AssertionError(message);
                    }
                }
            });
        } else {
            assertCondition(() -> {
                String message = helper.assertionErrorMessage("to all match " + helper.join(expectedCollection));
                if (actualCollection == null) {
                    throw new AssertionError(message);
                } else {
                    final List<String> testActualList = ignoreCase ? actualCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : actualCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();
                    final List<String> testedExpectedList = ignoreCase ? expectedCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : expectedCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();

                    boolean match = true;
                    if (testActualList.size() != testedExpectedList.size()) {
                        match = false;
                    } else {
                        for (int i = 0; i < testActualList.size(); i++) {
                            if (!testActualList.get(i).equals(testedExpectedList.get(i))) {
                                match = false;
                                break;
                            }
                        }
                    }

                    if (negated == match) {
                        throw new AssertionError(message);
                    }
                }
            });
        }
    }

    /**
     * Assert that the collection contains the specified elements.
     * @param expected varargs of elements expected to be found
     */
    public void contain(Object... expected) {
        this.contain(Arrays.asList(expected));
    }

    /**
     * Assert that the collection contains all elements in the expected collection.
     * @param expectedCollection the collection of elements expected to be found
     */
    public void contain(Collection<?> expectedCollection) {
        assertCondition(() -> {
            final String message = helper.assertionErrorMessage("to have " + helper.join(expectedCollection));

            if (actualCollection == null) {
                throw new AssertionError(message);
            } else {
                final List<String> testActualList = ignoreCase ? actualCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : actualCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();
                final List<String> testedExpectedList = ignoreCase ? expectedCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : expectedCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();
                boolean allContained = true;

                for (String expected : testedExpectedList) {
                    if (!testActualList.contains(expected)) {
                        allContained = false;
                        break;
                    }
                }

                if (negated == allContained) {
                    throw new AssertionError(message);
                }
            }
        });
    }
}
