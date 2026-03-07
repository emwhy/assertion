package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionMethods;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectAssertionMethods extends AssertionMethods {
    public final JsonObjectBeAssertionMethods be;
    private final @Nullable Object obj;
    private final boolean negated;
    private final boolean ignoreCase;
    protected final @NonNull List<String> excludedNodes = new ArrayList<>();

    protected JsonObjectAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase, @NonNull List<String> excludedNodes) {
        super(null, "", negated, ignoreCase);
        this.obj = obj;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
        this.excludedNodes.addAll(excludedNodes);
        this.be = new JsonObjectBeAssertionMethods(obj, negated, ignoreCase);
    }

    public void be(@NonNull String expected) {
        assertCondition("Node doesn't exist.", () -> this.obj != null);
        assertCondition("", () -> {
            if (this.obj == null) {
                return false;
            } else if (JsonHelper.isJson(expected) && this.obj instanceof JSONObject) {
                return JsonHelper.jsonMatched((JSONObject) this.obj, expected, this.excludedNodes, this.ignoreCase) != negated;
            } else if (ignoreCase){
                return this.obj.toString().equalsIgnoreCase(expected) != negated;
            } else {
                return this.obj.toString().equals(expected) != negated;
            }
        });
    }

    public void be(@NonNull Object expected) {
        assertCondition("Node doesn't exist.", () -> this.obj != null);
        assertCondition("", () -> {
            if (this.obj == null) {
                return false;
            } else if (expected instanceof JSONObject && this.obj instanceof JSONObject) {
                return JsonHelper.jsonMatched((JSONObject) this.obj, (JSONObject) expected, this.excludedNodes, this.ignoreCase) != negated;
            } else if (expected instanceof Float && this.obj instanceof Float) {
                return (((Float) expected).floatValue() == ((Float) this.obj).floatValue()) != negated;
            } else if (expected instanceof Number && this.obj instanceof Number) {
                return (((Number) expected).doubleValue() == ((Number) this.obj).doubleValue()) != negated;
            } else {
                return this.obj.toString().equals(expected) != negated;
            }
        });
    }

    public void exists() {
        assertCondition("Node doesn't exist.", () -> (this.obj == null) == negated);
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
