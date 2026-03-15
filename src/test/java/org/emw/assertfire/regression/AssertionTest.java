package org.emw.assertfire.regression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertfire.AssertFire;
import org.emw.assertfire.exception.AssertionGroupError;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssertionTest implements AssertFire {
    @Test
    public void testString() {
        final String testString = "test";
        final String nullString = null;
        
        expect("Test 1", testString).to.startWith("te");
        expect("Test 2", testString).to.caseInsensitively.startWith("TES");
        expect("Test 3", testString).to.not.startWith("es");
        expect("Test 4", testString).to.caseInsensitively.not.startWith("ES");

        expect("Test 5", testString).to.endWith("st");
        expect("Test 6", testString).to.caseInsensitively.endWith("ST");
        expect("Test 7", testString).to.not.endWith("es");
        expect("Test 8", testString).to.caseInsensitively.not.endWith("ES");

        expect("Test 9", testString).to.be(testString);
        expect("Test 10", testString).to.caseInsensitively.be(testString);
        expect("Test 11", testString).to.not.be("es");
        expect("Test 12", testString).to.caseInsensitively.not.be("ES");

        expect("Test 13", testString).to.contain("es");
        expect("Test 14", testString).to.caseInsensitively.contain("ES");
        expect("Test 15", testString).to.not.contain("estt");
        expect("Test 16", testString).to.caseInsensitively.not.contain("ESTT");

        expect("Test 17", testString).to.match("\\w+");
        expect("Test 18", testString).to.not.match("\\d+");

        expect("Test 19", testString).to.be.oneOf("test", "test2", "test3");
        expect("Test 20", testString).to.caseInsensitively.be.oneOf("TEST", "TEST2", "TEST3");
        expect("Test 21", testString).to.not.be.oneOf("TEST", "TEST2", "TEST3");
        expect("Test 22", testString).to.caseInsensitively.not.be.oneOf("TEST1", "TEST2", "TEST3");

        expect("Test 23", "").to.be.empty();
        expect("Test 24", testString).to.not.be.empty();
        expect("Test 25", nullString).to.be.nullValue();
        expect("Test 26", "null").to.not.be.nullValue();

        expectError(() -> expect("Test 27", testString).to.startWith("es"), "Expected actual value('test') of 'Test 27' to start with 'es'.");

        expectError(() -> expect("Test 28", testString).to.caseInsensitively.startWith("abc"), "Ignoring cases, expected actual value('test') of 'Test 28' to start with 'abc'.");
        expectError(() -> expect("Test 29", testString).to.not.startWith("te"), "Expected actual value('test') of 'Test 29' not to start with 'te'.");
        expectError(() -> expect("Test 30", testString).to.caseInsensitively.not.startWith("TE"), "Ignoring cases, expected actual value('test') of 'Test 30' not to start with 'TE'.");

        expectError(() -> expect("Test 31", testString).to.endWith("te"), "Expected actual value('test') of 'Test 31' to end with 'te'.");
        expectError(() -> expect("Test 32", testString).to.caseInsensitively.endWith("ABC"), "Ignoring cases, expected actual value('test') of 'Test 32' to end with 'ABC'.");
        expectError(() -> expect("Test 33", testString).to.not.endWith("st"), "Expected actual value('test') of 'Test 33' not to end with 'st'.");
        expectError(() -> expect("Test 34", testString).to.caseInsensitively.not.endWith("ST"), "Ignoring cases, expected actual value('test') of 'Test 34' not to end with 'ST'.");

        expectError(() -> expect("Test 35", testString).to.be("TEST"), "Expected actual value('test') of 'Test 35' to equal 'TEST'.");
        expectError(() -> expect("Test 36", testString).to.caseInsensitively.be("abc"), "Ignoring cases, expected actual value('test') of 'Test 36' to equal 'abc'.");
        expectError(() -> expect("Test 37", testString).to.not.be(testString), "Expected actual value('test') of 'Test 37' not to equal 'test'.");
        expectError(() -> expect("Test 38", testString).to.caseInsensitively.not.be("TEST"), "Ignoring cases, expected actual value('test') of 'Test 38' not to equal 'TEST'.");

        expectError(() -> expect("Test 39", testString).to.contain("abc"), "Expected actual value('test') of 'Test 39' to contain 'abc'.");
        expectError(() -> expect("Test 40", testString).to.caseInsensitively.contain("ABC"), "Ignoring cases, expected actual value('test') of 'Test 40' to contain 'ABC'.");
        expectError(() -> expect("Test 41", testString).to.not.contain("es"), "Expected actual value('test') of 'Test 41' not to contain 'es'.");
        expectError(() -> expect("Test 42", testString).to.caseInsensitively.not.contain("ES"), "Ignoring cases, expected actual value('test') of 'Test 42' not to contain 'ES'.");

        expectError(() -> expect("Test 43", testString).to.match("\\d+"), "Expected actual value('test') of 'Test 43' to match the pattern '\\d+'.");
        expectError(() -> expect("Test 44", testString).to.not.match(".*st"), "Expected actual value('test') of 'Test 44' not to match the pattern '.*st'.");

        expectError(() -> expect("Test 45", testString).to.be.oneOf("abc", "123"), "Expected actual value('test') of 'Test 45' to be one of 'abc', '123'.");
        expectError(() -> expect("Test 46", testString).to.caseInsensitively.be.oneOf("ABC", "123"), "Ignoring cases, expected actual value('test') of 'Test 46' to be one of 'ABC', '123'.");
        expectError(() -> expect("Test 47", testString).to.not.be.oneOf("test", "other"), "Expected actual value('test') of 'Test 47' not to be one of 'test', 'other'.");
        expectError(() -> expect("Test 48", testString).to.caseInsensitively.not.be.oneOf("TEST", "other"), "Ignoring cases, expected actual value('test') of 'Test 48' not to be one of 'TEST', 'other'.");

        expectError(() -> expect("Test 49", testString).to.be.empty(), "Expected actual value('test') of 'Test 49' to be empty.");
        expectError(() -> expect("Test 50", "").to.not.be.empty(),"Expected actual value('') of 'Test 50' not to be empty.");

        expectError(() -> expect("Test 51", testString).to.be.nullValue(), "Expected actual value('test') of 'Test 51' to be null.");
        expectError(() -> expect("Test 52", nullString).to.not.be.nullValue(), "Expected actual value('null') of 'Test 52' not to be null.");

        expectError(() -> expect("Test 53", "   ").to.be.empty(), "Expected actual value('   ') of 'Test 53' to be empty.");
    }

    @Test
    public void testCollection() {
        final String[] emptyArray = new String[0];
        final String[] nullArray = null;

        expect("Test 1", new String[] { "test1", "test2" }).to.allMatch("test1", "test2");
        expect("Test 2", new String[] { "test1", "test2" }).to.inAnyOrder.allMatch("test2", "test1");
        expect("Test 3", new String[] { "test1", "test2" }).to.contain("test2", "test1");
        expect("Test 4", new String[] { "test1", "test2" }).to.caseInsensitively.allMatch("Test1", "test2");
        expect("Test 5", new String[] { "test1", "test2" }).to.caseInsensitively.inAnyOrder.allMatch("Test2", "test1");
        expect("Test 6", new String[] { "test1", "test2" }).to.caseInsensitively.contain("Test2");
        expect("Test 7", new String[] { "test1", "test2" }).to.be.sizeOf(2);
        expect("Test 8", emptyArray).to.be.empty();
        expect("Test 9", nullArray).to.be.nullValue();
        expect("Test 10", new String[] { "test1", "test2" }).to.not.be.empty();
        expect("Test 11", emptyArray).to.not.be.nullValue();

        expectError(() -> expect("Test 12", new String[] { "test1", "test2" }).to.allMatch("Test1", "test2"), "Expected actual value('test1', 'test2') of 'Test 12' to all match 'Test1', 'test2'.");
        expectError(() -> expect("Test 13", new String[] { "test1", "test2" }).to.not.allMatch("test1", "test2"), "Expected actual value('test1', 'test2') of 'Test 13' not to all match 'test1', 'test2'.");
        expectError(() -> expect("Test 14", new String[] { "test1", "test2" }).to.inAnyOrder.allMatch("Test2", "test1"), "Expected actual value('test1', 'test2') of 'Test 14' to all match (in any order) as 'Test2', 'test1'.");
        expectError(() -> expect("Test 15", new String[] { "test1", "test2" }).to.contain("Test2"), "Expected actual value('test1', 'test2') of 'Test 15' to have 'Test2'.");
        expectError(() -> expect("Test 16", new String[] { "test1", "test2" }).to.contain("test1", "test2", "test3"), "Expected actual value('test1', 'test2') of 'Test 16' to have 'test1', 'test2', 'test3'.");
        expectError(() -> expect("Test 17", new String[] { "test1", "test2" }).to.be.sizeOf(1), "Expected actual value('test1', 'test2') of 'Test 17' to have size of 1, but was 2.");
        expectError(() -> expect("Test 18", new String[] { "test1", "test2" }).to.be.empty(), "Expected actual value('test1', 'test2') of 'Test 18' to be empty.");
        expectError(() -> expect("Test 19", new String[] { "test1", "test2" }).to.be.nullValue(), "Expected actual value('test1', 'test2') of 'Test 19' to be null.");
    }

    @Test
    public void testBoolean() {
        final Boolean nullBoolean = null;

        expect("Test 1", true).to.be.trueValue();
        expect("Test 2", false).to.not.be.trueValue();
        expect("Test 3", nullBoolean).to.be.nullValue();
        expect("Test 4", Boolean.valueOf(false)).to.not.be.nullValue();

        expectError(() -> expect("Test 5", false).to.be.trueValue(), "Expected actual value('false') of 'Test 5' to be true.");
        expectError(() -> expect("Test 6", true).to.not.be.trueValue(), "Expected actual value('true') of 'Test 6' not to be true.");
    }

    @Test
    public void testDate() {
        final LocalDate testDate = LocalDate.of(2020, 1, 1);
        final LocalDate nullDate = null;

        expect("Test 1", testDate).to.be(LocalDate.of(2020, 1, 1));
        expect("Test 2", testDate).to.not.be(LocalDate.of(2020, 1, 2));
        expect("Test 3", testDate).to.be.sameDateAs(LocalDateTime.of(2020, 1, 1, 2, 3, 45));
        expect("Test 4", testDate).to.be.after(LocalDate.of(2019, 12, 31));
        expect("Test 5", testDate).to.be.before(LocalDate.of(2020, 1, 2));
        expect("Test 6", testDate).to.be.sameOrBefore(LocalDate.of(2020, 1, 1));
        expect("Test 7", testDate).to.be.sameOrBefore(LocalDate.of(2020, 1, 2));
        expect("Test 8", testDate).to.be.sameOrAfter(LocalDate.of(2020, 1, 1));
        expect("Test 9", testDate).to.be.sameOrAfter(LocalDate.of(2019, 12, 31));
        expect("Test 10", testDate).to.be.between(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 2));
        expect("Test 11", nullDate).to.be.nullValue();
        expect("Test 12", LocalDate.now()).to.not.be.nullValue();
        expect("Test 13", LocalDate.now().plusDays(3)).to.be.withinDays(3);
        expect("Test 14", LocalDate.now().plusDays(4)).to.not.be.withinDays(3);
        expect("Test 15", LocalDate.now().minusDays(1)).to.not.be.withinDays(3);
        expect("Test 16", LocalDate.now().minusDays(3)).to.be.withinPastDays(3);
        expect("Test 17", LocalDate.now().minusDays(4)).to.not.be.withinPastDays(3);
        expect("Test 18", LocalDate.now().plusDays(1)).to.not.be.withinPastDays(3);
        expect("Test 19", LocalDate.now().plusDays(6)).to.be.moreThanDaysInFuture(3);
        expect("Test 20", LocalDate.now().plusDays(2)).to.not.be.moreThanDaysInFuture(3);
        expect("Test 21", LocalDate.now().minusDays(1)).to.not.be.moreThanDaysInFuture(3);
        expect("Test 22", LocalDate.now().minusDays(6)).to.be.moreThanDaysInPast(3);
        expect("Test 23", LocalDate.now().minusDays(3)).to.not.be.moreThanDaysInPast(3);
        expect("Test 24", LocalDate.now().plusDays(1)).to.not.be.moreThanDaysInPast(3);
        expect("Test 25", LocalDate.now()).to.be.today();
        expect("Test 26", LocalDate.now().plusDays(3)).to.not.be.today();

        expectError(() -> expect("Test 27", testDate).to.be(LocalDate.of(2020, 1, 2)), "Expected actual value('2020-01-01') of 'Test 27' to be '2020-01-02'.");
        expectError(() -> expect("Test 28", testDate).to.not.be(LocalDate.of(2020, 1, 1)), "Expected actual value('2020-01-01') of 'Test 28' not to be '2020-01-01'.");
        expectError(() -> expect("Test 29", testDate).to.be.sameDateAs(LocalDateTime.of(2020, 1, 2, 0, 0)), "Expected actual value('2020-01-01') of 'Test 29' to be the same date as '2020-01-02 00:00:00.000'.");
        expectError(() -> expect("Test 30", testDate).to.be.after(LocalDate.of(2020, 1, 1)), "Expected actual value('2020-01-01') of 'Test 30' to be after '2020-01-01'.");
        expectError(() -> expect("Test 31", testDate).to.be.after(LocalDate.of(2020, 1, 2)), "Expected actual value('2020-01-01') of 'Test 31' to be after '2020-01-02'.");
        expectError(() -> expect("Test 32", testDate).to.be.before(LocalDate.of(2020, 1, 1)), "Expected actual value('2020-01-01') of 'Test 32' to be before '2020-01-01'.");
        expectError(() -> expect("Test 33", testDate).to.be.before(LocalDate.of(2019, 12, 31)), "Expected actual value('2020-01-01') of 'Test 33' to be before '2019-12-31'.");
        expectError(() -> expect("Test 34", testDate).to.be.sameOrBefore(LocalDate.of(2019, 12, 31)), "Expected actual value('2020-01-01') of 'Test 34' to be the same or before '2019-12-31'.");
        expectError(() -> expect("Test 35", testDate).to.be.sameOrAfter(LocalDate.of(2020, 1, 2)), "Expected actual value('2020-01-01') of 'Test 35' to be the same or after '2020-01-02'.");
        expectError(() -> expect("Test 36", LocalDate.of(2020, 1, 3)).to.be.between(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2)), "Expected actual value('2020-01-03') of 'Test 36' to be between '2020-01-01' and '2020-01-02'.");
        expectError(() -> expect("Test 37", LocalDate.of(2020, 1, 5)).to.be.between(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 1)), "Expected actual value('2020-01-05') of 'Test 37' to be between '2019-12-31' and '2020-01-01'.");
        expectError(() -> expect("Test 38", testDate).to.be.between(LocalDate.of(2020, 1, 2), LocalDate.of(2021, 1, 1)), "Expected actual value('2020-01-01') of 'Test 38' to be between '2020-01-02' and '2021-01-01'.");
        expectError(() -> expect("Test 39", LocalDate.now()).to.be.nullValue(), " to be null.");
        expectError(() -> expect("Test 40", nullDate).to.not.be.nullValue(), "Expected actual value('null') of 'Test 40' not to be null.");
        expectError(() -> expect("Test 41", LocalDate.now().plusDays(3)).to.not.be.withinDays(3), " not to be within 3 days from today.");
        expectError(() -> expect("Test 42", LocalDate.now().plusDays(4)).to.be.withinDays(3), " to be within 3 days from today.");
        expectError(() -> expect("Test 43", LocalDate.now().minusDays(1)).to.be.withinDays(3), " to be within 3 days from today.");
        expectError(() -> expect("Test 44", LocalDate.now().minusDays(3)).to.not.be.withinPastDays(3), " to be within past 3 days from today.");
        expectError(() -> expect("Test 45", LocalDate.now().minusDays(4)).to.be.withinPastDays(3), " to be within past 3 days from today.");
        expectError(() -> expect("Test 46", LocalDate.now().plusDays(1)).to.be.withinPastDays(3), " to be within past 3 days from today.");
        expectError(() -> expect("Test 47", LocalDate.now()).to.not.be.today(), " not to be today.");
        expectError(() -> expect("Test 48", LocalDate.now().plusDays(3)).to.be.today(), " to be today.");
        expectError(() -> expect("Test 49", LocalDate.now().plusDays(6)).to.not.be.moreThanDaysInFuture(3), " not to be more than 3 days in future");
        expectError(() -> expect("Test 50", LocalDate.now().plusDays(2)).to.be.moreThanDaysInFuture(3), " to be more than 3 days in future.");
        expectError(() -> expect("Test 51", LocalDate.now().minusDays(1)).to.be.moreThanDaysInFuture(3), " to be more than 3 days in future.");
        expectError(() -> expect("Test 52", LocalDate.now().minusDays(6)).to.not.be.moreThanDaysInPast(3), " not to be more than 3 days in past.");
        expectError(() -> expect("Test 53", LocalDate.now().minusDays(3)).to.be.moreThanDaysInPast(3), " to be more than 3 days in past.");
        expectError(() -> expect("Test 54", LocalDate.now().plusDays(1)).to.be.moreThanDaysInPast(3), " to be more than 3 days in past.");
    }

    @Test
    public void testDateTime() {
        final LocalDateTime testDateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
        final LocalDateTime nullDateTime = null;

        expect("Test 1", testDateTime).to.be.sameDateAs(LocalDate.of(2020, 1, 1));
        expect("Test 2", testDateTime).to.be.sameDateAs(LocalDateTime.of(2020, 1, 1, 12, 12));
        expect("Test 3", testDateTime).to.not.be.sameDateAs(LocalDate.of(2020, 1, 2));
        expect("Test 4", testDateTime).to.be(LocalDateTime.of(2020, 1, 1, 0, 0));
        expect("Test 5", testDateTime).to.not.be(LocalDateTime.of(2020, 1, 1, 12, 12));
        expect("Test 6", testDateTime).to.be.after(LocalDateTime.of(2019, 12, 31, 23, 59));
        expect("Test 7", testDateTime).to.be.before(LocalDateTime.of(2020, 1, 2, 0, 0));
        expect("Test 8", testDateTime).to.be.sameOrBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
        expect("Test 9", testDateTime).to.be.sameOrBefore(LocalDateTime.of(2020, 1, 1, 1, 1));
        expect("Test 10", testDateTime).to.be.sameOrAfter(LocalDateTime.of(2020, 1, 1, 0, 0));
        expect("Test 11", testDateTime).to.be.sameOrAfter(LocalDateTime.of(2019, 12, 31, 23, 59));
        expect("Test 12", testDateTime).to.be.between(LocalDateTime.of(2019, 12, 31, 23, 59), LocalDateTime.of(2020, 1, 1, 0, 0, 1));
        expect("Test 13", nullDateTime).to.be.nullValue();
        expect("Test 14", LocalDateTime.now()).to.not.be.nullValue();
        expect("Test 13", LocalDateTime.now().plusDays(3)).to.be.withinDays(3);
        expect("Test 14", LocalDateTime.now().plusDays(4)).to.not.be.withinDays(3);
        expect("Test 15", LocalDateTime.now().minusDays(1)).to.not.be.withinDays(3);
        expect("Test 16", LocalDateTime.now().minusDays(3)).to.be.withinPastDays(3);
        expect("Test 17", LocalDateTime.now().minusDays(4)).to.not.be.withinPastDays(3);
        expect("Test 18", LocalDateTime.now().plusDays(1)).to.not.be.withinPastDays(3);
        expect("Test 19", LocalDateTime.now().plusHours(3)).to.be.withinHours(3);
        expect("Test 20", LocalDateTime.now().plusHours(4)).to.not.be.withinHours(3);
        expect("Test 21", LocalDateTime.now().minusHours(1)).to.not.be.withinHours(3);
        expect("Test 22", LocalDateTime.now().minusHours(3)).to.be.withinPastHours(4);
        expect("Test 23", LocalDateTime.now().minusHours(4)).to.not.be.withinPastHours(3);
        expect("Test 24", LocalDateTime.now().plusHours(1)).to.not.be.withinPastHours(3);
        expect("Test 25", LocalDateTime.now().plusDays(6)).to.be.moreThanDaysInFuture(3);
        expect("Test 26", LocalDateTime.now().plusDays(2)).to.not.be.moreThanDaysInFuture(3);
        expect("Test 27", LocalDateTime.now().minusDays(1)).to.not.be.moreThanDaysInFuture(3);
        expect("Test 28", LocalDateTime.now().minusDays(6)).to.be.moreThanDaysInPast(3);
        expect("Test 29", LocalDateTime.now().minusDays(1)).to.not.be.moreThanDaysInPast(3);
        expect("Test 30", LocalDateTime.now().plusDays(1)).to.not.be.moreThanDaysInPast(3);
        expect("Test 31", LocalDateTime.now().plusHours(6)).to.be.moreThanHoursInFuture(3);
        expect("Test 32", LocalDateTime.now().plusHours(2)).to.not.be.moreThanHoursInFuture(3);
        expect("Test 33", LocalDateTime.now().minusHours(1)).to.not.be.moreThanHoursInFuture(3);
        expect("Test 34", LocalDateTime.now().minusHours(6)).to.be.moreThanHoursInPast(3);
        expect("Test 35", LocalDateTime.now().minusHours(1)).to.not.be.moreThanHoursInPast(3);
        expect("Test 36", LocalDateTime.now().plusHours(1)).to.not.be.moreThanHoursInPast(3);
        expect("Test 37", LocalDateTime.now()).to.be.today();
        expect("Test 38", LocalDateTime.now().plusDays(3)).to.not.be.today();

        expectError(() -> expect("Test 39", testDateTime).to.be.sameDateAs(LocalDate.of(2020, 1, 2)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 39' to be the same date as '2020-01-02'.");
        expectError(() -> expect("Test 40", testDateTime).to.not.be.sameDateAs(LocalDate.of(2020, 1, 1)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 40' not to be the same date as '2020-01-01'.");
        expectError(() -> expect("Test 41", testDateTime).to.be.after(LocalDateTime.of(2020, 1, 1, 0, 0)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 41' to be after '2020-01-01 00:00:00.000'.");
        expectError(() -> expect("Test 42", testDateTime).to.be.after(LocalDateTime.of(2020, 1, 2, 1, 1)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 42' to be after '2020-01-02 01:01:00.000'.");
        expectError(() -> expect("Test 43", testDateTime).to.be.before(LocalDateTime.of(2020, 1, 1, 0, 0)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 43' to be before '2020-01-01 00:00:00.000'.");
        expectError(() -> expect("Test 44", testDateTime).to.be.before(LocalDateTime.of(2019, 12, 31, 23, 59)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 44' to be before '2019-12-31 23:59:00.000'.");
        expectError(() -> expect("Test 45", testDateTime).to.be.sameOrBefore(LocalDateTime.of(2019, 12, 31, 23, 59)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 45' to be the same or before '2019-12-31 23:59:00.000'.");
        expectError(() -> expect("Test 46", testDateTime).to.be.sameOrAfter(LocalDateTime.of(2020, 1, 1, 0, 1)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 46' to be the same or after '2020-01-01 00:01:00.000'.");
        expectError(() -> expect("Test 47", LocalDateTime.of(2020, 1, 3, 0, 0)).to.be.between(LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 2, 0, 0)), "Expected actual value('2020-01-03 00:00:00.000') of 'Test 47' to be between '2020-01-01 00:00:00.000' and '2020-01-02 00:00:00.000'.");
        expectError(() -> expect("Test 48", LocalDateTime.of(2020, 1, 5, 0, 0)).to.be.between(LocalDateTime.of(2019, 12, 31, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0)), "Expected actual value('2020-01-05 00:00:00.000') of 'Test 48' to be between '2019-12-31 00:00:00.000' and '2020-01-01 00:00:00.000'.");
        expectError(() -> expect("Test 49", testDateTime).to.be.between(LocalDateTime.of(2020, 1, 2, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 49' to be between '2020-01-02 00:00:00.000' and '2021-01-01 00:00:00.000'.");
        expectError(() -> expect("Test 50", LocalDateTime.of(5, 5, 5, 5, 5)).to.be.nullValue(), "Expected actual value('0005-05-05 05:05:00.000') of 'Test 50' to be null.");
        expectError(() -> expect("Test 51", nullDateTime).to.not.be.nullValue(), "Expected actual value('null') of 'Test 51' not to be null.");
        expectError(() -> expect("Test 52", testDateTime).to.be(LocalDateTime.of(2020, 1, 1, 0, 1)), "Expected actual value('2020-01-01 00:00:00.000') of 'Test 52' to be '2020-01-01'.");

        expectError(() -> expect("Test 53", LocalDateTime.now().plusDays(3)).to.not.be.withinDays(3), " not to be within 3 days from today.");
        expectError(() -> expect("Test 54", LocalDateTime.now().plusDays(4)).to.be.withinDays(3), " to be within 3 days from today.");
        expectError(() -> expect("Test 55", LocalDateTime.now().minusDays(1)).to.be.withinDays(3), " to be within 3 days from today.");
        expectError(() -> expect("Test 56", LocalDateTime.now().minusDays(3)).to.not.be.withinPastDays(3), " to be within past 3 days from today.");
        expectError(() -> expect("Test 57", LocalDateTime.now().minusDays(4)).to.be.withinPastDays(3), " to be within past 3 days from today");
        expectError(() -> expect("Test 58", LocalDateTime.now().plusDays(1)).to.be.withinPastDays(3), " to be within past 3 days from today.");
        expectError(() -> expect("Test 59", LocalDateTime.now().plusHours(3)).to.not.be.withinHours(3), " to be within 3 hours from ");
        expectError(() -> expect("Test 60", LocalDateTime.now().plusHours(4)).to.be.withinHours(3), " to be within 3 hours from ");
        expectError(() -> expect("Test 61", LocalDateTime.now().minusHours(1)).to.be.withinHours(3), " to be within 3 hours from ");
        expectError(() -> expect("Test 62", LocalDateTime.now().minusHours(2)).to.not.be.withinPastHours(3), " to be within past 3 hours from ");
        expectError(() -> expect("Test 63", LocalDateTime.now().minusHours(4)).to.be.withinPastHours(3), " to be within past 3 hours from ");
        expectError(() -> expect("Test 64", LocalDateTime.now().plusHours(1)).to.be.withinPastHours(3), " to be within past 3 hours from ");
        expectError(() -> expect("Test 65", LocalDateTime.now().plusDays(6)).to.not.be.moreThanDaysInFuture(3), " to be more than 3 days in future.");
        expectError(() -> expect("Test 66", LocalDateTime.now().plusDays(2)).to.be.moreThanDaysInFuture(3), " to be more than 3 days in future.");
        expectError(() -> expect("Test 67", LocalDateTime.now().minusDays(1)).to.be.moreThanDaysInFuture(3), " to be more than 3 days in future.");
        expectError(() -> expect("Test 68", LocalDateTime.now().minusDays(6)).to.not.be.moreThanDaysInPast(3), " to be more than 3 days in past.");
        expectError(() -> expect("Test 69", LocalDateTime.now().minusDays(1)).to.be.moreThanDaysInPast(3), " to be more than 3 days in past.");
        expectError(() -> expect("Test 70", LocalDateTime.now().plusDays(1)).to.be.moreThanDaysInPast(3), " to be more than 3 days in past.");
        expectError(() -> expect("Test 71", LocalDateTime.now().plusHours(6)).to.not.be.moreThanHoursInFuture(3), " not to be more than 3 hours in future from ");
        expectError(() -> expect("Test 72", LocalDateTime.now().plusHours(2)).to.be.moreThanHoursInFuture(3), " to be more than 3 hours in future from ");
        expectError(() -> expect("Test 73", LocalDateTime.now().minusHours(1)).to.be.moreThanHoursInFuture(3), " to be more than 3 hours in future from ");
        expectError(() -> expect("Test 74", LocalDateTime.now().minusHours(6)).to.not.be.moreThanHoursInPast(3), " not to be more than 3 hours in past from ");
        expectError(() -> expect("Test 75", LocalDateTime.now().minusHours(1)).to.be.moreThanHoursInPast(3), " to be more than 3 hours in past from ");
        expectError(() -> expect("Test 76", LocalDateTime.now().plusHours(1)).to.be.moreThanHoursInPast(3), " to be more than 3 hours in past from ");
        expectError(() -> expect("Test 77", LocalDateTime.now()).to.not.be.today(), " not to be today.");
        expectError(() -> expect("Test 78", LocalDateTime.now().plusDays(3)).to.be.today(), " to be today.");
    }

    @Test
    public void testNumber() {
        final Double nullNumber = null;

        expect("Test 1", 10).to.be(10);
        expect("Test 2", 10.1).to.be(10.1f);
        expect("Test 3", 10.1).to.not.be(10);
        expect("Test 4", 10.1).to.be.moreThan(10);
        expect("Test 5", 10.1).to.be.lessThan(10.2f);
        expect("Test 6", 10).to.be.moreThanOrEqual(9.9f);
        expect("Test 7", 10).to.be.lessThanOrEqual(10.1f);
        expect("Test 8", 10).to.be.moreThanOrEqual(10.0f);
        expect("Test 9", 10).to.be.lessThanOrEqual(10.0);
        expect("Test 10", 10).to.be.between(9.9, 11);
        expect("Test 11", nullNumber).to.be.nullValue();
        expect("Test 12", Double.valueOf(1)).to.not.be.nullValue();

        expectError(() -> expect("Test 13", 10).to.be.between(10.1, 11), "Expected actual value('10') of 'Test 13' to be between '10.1' and '11.0'.");

        expectError(() -> expect("Test 14", 10).to.be(11), "Expected actual value('10') of 'Test 14' to equal '11'.");
        expectError(() -> expect("Test 15", 10.1).to.be(10.11), "Expected actual value('10.1') of 'Test 15' to equal '10.11'.");
        expectError(() -> expect("Test 16", 10).to.not.be(10.0), "Expected actual value('10') of 'Test 16' not to equal '10.0'.");

        expectError(() -> expect("Test 17", 10).to.be.moreThan(10), "Expected actual value('10') of 'Test 17' to be more than '10'.");
        expectError(() -> expect("Test 18", 10).to.be.moreThan(10.1), "Expected actual value('10') of 'Test 18' to be more than '10.1'.");
        expectError(() -> expect("Test 19", -5).to.be.moreThan(0), "Expected actual value('-5') of 'Test 19' to be more than '0'.");

        expectError(() -> expect("Test 20", 10).to.be.lessThan(10), "Expected actual value('10') of 'Test 20' to be less than '10'.");
        expectError(() -> expect("Test 21", 10.5).to.be.lessThan(10.4), "Expected actual value('10.5') of 'Test 21' to be less than '10.4'.");
        expectError(() -> expect("Test 22", 0).to.be.lessThan(-1), "Expected actual value('0') of 'Test 22' to be less than '-1'.");

        expectError(() -> expect("Test 23", 10).to.be.moreThanOrEqual(11), "Expected actual value('10') of 'Test 23' to be more than or equal '11'.");
        expectError(() -> expect("Test 24", 10.1).to.be.lessThanOrEqual(10.0), "Expected actual value('10.1') of 'Test 24' to be less than or equal '10.0'.");
        expectError(() -> expect("Test 25", -10).to.be.moreThanOrEqual(-9.9), "Expected actual value('-10') of 'Test 25' to be more than or equal '-9.9'.");

        expectError(() -> expect("Test 26", 11.1).to.be.between(10, 11), "Expected actual value('11.1') of 'Test 26' to be between '10.0' and '11.0'.");
        expectError(() -> expect("Test 27", 9.9).to.be.between(10, 11), "Expected actual value('9.9') of 'Test 27' to be between '10.0' and '11.0'.");
        expectError(() -> expect("Test 28", 5).to.be.between(6, 7), "Expected actual value('5') of 'Test 28' to be between '6.0' and '7.0'.");

        expectError(() -> expect("Test 27", 100L).to.be.lessThan(50), "Expected actual value('100') of 'Test 27' to be less than '50'.");
        expectError(() -> expect("Test 28", 0.01f).to.be.moreThan(0.1), "Expected actual value('0.01') of 'Test 28' to be more than '0.1'.");
        expectError(() -> expect("Test 29", nullNumber).to.be.moreThan(0.1), "Expected actual value('null') of 'Test 29' to be more than '0.1'.");
    }

    @Test
    public void testTime() {
        final LocalTime testTime = LocalTime.of(11, 0, 0);
        final LocalTime nullTime = null;

        expect("Test 1", testTime).to.be(LocalTime.of(11, 0, 0));
        expect("Test 2", testTime).to.not.be(LocalTime.of(12, 0, 0));
        expect("Test 3", testTime).to.be.after(LocalTime.of(10, 0, 0));
        expect("Test 4", testTime).to.not.be.after(LocalTime.of(12, 0, 0));
        expect("Test 5", testTime).to.be.before(LocalTime.of(12, 0, 0));
        expect("Test 6", testTime).to.not.be.before(LocalTime.of(10, 0, 0));
        expect("Test 7", testTime).to.be.between(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0));
        expect("Test 8", testTime).to.be.sameOrAfter(LocalTime.of(11, 0, 0));
        expect("Test 9", testTime).to.be.sameOrAfter(LocalTime.of(10, 0, 0));
        expect("Test 10", testTime).to.be.sameOrBefore(LocalTime.of(11, 0, 0));
        expect("Test 11", testTime).to.be.sameOrBefore(LocalTime.of(12, 0, 0));
        expect("Test 12", nullTime).to.be.nullValue();

        expectError(() -> expect("Test 13", testTime).to.not.be(LocalTime.of(11, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 13' not to be '11:00:00.000'.");
        expectError(() -> expect("Test 14", testTime).to.be(LocalTime.of(12, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 14' to be '12:00:00.000'.");
        expectError(() -> expect("Test 15", testTime).to.not.be.after(LocalTime.of(10, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 15' not to be after '10:00:00.000'.");
        expectError(() -> expect("Test 16", testTime).to.be.after(LocalTime.of(12, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 16' to be after '12:00:00.000'.");
        expectError(() -> expect("Test 17", testTime).to.not.be.before(LocalTime.of(12, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 17' not to be before '12:00:00.000'.");
        expectError(() -> expect("Test 18", testTime).to.be.before(LocalTime.of(10, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 18' to be before '10:00:00.000'.");
        expectError(() -> expect("Test 19", testTime).to.not.be.between(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 19' not to be between '10:00:00.000' and '12:00:00.000'.");
        expectError(() -> expect("Test 20", testTime).to.not.be.sameOrAfter(LocalTime.of(11, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 20' not to be the same or after '11:00:00.000'.");
        expectError(() -> expect("Test 21", testTime).to.not.be.sameOrAfter(LocalTime.of(10, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 21' not to be the same or after '10:00:00.000'.");
        expectError(() -> expect("Test 22", testTime).to.not.be.sameOrBefore(LocalTime.of(11, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 22' not to be the same or before '11:00:00.000'.");
        expectError(() -> expect("Test 23", testTime).to.not.be.sameOrBefore(LocalTime.of(12, 0, 0)), "Expected actual value('11:00:00.000') of 'Test 23' not to be the same or before '12:00:00.000'.");
        expectError(() -> expect("Test 24", nullTime).to.not.be.nullValue(), "Expected actual value('null') of 'Test 24' not to be null.");
    }

    @Test
    public void testGroup() {
        try {
            assertionGroup(g -> {
                final String testString = "test";

                g.expect("Test 1", testString).to.be("test1");
                g.expect("Test 2", testString).to.be("test2");
                g.expect("Test 3", 1).to.be(1);
                g.expect("Test 4", 1).to.be(0);
                g.assertWith(() -> Assert.assertEquals(1, 0));
            });
        } catch (AssertionGroupError ex) {
            expect(ex.getMessage()).to.contain("Expected actual value('test') of 'Test 1' to equal 'test1'.");
            expect(ex.getMessage()).to.contain("Expected actual value('test') of 'Test 2' to equal 'test2'.");
            expect(ex.getMessage()).to.contain("Expected actual value('1') of 'Test 4' to equal '0'.");
            expect(ex.getMessage()).to.contain("expected [0] but found [1]");
        }
    }

    private void expectError(AssertionErrorAction action) {
        this.expectError(action, "");
    }

    private void expectError(@NonNull AssertionErrorAction action, @NonNull String errorMessage) {
        try {
            action.expectAssertionError();
        } catch (AssertionError ex) {
            if (errorMessage.isEmpty()) {
                System.out.println("Error message = , \"" + ex.getMessage() + "\"");
            } else {
                if (ex.getMessage() == null || !ex.getMessage().contains(errorMessage)) {
                    throw new AssertionError("Unexpected AssertionError message. Actual: [" + ex.getMessage() + "] Expected: [" + errorMessage + "]");
                }
            }
            return;
        }
        throw new AssertionError("Expected AssertionError.");
    }

    interface AssertionErrorAction {
        void expectAssertionError();
    }
}
