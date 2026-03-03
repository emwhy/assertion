package org.emw.assertion;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertion.bool.BooleanAssertor;
import org.emw.assertion.collection.CollectionAssertor;
import org.emw.assertion.date.DateAssertor;
import org.emw.assertion.datetime.DateTimeAssertor;
import org.emw.assertion.number.NumberAssertor;
import org.emw.assertion.string.StringAssertor;

/**
 * The main accessor for all assertions.
 * <p>
 * To use, implement this interface.
 * <p>
 * <pre>{@code
 * // Implementing gives access to all "expect" methods within the class.
 * public class TestClass implements Assertor {
 *     @Test
 *     public void test() {
 *         ...
 *         // These methods become available.
 *         expect("Test 1", testText).to.startWith("te");
 *         expect("Test 2", testText).to.caseInsensitively.startWith("TES");
 *     }
 * }
 * }</pre>
 *
 * @see StringAssertor
 * @see BooleanAssertor
 * @see CollectionAssertor
 * @see DateAssertor
 * @see NumberAssertor
 * @see AssertionGroup
 * @see DateTimeAssertor
 */
public interface Assertor extends StringAssertor, CollectionAssertor, BooleanAssertor, DateAssertor, NumberAssertor, DateTimeAssertor {
    default void assertionGroup(AssertionGroup.@NonNull GroupAction action) {
        assertionGroup("", action);
    }

    default void assertionGroup(@NonNull String groupName, AssertionGroup.@NonNull GroupAction action) {
        AssertionGroup.group(groupName, action);
    }
}
