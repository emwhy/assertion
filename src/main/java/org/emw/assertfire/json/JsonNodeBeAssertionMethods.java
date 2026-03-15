package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.bool.BooleanAssertor;
import org.emw.assertfire.date.DateAssertor;
import org.emw.assertfire.datetime.DateTimeAssertor;
import org.emw.assertfire.number.NumberAssertor;
import org.emw.assertfire.string.StringAssertor;
import org.emw.assertfire.time.TimeAssertor;

import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;

public class JsonNodeBeAssertionMethods extends JsonAssertionMethods {
    public final JsonStringAssertions string;
    public final JsonNumberAssertions number;
    public final JsonBooleanAssertions bool;
    public final JsonDateAssertions date;
    public final JsonDateTimeAssertions dateTime;
    public final JsonTimeAssertions time;

    protected JsonNodeBeAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable Object object, boolean negated, boolean ignoreCase) {
        super(group, object, negated, ignoreCase, false, List.of());

        this.string = new JsonStringAssertions(object);
        this.number = new JsonNumberAssertions(object);
        this.bool = new JsonBooleanAssertions(object);
        this.date = new JsonDateAssertions(object);
        this.dateTime = new JsonDateTimeAssertions(object);
        this.time = new JsonTimeAssertions(object);
    }

    /**
     * Assert that the JSON node contains a "null" value.
     */
    public void nullValue() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (String.valueOf(object).equals("null")) {
                    throw new AssertionError("Expected node to not be null.");
                }
            } else if (!String.valueOf(object).equals("null")) {
                throw new AssertionError("Expected node to be null.");
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of string.
     */
    public void stringType() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (object instanceof String) {
                    throw new AssertionError("Expected node to not be a type of string.");
                }
            } else if (!(object instanceof String)) {
                throw new AssertionError("Expected node to be a type of string.");
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of number.
     */
    public void numberType() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (object instanceof Number) {
                    throw new AssertionError("Expected node to not be a type of number.");
                }
            } else if (!(object instanceof Number)) {
                throw new AssertionError("Expected node to be a type of number.");
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of boolean.
     */
    public void booleanType() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (String.valueOf(object).equals("true") || String.valueOf(object).equals("false")) {
                    throw new AssertionError("Expected node to not be a type of boolean.");
                }
            } else if (!String.valueOf(object).equals("true") && !String.valueOf(object).equals("false")) {
                throw new AssertionError("Expected node to be a type of boolean.");
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of date against typical formats. If the node is in a custom time format,
     * use {@link #dateType(String)} to define the format.
     */
    public void dateType() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (JsonDateAssertions.isDate(object)) {
                    throw new AssertionError("Expected node to not be a type of date: " + object);
                }
            } else {
                if (!JsonDateAssertions.isDate(object)) {
                    throw new AssertionError("Expected node is a type of date: " + object);
                }
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of date against provided time format.
     */
    public void dateType(@NonNull String format) {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (JsonDateAssertions.isDate(object, DateTimeFormatter.ofPattern(format))) {
                    throw new AssertionError("Expected node to not be a type of date of specified format: " + object + ", format: " + format);
                }
            } else {
                if (!JsonDateAssertions.isDate(object, DateTimeFormatter.ofPattern(format))) {
                    throw new AssertionError("Expected node to be a type of date of specified format: " + object +  ", format: " + format);
                }
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of date/time against typical formats. If the node is in a custom time format,
     * use {@link #dateTimeType(String)} to define the format.
     */
    public void dateTimeType() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (JsonDateTimeAssertions.isDateTime(object)) {
                    throw new AssertionError("Expected node to not be a type of date-time: " + object);
                }
            } else {
                if (!JsonDateTimeAssertions.isDateTime(object)) {
                    throw new AssertionError("Expected node to be a type of date-time: " + object);
                }
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of date/time against provided time format.
     */
    public void dateTimeType(@NonNull String format) {
        assertCondition(() -> {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            final Object object = this.object();

            if (negated) {
                if (JsonDateTimeAssertions.isDateTime(object, formatter)) {
                    throw new AssertionError("Expected node to not be a type of date-time of specified format: " + object + ", format: " + format);
                }
            } else {
                if (!JsonDateTimeAssertions.isDateTime(object, formatter)) {
                    throw new AssertionError("Expected node to be a type of date-time of specified format: " + object + ", format: " + format);
                }
            }

        });
    }

    /**
     * Assert that the JSON node contains a type of time against typical formats. If the node is in a custom time format,
     * use {@link #timeType(String)} to define the format.
     */
    public void timeType() {
        assertCondition(() -> {
            final Object object = this.object();

            if (negated) {
                if (JsonTimeAssertions.isTime(object)) {
                    throw new AssertionError("Expected node to not be a type of time: " + object);
                }
            } else {
                if (!JsonTimeAssertions.isTime(object)) {
                    throw new AssertionError("Expected node to be a type of time: " + object);
                }
            }
        });
    }

    /**
     * Assert that the JSON node contains a type of time against provided time format.
     */
    public void timeType(@NonNull String format) {
        assertCondition(() -> {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            final Object object = this.object();

            if (negated) {
                if (JsonTimeAssertions.isTime(object, formatter)) {
                    throw new AssertionError("Expected node to not be a type of time of specified format: " + object + ", format: " + format);
                }
            } else {
                if (!JsonTimeAssertions.isTime(object, formatter)) {
                    throw new AssertionError("Expected node to be a type of time of specified format: " + object + ", format: " + format);
                }
            }
        });
    }

    /**
     * Inner class to handle string node assertions.
     */
    public class JsonStringAssertions {
        private final @Nullable Object actual;
        private final JsonStringAssertor assertor = new JsonStringAssertor();

        private JsonStringAssertions(@Nullable Object actual) {
            this.actual = actual;
        }

        /**
         * Ensure that the object is of a string type, or throw error.
         * @return a string value
         */
        private String actualString() {
            final Object object = object();

            if (this.actual == null || !(object instanceof String)) {
                throw new AssertionError("Expected node to be a string type.");
            } else {
                return ((String) this.actual).trim();
            }
        }

        /**
         * Assert that the string node is empty.
         */
        public void empty() {
            assertCondition(() -> {
                final String actualString = this.actualString();

                if (negated) {
                    assertor.expect("JsonNode String", actualString).to.not.be.empty();
                } else {
                    assertor.expect("JsonNode String", actualString).to.be.empty();
                }
            });
        }

        /**
         * Assert that the string node starts with a string.
         * @param startWith start with string
         */
        public void startWith(String startWith) {
            assertCondition(() -> {
                final String actualString = this.actualString();

                if (ignoreCase) {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.not.startWith(startWith);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.startWith(startWith);
                    }
                } else {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.not.startWith(startWith);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.startWith(startWith);
                    }
                }
            });
        }

        /**
         * Assert that the string node ends with a string.
         * @param endWith end with string
         */
        public void endWith(String endWith) {
            assertCondition(() -> {
                final String actualString = this.actualString();

                if (ignoreCase) {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.not.endWith(endWith);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.endWith(endWith);
                    }

                } else {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.not.endWith(endWith);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.endWith(endWith);
                    }
                }
            });
        }

        /**
         * Assert that the string node contains a string.
         * @param contained contained string
         */
        public void contain(String contained) {
            assertCondition(() -> {
                final String actualString = this.actualString();

                if (ignoreCase) {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.not.contain(contained);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.contain(contained);
                    }
                } else {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.not.contain(contained);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.contain(contained);
                    }
                }
            });
        }

        /**
         * Assert that the string node match with the regular expression pattern.
         * @param matchPattern regular expression pattern
         */
        public void match(String matchPattern) {
            assertCondition(() -> {
                final String actualString = this.actualString();

                if (ignoreCase) {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.not.match(matchPattern);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.match(matchPattern);
                    }

                } else {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.not.match(matchPattern);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.match(matchPattern);
                    }
                }
            });
        }

        /**
         * Assert that the string node is one of strings in list.
         * @param oneOfStrings a list of strings
         */
        public void oneOf(String... oneOfStrings) {
            assertCondition(() -> {
                final String actualString = this.actualString();

                if (ignoreCase) {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.not.be.oneOf(oneOfStrings);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.caseInsensitively.be.oneOf(oneOfStrings);
                    }
                } else {
                    if (negated) {
                        assertor.expect("JsonNode String", actualString).to.not.be.oneOf(oneOfStrings);
                    } else {
                        assertor.expect("JsonNode String", actualString).to.be.oneOf(oneOfStrings);
                    }
                }
            });
        }
    }

    /**
     * Inner class to handle number node assertions.
     */
    public class JsonNumberAssertions {
        private final @Nullable Object actual;
        private final JsonNumberAssertor assertor = new JsonNumberAssertor();

        private JsonNumberAssertions(@Nullable Object actual) {
            this.actual = actual;
        }

        /**
         * Ensure that the object is of a number type, or throw error.
         * @return a number value
         */
        private Number actualNumber() {
            if (!(actual instanceof Number)) {
                throw new AssertionError("Expected node to be a number type.");
            } else {
                return (Number) actual;
            }
        }

        /**
         * Assert that the number node is greater than a number.
         * @param expected compared number
         */
        public void greaterThan(@NonNull Number expected) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.moreThan(expected);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.moreThan(expected);
                }
            });
        }

        /**
         * Assert that the number node is less than a number.
         * @param expected compared number
         */
        public void lessThan(@NonNull Number expected) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.lessThan(expected);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.lessThan(expected);
                }
            });
        }

        /**
         * Assert that the number node is greater than or equals to a number.
         * @param expected compared number
         */
        public void greaterThanOrEqual(@NonNull Number expected) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.moreThanOrEqual(expected);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.moreThanOrEqual(expected);
                }
            });
        }

        /**
         * Assert that the number node is less than or equals to a number.
         * @param expected compared number
         */

        public void lessThanOrEqual(@NonNull Number expected) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.lessThanOrEqual(expected);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.lessThanOrEqual(expected);
                }
            });
        }

        /**
         * Assert that the number node is between numbers.
         * @param expectedLower lower number, inclusive
         * @param expectedUpper Upper number, inclusive
         */
        public void between(@NonNull Number expectedLower, @NonNull Number expectedUpper) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.between(expectedLower, expectedUpper);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.between(expectedLower, expectedUpper);
                }
            });
        }
    }

    /**
     * Inner class to handle boolean node assertions.
     */
    public class JsonBooleanAssertions {
        private final @Nullable Object actual;
        private final JsonBooleanAssertor assertor = new JsonBooleanAssertor();

        private JsonBooleanAssertions(@Nullable Object actual) {
            this.actual = actual;
        }

        /**
         * Ensure that the object is of a boolean type, or throw error.
         * @return a boolean value
         */
        private boolean actualBoolean() {
            if (String.valueOf(actual).equals("true")) {
                return true;
            } else if (String.valueOf(actual).equals("false")) {
                return false;
            } else {
                throw new AssertionError("Expected node to be a boolean type.");
            }
        }

        /**
         * Assert that the boolean node is a true value.
         */
        public void trueValue() {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Boolean", actualBoolean()).to.not.be.trueValue();
                } else {
                    assertor.expect("JsonNode Boolean", actualBoolean()).to.be.trueValue();
                }
            });
        }
    }

    /**
     * Inner class to handle date node assertions.
     */
    public class JsonDateAssertions {
        private static final DateTimeFormatter[] DATE_FORMATTERS = new DateTimeFormatter[] {
                DateTimeFormatter.BASIC_ISO_DATE,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL),
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG),
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT),
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("M/d/yyyy"),
        };
        private final @Nullable Object actual;
        private final @Nullable DateTimeFormatter definedFormat;
        private final JsonDateAssertor assertor = new JsonDateAssertor();

        private JsonDateAssertions(@Nullable Object actual) {
            this(actual, null);
        }

        private JsonDateAssertions(@Nullable Object actual, @Nullable DateTimeFormatter definedFormat) {
            this.actual = actual;
            this.definedFormat = definedFormat;
        }

        /**
         * Specify a date time format to use.
         * @param definedFormat DateTimeFormatter to be used
         * @return itself
         */
        public JsonDateAssertions with(DateTimeFormatter definedFormat) {
            return new JsonDateAssertions(this.actual, definedFormat);
        }

        /**
         * Assert that the date node is the same as a date.
         * @param date a SQL date value
         */
        public void of(@NonNull Date date) {
            this.of(date.toLocalDate());
        }

        /**
         * Assert that the date node is before a date.
         * @param date a SQL date value
         */
        public void before(@NonNull Date date) {
            this.before(date.toLocalDate());
        }

        /**
         * Assert that the date node is after a date.
         * @param date a SQL date value
         */
        public void after(@NonNull Date date) {
            this.after(date.toLocalDate());
        }

        /**
         * Assert that the date node is the same or before a date.
         * @param date a SQL date value
         */
        public void sameOrBefore(@NonNull Date date) {
            this.sameOrBefore(date.toLocalDate());
        }

        /**
         * Assert that the date node is the same or after a date.
         * @param date a SQL date value
         */
        public void sameOrAfter(@NonNull Date date) {
            this.sameOrAfter(date.toLocalDate());
        }

        /**
         * Assert that the date node is between dates.
         * @param lowerDate lower SQL date, inclusive
         * @param upperDate upper SQL date, inclusive
         */
        public void between(@NonNull Date lowerDate, @NonNull Date upperDate) {
            this.between(lowerDate.toLocalDate(), upperDate.toLocalDate());
        }

        /**
         * Assert that the date node is the same as a date.
         * @param expectedDate a local date
         */
        public void of(@NonNull LocalDate expectedDate) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be(expectedDate);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be(expectedDate);
                }
            });
        }

        /**
         * Assert that the date node is the current date.
         */
        public void today() {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.today();
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.today();
                }
            });
        }

        /**
         * Assert that the date node is of a specified year.
         * @param year a year
         */
        public void year(int year) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.year(year);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.year(year);
                }
            });
        }

        /**
         * Assert that the date node is the same date as the provided date time.
         * @param expected a local date time
         */
        public void sameDateAs(@NonNull LocalDateTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.sameDateAs(expected);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.sameDateAs(expected);
                }
            });
        }

        /**
         * Assert that the date node is before a date.
         * @param expected a local date
         */
        public void before(@NonNull LocalDate expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.before(expected);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.before(expected);
                }
            });
        }

        /**
         * Assert that the date node is after a date.
         * @param expected a local date
         */
        public void after(@NonNull LocalDate expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.after(expected);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.after(expected);
                }
            });
        }

        /**
         * Assert that the date node is the same or before a date.
         * @param expected a local date
         */
        public void sameOrBefore(@NonNull LocalDate expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.sameOrBefore(expected);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.sameOrBefore(expected);
                }
            });
        }

        /**
         * Assert that the date node is the same or after a date.
         * @param expected a local date
         */
        public void sameOrAfter(@NonNull LocalDate expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.sameOrAfter(expected);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.sameOrAfter(expected);
                }
            });
        }

        /**
         * Assert that the date node is between dates.
         * @param start lower local date, inclusive
         * @param end upper local date, inclusive
         */
        public void between(@NonNull LocalDate start, @NonNull LocalDate end) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.between(start,end);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.between(start,end);
                }
            });
        }

        /**
         * Assert that the date node is within a specified number of days from today.
         * @param days number of days
         */
        public void withinDays(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.withinDays(days);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.withinDays(days);
                }
            });
        }

        /**
         * Assert that the date node is within a specified number of past days from today.
         * @param days number of days
         */
        public void withinPastDays(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.withinPastDays(days);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.withinPastDays(days);
                }
            });
        }

        /**
         * Assert that the date node is more than a specified number of days in the future.
         * @param days number of days
         */
        public void moreThanDaysInFuture(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.moreThanDaysInFuture(days);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.moreThanDaysInFuture(days);
                }
            });
        }

        /**
         * Assert that the date node is more than a specified number of days in the past.
         * @param days number of days
         */
        public void moreThanDaysInPast(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", actualDate()).to.not.be.moreThanDaysInPast(days);
                } else {
                    assertor.expect("JsonNode Date", actualDate()).to.be.moreThanDaysInPast(days);
                }
            });
        }

        /**
         * Ensure that the object is of a date type, or throw error.
         * @return a date value
         */
        private LocalDate actualDate() {
            if (actual instanceof String && definedFormat != null) {
                try {
                    return LocalDate.parse(String.valueOf(actual), definedFormat);
                } catch (DateTimeParseException e) {
                    throw new AssertionError("Expected node does not match the defined date format.");
                }
            } else if (actual instanceof String) {
                for (DateTimeFormatter formatter : DATE_FORMATTERS) {
                    try {
                        return LocalDate.parse(String.valueOf(actual), formatter);
                    } catch (DateTimeParseException e) {
                        // Do nothing.
                    }
                }
            }
            throw new AssertionError("Node is not in recognized date format.");
        }

        /**
         * Check if the tested object is a of a date type.
         * @param tested tested object
         * @return true if the object is a date
         */
        private static boolean isDate(@NonNull Object tested) {
            if (tested instanceof String) {
                for (DateTimeFormatter formatter : DATE_FORMATTERS) {
                    try {
                        LocalDate.parse(String.valueOf(tested), formatter);
                        return true;
                    } catch (DateTimeParseException e) {
                        // Do nothing.
                    }
                }
            }
            return false;
        }

        /**
         * Check if the tested object is a of a date type based on the provided format.
         * @param tested tested object
         * @param formatter formatter
         * @return true if the object is a date
         */
        private static boolean isDate(@NonNull Object tested, DateTimeFormatter formatter) {
            if (tested instanceof String) {
                try {
                    LocalDate.parse(String.valueOf(tested), formatter);
                    return true;
                } catch (DateTimeParseException e) {
                    // Do nothing.
                }
            }
            return false;
        }
    }

    /**
     * Inner class to handle date/time node assertions.
     */
    public class JsonDateTimeAssertions {
        private static final DateTimeFormatter[] DATE_TIME_FORMATTERS = new DateTimeFormatter[] {
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"),
                DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        };

        private final @Nullable Object actual;
        private final @Nullable DateTimeFormatter definedFormat;
        private final JsonDateTimeAssertor assertor = new JsonDateTimeAssertor();

        private JsonDateTimeAssertions(@Nullable Object actual) {
            this(actual, null);
        }

        private JsonDateTimeAssertions(@Nullable Object actual, @Nullable DateTimeFormatter definedFormat) {
            this.actual = actual;
            this.definedFormat = definedFormat;
        }

        /**
         * Specify a date time format to use.
         * @param definedFormat DateTimeFormatter to be used
         * @return itself
         */
        public JsonDateTimeAssertions with(DateTimeFormatter definedFormat) {
            return new JsonDateTimeAssertions(this.actual, definedFormat);
        }

        /**
         * Assert that the date node is the same date as a date.
         * @param date a SQL date value
         */
        public void sameDateAs(@NonNull Date date) {
            this.sameDateAs(date.toLocalDate());
        }

        /**
         * Assert that the date node is the same date as a date time.
         * @param localDateTime a local date time
         */
        public void sameDateAs(@NonNull LocalDateTime localDateTime) {
            this.sameDateAs(localDateTime.toLocalDate());
        }

        /**
         * Assert that the date node is the same as a date time.
         * @param expected a local date time
         */
        public void of(@NonNull LocalDateTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Date", this.actualDateTime()).to.not.be(expected);
                } else {
                    assertor.expect("JsonNode Date", this.actualDateTime()).to.be(expected);
                }
            });
        }

        /**
         * Assert that the date node is the current date and time.
         */
        public void today() {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.today();
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.today();
                }
            });
        }

        /**
         * Assert that the date node is of a specified year.
         * @param year a year
         */
        public void year(int year) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.year(year);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.year(year);
                }
            });
        }

        /**
         * Assert that the date node is the same date as a date.
         * @param expected a local date
         */
        public void sameDateAs(@NonNull LocalDate expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.sameDateAs(expected);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.sameDateAs(expected);
                }
            });
        }

        /**
         * Assert that the date node is before a date time.
         * @param expected a local date time
         */
        public void before(@NonNull LocalDateTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.before(expected);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.before(expected);
                }
            });
        }

        /**
         * Assert that the date node is after a date time.
         * @param expected a local date time
         */
        public void after(@NonNull LocalDateTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.after(expected);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.after(expected);
                }
            });
        }

        /**
         * Assert that the date node is the same or before a date time.
         * @param expected a local date time
         */
        public void sameOrBefore(@NonNull LocalDateTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.sameOrBefore(expected);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.sameOrBefore(expected);
                }
            });
        }

        /**
         * Assert that the date node is the same or after a date time.
         * @param expected a local date time
         */
        public void sameOrAfter(@NonNull LocalDateTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.sameOrAfter(expected);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.sameOrAfter(expected);
                }
            });
        }

        /**
         * Assert that the date node is between date times.
         * @param start lower local date time, inclusive
         * @param end upper local date time, inclusive
         */
        public void between(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonData DateTime", this.actualDateTime()).to.not.be.between(start, end);
                } else {
                    assertor.expect("JsonData DateTime", this.actualDateTime()).to.be.between(start, end);
                }
            });
        }

        /**
         * Assert that the date node is within a specified number of days from now.
         * @param days number of days
         */
        public void withinDays(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.withinDays(days);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.withinDays(days);
                }
            });
        }

        /**
         * Assert that the date node is within a specified number of past days from now.
         * @param days number of days
         */
        public void withinPastDays(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.withinPastDays(days);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.withinPastDays(days);
                }
            });
        }

        /**
         * Assert that the date node is more than a specified number of days in the future.
         * @param days number of days
         */
        public void moreThanDaysInFuture(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.moreThanDaysInFuture(days);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.moreThanDaysInFuture(days);
                }
            });
        }

        /**
         * Assert that the date node is more than a specified number of days in the past.
         * @param days number of days
         */
        public void moreThanDaysInPast(int days) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.moreThanDaysInPast(days);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.moreThanDaysInPast(days);
                }
            });
        }

        /**
         * Assert that the date node is within a specified number of hours from now.
         * @param hours number of hours
         */
        public void withinHours(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.withinHours(hours);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.withinHours(hours);
                }
            });
        }

        /**
         * Assert that the date node is within a specified number of past hours from now.
         * @param hours number of hours
         */
        public void withinPastHours(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.withinPastHours(hours);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.withinPastHours(hours);
                }
            });
        }

        /**
         * Assert that the date node is more than a specified number of hours in the future.
         * @param hours number of hours
         */
        public void moreThanHoursInFuture(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.moreThanHoursInFuture(hours);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.moreThanHoursInFuture(hours);
                }
            });
        }

        /**
         * Assert that the date node is more than a specified number of hours in the past.
         * @param hours number of hours
         */
        public void moreThanHoursInPast(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.not.be.moreThanHoursInPast(hours);
                } else {
                    assertor.expect("JsonNode DateTime", this.actualDateTime()).to.be.moreThanHoursInPast(hours);
                }
            });
        }

        /**
         * Ensure that the object is of a date/time type, or throw error.
         * @return a date/time value
         */
        private LocalDateTime actualDateTime() {
            if (actual instanceof String && definedFormat != null) {
                final String stringValue = String.valueOf(actual);
                try {
                    return LocalDateTime.parse(stringValue, definedFormat);
                } catch (DateTimeParseException e) {
                    throw new AssertionError("Node does not match the defined date-time format.");
                }
            } else if (actual instanceof String) {
                final String stringValue = String.valueOf(actual);
                for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
                    try {
                        // Using parseBest to handle both Local and Offset/Zoned times
                        final TemporalAccessor accessor = formatter.parseBest(stringValue, ZonedDateTime::from, OffsetDateTime::from, LocalDateTime::from);

                        if (accessor instanceof LocalDateTime localDateTime) {
                            return localDateTime;
                        } else if (accessor instanceof OffsetDateTime offsetDateTime) {
                            return offsetDateTime.toLocalDateTime();
                        } else if (accessor instanceof ZonedDateTime zonedDateTime) {
                            return zonedDateTime.toLocalDateTime();
                        }
                    } catch (DateTimeParseException e) {
                        // Continue to next formatter
                    }
                }
            }
            throw new AssertionError("Node is not in a recognized date-time format.");
        }
        /**
         * Check if the provided object is a string representing a valid date time.
         * <p>
         * This method attempts to parse the string against a predefined list of
         * {@code DATE_TIME_FORMATTERS}.
         *
         * @param tested the object to check
         * @return {@code true} if the object is a string and matches any supported date time format
         */
        private static boolean isDateTime(@NonNull Object tested) {
            if (tested instanceof String) {
                for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
                    try {
                        // Using parseBest to handle both Local and Offset/Zoned times
                        formatter.withLocale(Locale.getDefault()).parseBest(String.valueOf(tested), ZonedDateTime::from, OffsetDateTime::from, LocalDateTime::from);
                        return true;
                    } catch (DateTimeParseException e) {
                        // Do nothing
                    }
                }
            }
            return false;
        }

        /**
         * Check if the provided object is a string matching a specific date time format.
         *
         * @param tested the object to check
         * @param formatter the specific {@link DateTimeFormatter} to use for validation
         * @return {@code true} if the object is a string and matches the specified format
         */
        private static boolean isDateTime(@NonNull Object tested, DateTimeFormatter formatter) {
            if (tested instanceof String) {
                try {
                    formatter.parse(String.valueOf(tested));
                    return true;
                } catch (DateTimeParseException e) {
                    // Do nothing
                }
            }
            return false;
        }

    }

    /**
     * Inner class to handle time node assertions.
     */
    public class JsonTimeAssertions {
        private static final DateTimeFormatter[] TIME_FORMATTERS = new DateTimeFormatter[] {
                DateTimeFormatter.ISO_LOCAL_TIME,      // 14:30:15
                DateTimeFormatter.ISO_TIME,            // 14:30:15+01:00
                DateTimeFormatter.ISO_OFFSET_TIME,     // 14:30:15+01:00
                DateTimeFormatter.ofPattern("HH:mm:ss"),
                DateTimeFormatter.ofPattern("HH:mm"),
                DateTimeFormatter.ofPattern("h:mm a"),      // 2:30 PM
                DateTimeFormatter.ofPattern("h:mm:ss a"),   // 2:30:15 PM
                DateTimeFormatter.ofPattern("H:mm:ss"),     // 14:30:15
                DateTimeFormatter.ofPattern("H:mm")         // 14:30
        };

        private final @Nullable Object actual;
        private final @Nullable DateTimeFormatter definedFormat;
        private final JsonTimeAssertor assertor = new JsonTimeAssertor();

        private JsonTimeAssertions(@Nullable Object actual) {
            this(actual, null);
        }

        private JsonTimeAssertions(@Nullable Object actual, @Nullable DateTimeFormatter definedFormat) {
            this.actual = actual;
            this.definedFormat = definedFormat;
        }

        /**
         * Specify a date time format to use.
         * @param definedFormat DateTimeFormatter to be used
         * @return itself
         */
        public JsonTimeAssertions with(DateTimeFormatter definedFormat) {
            return new JsonTimeAssertions(this.actual, definedFormat);
        }
        /**
         * Assert that the time node is the same as a specified time.
         * @param expected a local time
         */
        public void of(@NonNull LocalTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be(expected);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be(expected);
                }
            });
        }

        /**
         * Assert that the time node is before a specified time.
         * @param expected a local time
         */
        public void before(@NonNull LocalTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.before(expected);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.before(expected);
                }
            });
        }

        /**
         * Assert that the time node is after a specified time.
         * @param expected a local time
         */
        public void after(@NonNull LocalTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.after(expected);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.after(expected);
                }
            });
        }

        /**
         * Assert that the time node is the same or before a specified time.
         * @param expected a local time
         */
        public void sameOrBefore(@NonNull LocalTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.sameOrBefore(expected);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.sameOrBefore(expected);
                }
            });
        }

        /**
         * Assert that the time node is the same or after a specified time.
         * @param expected a local time
         */
        public void sameOrAfter(@NonNull LocalTime expected) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.sameOrAfter(expected);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.sameOrAfter(expected);
                }
            });
        }

        /**
         * Assert that the time node is between specified times.
         * @param start lower local time, inclusive
         * @param end upper local time, inclusive
         */
        public void between(@NonNull LocalTime start, @NonNull LocalTime end) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.between(start, end);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.between(start, end);
                }
            });
        }

        /**
         * Assert that the time node is within a specified number of hours from now.
         * @param hours number of hours
         */
        public void withinHours(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.withinHours(hours);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.withinHours(hours);
                }
            });
        }

        /**
         * Assert that the time node is within a specified number of past hours from now.
         * @param hours number of hours
         */
        public void withinPastHours(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.withinPastHours(hours);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.withinPastHours(hours);
                }
            });
        }

        /**
         * Assert that the time node is more than a specified number of hours in the future.
         * @param hours number of hours
         */
        public void moreThanHoursInFuture(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.moreThanHoursInFuture(hours);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.moreThanHoursInFuture(hours);
                }
            });
        }

        /**
         * Assert that the time node is more than a specified number of hours in the past.
         * @param hours number of hours
         */
        public void moreThanHoursInPast(int hours) {
            assertCondition(() -> {
                if (negated) {
                    assertor.expect("JsonNode Time", this.actualTime()).to.not.be.moreThanHoursInPast(hours);
                } else {
                    assertor.expect("JsonNode Time", this.actualTime()).to.be.moreThanHoursInPast(hours);
                }
            });
        }


        /**
         * Ensure that the object is of a time type, or throw error.
         * @return a time value
         */
        private LocalTime actualTime() {
            if (actual instanceof String && definedFormat != null) {
                final String stringValue = String.valueOf(actual);
                try {
                    return LocalTime.parse(stringValue, definedFormat);
                } catch (DateTimeParseException e) {
                    throw new AssertionError("Node does not match the defined time format.");
                }
            } else if (actual instanceof String) {
                final String stringValue = String.valueOf(actual);
                for (DateTimeFormatter formatter : TIME_FORMATTERS) {
                    try {
                        // Handles local time and offset time (stripping the offset)
                        final TemporalAccessor accessor = formatter.parseBest(stringValue, OffsetTime::from, LocalTime::from);

                        if (accessor instanceof LocalTime) {
                            return (LocalTime) accessor;
                        } else if (accessor instanceof OffsetTime) {
                            return ((OffsetTime) accessor).toLocalTime();
                        }

                    } catch (DateTimeParseException e) {
                        // Continue to next formatter
                    }
                }
            }
            throw new AssertionError("Node is not in a recognized time format.");
        }

        /**
         * Check if the provided object is a string representing a valid time.
         * <p>
         * This method attempts to parse the string against a predefined list of
         * {@code TIME_FORMATTERS}.
         *
         * @param tested the object to check
         * @return {@code true} if the object is a string and matches any supported time format
         */
        private static boolean isTime(@NonNull Object tested) {
            if (tested instanceof String) {
                for (DateTimeFormatter formatter : TIME_FORMATTERS) {
                    try {
                        formatter.withLocale(Locale.getDefault()).parseBest(String.valueOf(tested), OffsetTime::from, LocalTime::from);
                        return true;
                    } catch (DateTimeParseException e) {
                        // Do nothing
                    }
                }
            }
            return false;
        }

        /**
         * Check if the provided object is a string matching a specific time format.
         *
         * @param tested the object to check
         * @param formatter the specific {@link DateTimeFormatter} to use for validation
         * @return {@code true} if the object is a string and matches the specified format
         */
        private static boolean isTime(@NonNull Object tested, DateTimeFormatter formatter) {
            if (tested instanceof String) {
                try {
                    formatter.parse(String.valueOf(tested));
                    return true;
                } catch (DateTimeParseException e) {
                    // Do nothing
                }
            }
            return false;
        }

    }

    /*
        Expose the assertors of types.
     */

    private class JsonStringAssertor implements StringAssertor {
    }

    private class JsonNumberAssertor implements NumberAssertor {
    }

    private class JsonBooleanAssertor implements BooleanAssertor {
    }

    private class JsonDateAssertor implements DateAssertor {
    }

    private class JsonDateTimeAssertor implements DateTimeAssertor {
    }

    private class JsonTimeAssertor implements TimeAssertor {
    }
}
