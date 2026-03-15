package org.emw.assertfire;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.bool.BooleanAssertionGroup;
import org.emw.assertfire.bool.BooleanAssertor;
import org.emw.assertfire.bool.BooleanExpect;
import org.emw.assertfire.collection.CollectionAssertionGroup;
import org.emw.assertfire.collection.CollectionAssertor;
import org.emw.assertfire.collection.CollectionExpect;
import org.emw.assertfire.date.DateAssertionGroup;
import org.emw.assertfire.date.DateAssertor;
import org.emw.assertfire.date.DateExpect;
import org.emw.assertfire.datetime.DateTimeAssertionGroup;
import org.emw.assertfire.datetime.DateTimeAssertor;
import org.emw.assertfire.datetime.DateTimeExpect;
import org.emw.assertfire.exception.AssertionGroupError;
import org.emw.assertfire.number.NumberAssertionGroup;
import org.emw.assertfire.number.NumberAssertor;
import org.emw.assertfire.number.NumberExpect;
import org.emw.assertfire.string.StringAssertionGroup;
import org.emw.assertfire.string.StringAssertor;
import org.emw.assertfire.string.StringExpect;
import org.emw.assertfire.time.TimeAssertionGroup;
import org.emw.assertfire.time.TimeExpect;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
 * @see AssertFire
 * @see StringAssertor
 * @see BooleanAssertor
 * @see CollectionAssertor
 * @see DateAssertor
 * @see NumberAssertor
 * @see DateTimeAssertor
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
        private final DateTimeAssertionGroup datetime;
        private final TimeAssertionGroup time;
        private final AssertionGroup group;

        private Group(AssertionGroup group) {
            this.group = group;
            this.string = new StringAssertionGroup(group);
            this.collection = new CollectionAssertionGroup(group);
            this.number = new NumberAssertionGroup(group);
            this.date = new DateAssertionGroup(group);
            this.bool = new BooleanAssertionGroup(group);
            this.datetime = new DateTimeAssertionGroup(group);
            this.time = new  TimeAssertionGroup(group);
        }

        public StringExpect expect(@Nullable String actual) {
            return expect("", actual);
        }

        public StringExpect expect(@NonNull String labelForActual, @Nullable String actual) {
            return string.expect(labelForActual, actual);
        }

        public NumberExpect expect(int actual) {
            return expect(Integer.valueOf(actual));
        }

        public NumberExpect expect(long actual) {
            return expect(Long.valueOf(actual));
        }

        public NumberExpect expect(float actual) {
            return expect(Float.valueOf(actual));
        }

        public NumberExpect expect(double actual) {
            return expect(Double.valueOf(actual));
        }

        public NumberExpect expect(@NonNull String labelForActual, int actual) {
            return expect(labelForActual, Integer.valueOf(actual));
        }

        public NumberExpect expect(@NonNull String labelForActual, long actual) {
            return expect(labelForActual, Long.valueOf(actual));
        }

        public NumberExpect expect(@NonNull String labelForActual, float actual) {
            return expect(labelForActual, Float.valueOf(actual));
        }

        public NumberExpect expect(@NonNull String labelForActual, double actual) {
            return expect(labelForActual, Double.valueOf(actual));
        }

        public NumberExpect expect(@NonNull Number actual) {
            return expect("", actual);
        }

        public NumberExpect expect(@NonNull String labelForActual, @NonNull Number actual) {
            return number.expect(labelForActual, actual);
        }

        public DateExpect expect(@NonNull Date actual) {
            return expect("", actual.toLocalDate());
        }

        public DateExpect expect(@NonNull LocalDate actual) {
            return expect("", actual);
        }

        public DateExpect expect(@NonNull String labelForActual, @NonNull Date actual) {
            return expect(labelForActual, actual.toLocalDate());
        }

        public DateExpect expect(@NonNull String labelForActual, @NonNull LocalDate actual) {
            return date.expect(labelForActual, actual);
        }

        public CollectionExpect expect(@NonNull Object[] actual) {
            return expect("", actual);
        }

        public CollectionExpect expect(@NonNull String labelForActual, @NonNull Object[] actual) {
            return collection.expect(labelForActual, actual);
        }

        public CollectionExpect expect(@NonNull Collection<?> actual) {
            return expect("", actual);
        }

        public CollectionExpect expect(@NonNull String labelForActual, @NonNull Collection<?> actual) {
            return collection.expect(labelForActual, actual);
        }

        public BooleanExpect expect(boolean actual) {
            return expect("", actual);
        }

        public BooleanExpect expect(@NonNull String labelForActual, boolean actual) {
            return bool.expect(labelForActual, actual);
        }

        public BooleanExpect expect(@Nullable Boolean actual) {
            return expect("", actual);
        }

        public BooleanExpect expect(@NonNull String labelForActual, @Nullable Boolean actual) {
            return bool.expect(labelForActual, actual);
        }

        public DateTimeExpect expect(@Nullable LocalDateTime actual) {
            return expect("", actual);
        }

        public DateTimeExpect expect(@NonNull String labelForActual, @Nullable LocalDateTime actual) {
            return datetime.expect(labelForActual, actual);
        }

        public TimeExpect expect(@Nullable LocalTime actual) {
            return expect("", actual);
        }

        public TimeExpect expect(@NonNull String labelForActual, @Nullable LocalTime actual) {
            return time.expect(labelForActual, actual);
        }

        public void assertWith(AnyAssertionAction action) {
            try {
                action.assertWith();
            } catch (Throwable ex) {
                group.throwables.add(ex);
            }
        }

    }

    public interface AnyAssertionAction {
        void assertWith();
    }

    public interface GroupAction {
        void doAssertions(Group g);
    }
}
