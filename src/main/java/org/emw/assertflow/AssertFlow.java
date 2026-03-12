package org.emw.assertflow;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertflow.bool.BooleanAssertor;
import org.emw.assertflow.collection.CollectionAssertor;
import org.emw.assertflow.date.DateAssertor;
import org.emw.assertflow.datetime.DateTimeAssertor;
import org.emw.assertflow.json.JsonAssertor;
import org.emw.assertflow.number.NumberAssertor;
import org.emw.assertflow.string.StringAssertor;
import org.emw.assertflow.time.TimeAssertor;

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
 * @see TimeAssertor
 * @see JsonAssertor
 */
public interface AssertFlow extends StringAssertor, CollectionAssertor, BooleanAssertor, DateAssertor, NumberAssertor, DateTimeAssertor, TimeAssertor, JsonAssertor {
    default void assertionGroup(AssertionGroup.@NonNull GroupAction action) {
        assertionGroup("", action);
    }

    default void assertionGroup(@NonNull String groupName, AssertionGroup.@NonNull GroupAction action) {
        AssertionGroup.group(groupName, action);
    }
}
