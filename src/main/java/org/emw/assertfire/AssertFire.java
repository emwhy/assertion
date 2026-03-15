package org.emw.assertfire;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertfire.bool.BooleanAssertor;
import org.emw.assertfire.collection.CollectionAssertor;
import org.emw.assertfire.date.DateAssertor;
import org.emw.assertfire.datetime.DateTimeAssertor;
import org.emw.assertfire.json.JsonAssertor;
import org.emw.assertfire.number.NumberAssertor;
import org.emw.assertfire.string.StringAssertor;
import org.emw.assertfire.time.TimeAssertor;

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
public interface AssertFire extends StringAssertor, CollectionAssertor, BooleanAssertor, DateAssertor, NumberAssertor, DateTimeAssertor, TimeAssertor, JsonAssertor {
    default void assertionGroup(AssertionGroup.@NonNull GroupAction action) {
        assertionGroup("", action);
    }

    default void assertionGroup(@NonNull String groupName, AssertionGroup.@NonNull GroupAction action) {
        AssertionGroup.group(groupName, action);
    }
}
