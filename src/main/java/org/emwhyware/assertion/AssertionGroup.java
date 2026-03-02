package org.emwhyware.assertion;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.assertion.bool.BooleanAssertionGroup;
import org.emwhyware.assertion.bool.BooleanTo;
import org.emwhyware.assertion.bool.BooleanToOrNot;
import org.emwhyware.assertion.collection.CollectionAssertionGroup;
import org.emwhyware.assertion.collection.CollectionTo;
import org.emwhyware.assertion.date.DateAssertionGroup;
import org.emwhyware.assertion.date.DateTo;
import org.emwhyware.assertion.exception.AssertionGroupError;
import org.emwhyware.assertion.number.NumberAssertionGroup;
import org.emwhyware.assertion.number.NumberTo;
import org.emwhyware.assertion.string.StringAssertionGroup;
import org.emwhyware.assertion.string.StringTo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AssertionGroup {
    private final String groupName;
    private final List<Throwable> throwables = new ArrayList<>();

    public StringAssertionGroup string;

    private AssertionGroup(String groupName) {
        this.groupName = groupName;
    }

    public static void group(@NonNull GroupAction action) {
        group("", action);
    }

    public static void group(@NonNull String groupName, @NonNull GroupAction action) {
        final AssertionGroup assertionGroup = new AssertionGroup(groupName);

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
