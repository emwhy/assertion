package org.emw.assertion;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.bool.BooleanAssertionGroup;
import org.emw.assertion.bool.BooleanAssertor;
import org.emw.assertion.bool.BooleanTo;
import org.emw.assertion.collection.CollectionAssertionGroup;
import org.emw.assertion.collection.CollectionAssertor;
import org.emw.assertion.collection.CollectionTo;
import org.emw.assertion.date.DateAssertionGroup;
import org.emw.assertion.date.DateAssertor;
import org.emw.assertion.date.DateTo;
import org.emw.assertion.exception.AssertionGroupError;
import org.emw.assertion.number.NumberAssertionGroup;
import org.emw.assertion.number.NumberAssertor;
import org.emw.assertion.number.NumberTo;
import org.emw.assertion.string.StringAssertionGroup;
import org.emw.assertion.string.StringAssertor;
import org.emw.assertion.string.StringTo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AssertionGroup allows grouping multiple assertions.
 * <p>
 * All assertions within a group are tested before throwing {@link AssertionGroupError}. If multiple assertion failed,
 * all errors are returned in exception message.
 * <p>
 * This is useful when multiple values need to be checked within the same context.
 * <p>
 * <pre>{@code
 *     // This would throw AssertionError on the first "expect". Until this is fixed, you have no idea
 *     // there are other errors or not.
 *     expect("test").to.be("test1");
 *     expect("test").to.be("test2");
 *     expect(1).to.be(1);
 *     expect(1).to.be(0);
 * }</pre>
 * <p>
 * <pre>{@code
 *     // This would assert all of "expect" and show which ones fail, saving repeated execution of tests.
 *     assertionGroup(g -> {
 *         g.expect("test").to.be("test1");
 *         g.expect("test").to.be("test2");
 *         g.expect(1).to.be(1);
 *         g.expect(1).to.be(0);
 *     });
 * }</pre>
 *
 * <p>
 * The exception shows all assertion errors at once.
 * <p>
 * <pre>{@code
 * org.emw.assertion.exception.AssertionGroupError: 3 errors in group
 *
 * 	Error #1: java.lang.AssertionError: Expected 'test' to equal 'test1'.
 *
 * 	Error #2: java.lang.AssertionError: Expected 'test' to equal 'test2'.
 *
 * 	Error #3: java.lang.AssertionError: Expected '1' to equal '0'.
 *
 * 	Error Stack #1:
 * 		at org.emw.assertion.Conditions.assertCondition(Conditions.java:25)
 * 		at org.emw.assertion.string.StringConditions.be(StringConditions.java:23)
 * 		at org.emw.assertion.regression.AssertionTest.lambda$testGroup$69(AssertionTest.java:212)
 * 		at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:71)
 * 		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
 * 		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
 * 		at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
 * 		at jdk.proxy1/jdk.proxy1.$Proxy4.stop(Unknown Source)
 *
 * 	Error Stack #2:
 * 		at org.emw.assertion.Conditions.assertCondition(Conditions.java:25)
 * 		at org.emw.assertion.string.StringConditions.be(StringConditions.java:23)
 * 		at org.emw.assertion.regression.AssertionTest.lambda$testGroup$69(AssertionTest.java:213)
 * 		at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:71)
 * 		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
 * 		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
 * 		at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
 * 		at jdk.proxy1/jdk.proxy1.$Proxy4.stop(Unknown Source)
 *
 * 	Error Stack #3:
 * 		at org.emw.assertion.Conditions.assertCondition(Conditions.java:25)
 * 		at org.emw.assertion.number.NumberConditions.be(NumberConditions.java:35)
 * 		at org.emw.assertion.number.NumberConditions.be(NumberConditions.java:19)
 * 		at org.emw.assertion.regression.AssertionTest.lambda$testGroup$69(AssertionTest.java:215)
 * 		at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:71)
 * 		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
 * 		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
 * 		at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
 * 		at jdk.proxy1/jdk.proxy1.$Proxy4.stop(Unknown Source)
 *
 * 	at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:74)
 * 	at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
 * 	at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
 * 	at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
 * 	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
 * 	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
 * 	at org.testng.internal.invokers.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:141)
 * 	at org.testng.internal.invokers.TestInvoker.invokeMethod(TestInvoker.java:687)
 * }</pre>
 *
 * @see Assertor
 * @see StringAssertor
 * @see BooleanAssertor
 * @see CollectionAssertor
 * @see DateAssertor
 * @see NumberAssertor
 */
public final class AssertionGroup {
    private final List<Throwable> throwables = new ArrayList<>();

    private AssertionGroup() {
    }

    static void group(@NonNull String groupName, @NonNull GroupAction action) {
        final AssertionGroup assertionGroup = new AssertionGroup();

        try {
            action.doAssertions(assertionGroup.instantiateGroup());
        } finally {
            if (!assertionGroup.throwables.isEmpty()) {
                throw new AssertionGroupError(groupName, assertionGroup.throwables);
            }
        }
    }

    private Group instantiateGroup() {
        return new Group(this);
    }

    void addThrowable(@NonNull Throwable throwable) {
        this.throwables.add(throwable);
    }

    public class Group {
        private final StringAssertionGroup string;
        private final CollectionAssertionGroup collection;
        private final NumberAssertionGroup number;
        private final DateAssertionGroup date;
        private final BooleanAssertionGroup bool;

        private Group(AssertionGroup group) {
            this.string = new StringAssertionGroup(group);
            this.collection = new CollectionAssertionGroup(group);
            this.number = new NumberAssertionGroup(group);
            this.date = new DateAssertionGroup(group);
            this.bool = new BooleanAssertionGroup(group);
        }

        public StringTo expect(@Nullable String actual) {
            return expect("", actual);
        }

        public StringTo expect(@NonNull String labelForActual, @Nullable String actual) {
            return string.expect(labelForActual, actual);
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
            return number.expect(labelForActual, actual);
        }


        public DateTo expect(@NonNull Date actual) {
            return expect("", actual.toLocalDate());
        }

        public DateTo expect(@NonNull LocalDate actual) {
            return expect("", actual);
        }

        public DateTo expect(@NonNull String labelForActual, @NonNull Date actual) {
            return expect(labelForActual, actual.toLocalDate());
        }

        public DateTo expect(@NonNull String labelForActual, @NonNull LocalDate actual) {
            return date.expect(labelForActual, actual);
        }

        public CollectionTo expect(@NonNull Object[] actual) {
            return expect("", actual);
        }

        public CollectionTo expect(@NonNull String labelForActual, @NonNull Object[] actual) {
            return collection.expect(labelForActual, actual);
        }

        public CollectionTo expect(@NonNull Collection<?> actual) {
            return expect("", actual);
        }

        public CollectionTo expect(@NonNull String labelForActual, @NonNull Collection<?> actual) {
            return collection.expect(labelForActual, actual);
        }

        public BooleanTo expect(boolean actual) {
            return expect("", actual);
        }

        public BooleanTo expect(@NonNull String labelForActual, boolean actual) {
            return bool.expect(labelForActual, actual);
        }

        public BooleanTo expect(@Nullable Boolean actual) {
            return expect("", actual);
        }

        public BooleanTo expect(@NonNull String labelForActual, @Nullable Boolean actual) {
            return bool.expect(labelForActual, actual);
        }

    }

    public interface GroupAction {
        void doAssertions(Group g);
    }
}
