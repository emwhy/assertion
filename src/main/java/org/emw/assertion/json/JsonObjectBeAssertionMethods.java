package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionMethods;

public class JsonObjectBeAssertionMethods extends AssertionMethods {
    private final @Nullable Object obj;
    private final boolean negated;
    private final boolean ignoreCase;

    protected JsonObjectBeAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase) {
        super(null, "", negated, ignoreCase);
        this.obj = obj;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
    }

//    public final StringBeAssertionMethods be;
//    private final @Nullable String actual;
//
//    JsonObjectAssertionMethods(@Nullable AssertionGroup group, @NonNull String labelForActual, @Nullable String actual, boolean negated, boolean ignoreCase) {
//        super(group, labelForActual, negated, ignoreCase);
//        this.actual = actual;
//        this.be = new StringBeAssertionMethods(group, labelForActual, actual, negated, ignoreCase);
//    }
//
//    public void be(@NonNull String expected) {
//        assertCondition(partialAssertionErrorMessage() + "to equal '" + expected + "'.", () -> {
//            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
//            final String testedExpected = ignoreCase ? expected.toLowerCase() : expected;
//
//            return actual != null && negated != testedActual.equals(testedExpected);
//        });
//    }
//
//    public void contain(@NonNull String expected) {
//        assertCondition(partialAssertionErrorMessage() + "to contain '" + expected + "'.", () -> {
//            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
//            final String testedExpected = ignoreCase ? expected.toLowerCase() : expected;
//
//            return actual != null && negated != testedActual.contains(testedExpected);
//        });
//
//    }
//
//    public void startWith(@NonNull String prefix) {
//        assertCondition(partialAssertionErrorMessage() + "to start with '" + prefix + "'.", () -> {
//            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
//            final String testedExpected = ignoreCase ? prefix.toLowerCase() : prefix;
//
//            return actual != null && negated != testedActual.startsWith(testedExpected);
//        });
//
//    }
//
//    public void endWith(@NonNull String suffix) {
//        assertCondition(partialAssertionErrorMessage() + "to end with '" + suffix + "'.", () -> {
//            final String testedActual = ignoreCase ? (actual == null ? "" : actual.toLowerCase()) : (actual == null ? "" : actual);
//            final String testedExpected = ignoreCase ? suffix.toLowerCase() : suffix;
//
//            return actual != null && negated != testedActual.endsWith(testedExpected);
//        });
//
//    }
//
//    public void match(@NonNull String regex) {
//        final Pattern pattern = Pattern.compile(regex);
//
//        assertCondition(partialAssertionErrorMessage() + "to match the pattern '" + regex + "'.", () -> {
//            return actual != null && negated != pattern.matcher(actual).matches();
//        });
//    }
//
//    private String partialAssertionErrorMessage() {
//        if (labelForActual.isEmpty() && ignoreCase) {
//            return "Ignoring cases, expected '" + actual + "'" + (negated?" not":"") + " ";
//        } else if (labelForActual.isEmpty() && !ignoreCase) {
//            return "Expected '" + actual + "'" + (negated?" not":"") + " ";
//        } else if (ignoreCase) {
//            return "Ignoring cases, expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
//        } else {
//            return "Expected actual value('" + actual + "') of '" + labelForActual + "'" + (negated?" not":"") + " ";
//        }
//    }

}
