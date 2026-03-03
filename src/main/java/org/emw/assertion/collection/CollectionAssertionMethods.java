package org.emw.assertion.collection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionGroup;
import org.emw.assertion.AssertionMethods;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionAssertionMethods extends AssertionMethods {
    public final CollectionBeAssertionMethods be;

    private final @Nullable Collection<?> actualCollection;
    private final boolean anyOrder;

    CollectionAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable Collection<?> actual, boolean negated, boolean ignoreCase, boolean anyOrder) {
        super(group, labelForActual, negated, ignoreCase);
        this.actualCollection = actual;
        this.anyOrder = anyOrder;
        this.be = new CollectionBeAssertionMethods(group, labelForActual, actual, negated, ignoreCase, anyOrder);
    }

    public void be(Object... expected) {
        this.be(Arrays.asList(expected));
    }

    public void be(Collection<?> expectedCollection) {
        if (this.anyOrder) {
            assertCondition(partialAssertionErrorMessage() + "to be same (in any order) as " + join(expectedCollection) + ".", () -> {
                if (actualCollection == null) {
                    return false;
                } else {
                    final List<String> testActualList = ignoreCase ? actualCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).sorted().toList() : actualCollection.stream().map(o -> o == null ? "null" : o.toString()).sorted().toList();
                    final List<String> testedExpectedList = ignoreCase ? expectedCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).sorted().toList() : expectedCollection.stream().map(o -> o == null ? "null" : o.toString()).sorted().toList();

                    if (testActualList.size() != testedExpectedList.size()) {
                        return negated;
                    } else {
                        for (int i = 0; i < testActualList.size(); i++) {
                            if (!testActualList.get(i).equals(testedExpectedList.get(i))) {
                                return negated;
                            }
                        }
                        return !negated;
                    }
                }
            });
        } else {
            assertCondition(partialAssertionErrorMessage() + "to be same as " + join(expectedCollection) + ".", () -> {
                if (actualCollection == null) {
                    return false;
                } else {
                    final List<String> testActualList = ignoreCase ? actualCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : actualCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();
                    final List<String> testedExpectedList = ignoreCase ? expectedCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : expectedCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();

                    if (testActualList.size() != testedExpectedList.size()) {
                        return negated;
                    } else {
                        for (int i = 0; i < testActualList.size(); i++) {
                            if (!testActualList.get(i).equals(testedExpectedList.get(i))) {
                                return negated;
                            }
                        }
                        return !negated;
                    }
                }
            });
        }
    }

    public void haveSizeOf(int expectedSize) {
        assertCondition(partialAssertionErrorMessage() + "to have size of " + expectedSize + ", but was " + (actualCollection == null ? "null collection" : actualCollection.size()) + ".", () -> {
            if (actualCollection == null) {
                return false;
            } else {
                return (actualCollection.size() == expectedSize) != negated;
            }
        });
    }

    public void have(Object... expected) {
        this.have(Arrays.asList(expected));
    }

    public void have(Collection<?> expectedCollection) {
        assertCondition(partialAssertionErrorMessage() + "to be same (in any order) as " + join(expectedCollection) + ".", () -> {
            if (actualCollection == null) {
                return false;
            } else {
                final List<String> testActualList = ignoreCase ? actualCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : actualCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();
                final List<String> testedExpectedList = ignoreCase ? expectedCollection.stream().map(o -> o == null ? "null" : o.toString().toLowerCase()).toList() : expectedCollection.stream().map(o -> o == null ? "null" : o.toString()).toList();

                for (String expected : testedExpectedList) {
                    if (!testActualList.contains(expected)) {
                        return negated;
                    }
                }
                return !negated;
            }
        });
    }

    private String partialAssertionErrorMessage() {
        if (labelForActual.isEmpty() && ignoreCase) {
            return "Ignoring cases, expected " + join(actualCollection) + (negated?" not":"") + " ";
        } else if (labelForActual.isEmpty() && !ignoreCase) {
            return "Expected " + join(actualCollection) + (negated?" not":"") + " ";
        } else if (ignoreCase) {
            return "Ignoring cases, expected actual value(" + join(actualCollection) + ") of '" + labelForActual + "'" + (negated?" not":"") + " ";
        } else {
            return "Expected actual value(" + join(actualCollection) + ") of '" + labelForActual + "'" + (negated?" not":"") + " ";
        }
    }

    private String join(@Nullable Collection<?> collection) {
        if (collection == null) {
            return "null";
        } else {
            return "'" + String.join(", ", collection.stream().map(o -> o == null ? "null" : o.toString()).toList()) + "'";
        }
    }
}
