package org.emw.assertion.regression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertion.Assertor;
import org.emw.assertion.exception.AssertionGroupError;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class AssertionTest implements Assertor {
    @Test
    public void testString() {
        final String nullString = null;
        
        expect("Test 1", "test").to.startWith("te");
        expect("Test 2", "test").to.caseInsensitively.startWith("TES");
        expect("Test 3", "test").to.not.startWith("es");
        expect("Test 4", "test").to.caseInsensitively.not.startWith("ES");

        expect("Test 5", "test").to.endWith("st");
        expect("Test 6", "test").to.caseInsensitively.endWith("ST");
        expect("Test 7", "test").to.not.endWith("es");
        expect("Test 8", "test").to.caseInsensitively.not.endWith("ES");

        expect("Test 9", "test").to.be("test");
        expect("Test 10", "test").to.caseInsensitively.be("TEST");
        expect("Test 11", "test").to.not.be("es");
        expect("Test 12", "test").to.caseInsensitively.not.be("ES");

        expect("Test 13", "test").to.contain("es");
        expect("Test 14", "test").to.caseInsensitively.contain("ES");
        expect("Test 15", "test").to.not.contain("estt");
        expect("Test 16", "test").to.caseInsensitively.not.contain("ESTT");

        expect("Test 17", "test").to.match("\\w+");
        expect("Test 18", "test").to.not.match("\\d+");

        expect("Test 19", "test").to.be.oneOf("test", "test2", "test3");
        expect("Test 20", "test").to.caseInsensitively.be.oneOf("TEST", "TEST2",  "TEST3");
        expect("Test 21", "test").to.not.be.oneOf("TEST", "TEST2", "TEST3");
        expect("Test 22", "test").to.caseInsensitively.not.be.oneOf("TEST1", "TEST2", "TEST3");

        expect("Test 23", "").to.be.empty();
        expect("Test 24", "test").to.not.be.empty();
        expect("Test 25", nullString).to.be.nullValue();
        expect("Test 26", "null").to.not.be.nullValue();

        expectError(() -> expect("Test 27", "test").to.startWith("es"));

        // startWith Failures
        expectError(() -> expect("Test 28", "test").to.caseInsensitively.startWith("abc"));
        expectError(() -> expect("Test 29", "test").to.not.startWith("te"));
        expectError(() -> expect("Test 30", "test").to.caseInsensitively.not.startWith("TE"));

        // endWith Failures
        expectError(() -> expect("Test 31", "test").to.endWith("te"));
        expectError(() -> expect("Test 32", "test").to.caseInsensitively.endWith("ABC"));
        expectError(() -> expect("Test 33", "test").to.not.endWith("st"));
        expectError(() -> expect("Test 34", "test").to.caseInsensitively.not.endWith("ST"));

        // equal Failures
        expectError(() -> expect("Test 35", "test").to.be("TEST"));
        expectError(() -> expect("Test 36", "test").to.caseInsensitively.be("abc"));
        expectError(() -> expect("Test 37", "test").to.not.be("test"));
        expectError(() -> expect("Test 38", "test").to.caseInsensitively.not.be("TEST"));

        // contain Failures
        expectError(() -> expect("Test 39", "test").to.contain("abc"));
        expectError(() -> expect("Test 40", "test").to.caseInsensitively.contain("ABC"));
        expectError(() -> expect("Test 41", "test").to.not.contain("es"));
        expectError(() -> expect("Test 42", "test").to.caseInsensitively.not.contain("ES"));

        // match (Regex) Failures
        expectError(() -> expect("Test 43", "test").to.match("\\d+"));
        expectError(() -> expect("Test 44", "test").to.not.match(".*st"));

        // beOneOf Failures
        expectError(() -> expect("Test 45", "test").to.be.oneOf("abc", "123"));
        expectError(() -> expect("Test 46", "test").to.caseInsensitively.be.oneOf("ABC", "123"));
        expectError(() -> expect("Test 47", "test").to.not.be.oneOf("test", "other"));
        expectError(() -> expect("Test 48", "test").to.caseInsensitively.not.be.oneOf("TEST", "other"));

        // beEmpty Failures
        expectError(() -> expect("Test 49", "test").to.be.empty());
        expectError(() -> expect("Test 50", "").to.not.be.empty());

        // beNull Failures
        expectError(() -> expect("Test 51", "test").to.be.nullValue());
        expectError(() -> expect("Test 52", nullString).to.not.be.nullValue());

        // Blank/Whitespace checks (if applicable in your class)
        expectError(() -> expect("Test 53", "   ").to.be.empty());
    }

    @Test
    public void testCollection() {
        final String[] emptyArray = new String[0];
        final String[] nullArray = null;

        expect("Test 1", new String[] { "test1", "test2" }).to.be("test1", "test2");
        expect("Test 2", new String[] { "test1", "test2" }).to.inAnyOrder.be("test2", "test1");
        expect("Test 3", new String[] { "test1", "test2" }).to.have("test2", "test1");
        expect("Test 4", new String[] { "test1", "test2" }).to.caseInsensitively.be("Test1", "test2");
        expect("Test 5", new String[] { "test1", "test2" }).to.caseInsensitively.inAnyOrder.be("Test2", "test1");
        expect("Test 6", new String[] { "test1", "test2" }).to.caseInsensitively.have("Test2");
        expect("Test 7", new String[] { "test1", "test2" }).to.haveSizeOf(2);
        expect("Test 8", emptyArray).to.be.empty();
        expect("Test 9", nullArray).to.be.nullValue();
        expect("Test 10", new String[] { "test1", "test2" }).to.not.be.empty();
        expect("Test 11", emptyArray).to.not.be.nullValue();

        expectError(() -> expect("Test 12", new String[] { "test1", "test2" }).to.be("Test1", "test2"));
        expectError(() -> expect("Test 13", new String[] { "test1", "test2" }).to.not.be("test1", "test2"));
        expectError(() -> expect("Test 14", new String[] { "test1", "test2" }).to.inAnyOrder.be("Test2", "test1"));
        expectError(() -> expect("Test 15", new String[] { "test1", "test2" }).to.have("Test2"));
        expectError(() -> expect("Test 16", new String[] { "test1", "test2" }).to.have("test1", "test2", "test3"));
        expectError(() -> expect("Test 17", new String[] { "test1", "test2" }).to.haveSizeOf(1));
        expectError(() -> expect("Test 18", new String[] { "test1", "test2" }).to.be.empty());
        expectError(() -> expect("Test 19", new String[] { "test1", "test2" }).to.be.nullValue());
    }

    @Test
    public void testBoolean() {
        final Boolean nullBoolean = null;
        expect("Test 1", true).to.be.trueValue();
        expect("Test 2", false).to.not.be.trueValue();
        expect("Test 3", nullBoolean).to.be.nullValue();
        expect("Test 4", Boolean.valueOf(false)).to.not.be.nullValue();

        expectError(() -> expect("Test 5", false).to.be.trueValue());
        expectError(() -> expect("Test 6", true).to.not.be.trueValue());
    }

    @Test
    public void testDate() {
        final LocalDate nullDate = null;

        expect("Test 1", LocalDate.of(2020, 1, 1)).to.be.sameDate(LocalDate.of(2020, 1, 1));
        expect("Test 2", LocalDate.of(2020, 1, 1)).to.not.be.sameDate(LocalDate.of(2020, 1, 2));
        expect("Test 3", LocalDate.of(2020, 1, 1)).to.be.after(LocalDate.of(2019, 12, 31));
        expect("Test 4", LocalDate.of(2020, 1, 1)).to.be.before(LocalDate.of(2020, 1, 2));
        expect("Test 5", LocalDate.of(2020, 1, 1)).to.be.sameOrBefore(LocalDate.of(2020, 1, 1));
        expect("Test 6", LocalDate.of(2020, 1, 1)).to.be.sameOrBefore(LocalDate.of(2020, 1, 2));
        expect("Test 7", LocalDate.of(2020, 1, 1)).to.be.sameOrAfter(LocalDate.of(2020, 1, 1));
        expect("Test 8", LocalDate.of(2020, 1, 1)).to.be.sameOrAfter(LocalDate.of(2019, 12, 31));
        expect("Test 9", LocalDate.of(2020, 1, 1)).to.be.between(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 2));
        expect("Test 10", nullDate).to.be.nullValue();
        expect("Test 11", LocalDate.now()).to.not.be.nullValue();

        expectError(() -> expect("Test 12", LocalDate.of(2020, 1, 1)).to.be.sameDate(LocalDate.of(2020, 1, 2)));
        expectError(() -> expect("Test 13", LocalDate.of(2020, 1, 1)).to.not.be.sameDate(LocalDate.of(2020, 1, 1)));
        expectError(() -> expect("Test 14", LocalDate.of(2020, 1, 1)).to.be.after(LocalDate.of(2020, 1, 1)));
        expectError(() -> expect("Test 15", LocalDate.of(2020, 1, 1)).to.be.after(LocalDate.of(2020, 1, 2)));
        expectError(() -> expect("Test 16", LocalDate.of(2020, 1, 1)).to.be.before(LocalDate.of(2020, 1, 1)));
        expectError(() -> expect("Test 17", LocalDate.of(2020, 1, 1)).to.be.before(LocalDate.of(2019, 12, 31)));
        expectError(() -> expect("Test 18", LocalDate.of(2020, 1, 1)).to.be.sameOrBefore(LocalDate.of(2019, 12, 31)));
        expectError(() -> expect("Test 19", LocalDate.of(2020, 1, 1)).to.be.sameOrAfter(LocalDate.of(2020, 1, 2)));
        expectError(() -> expect("Test 20", LocalDate.of(2020, 1, 3)).to.be.between(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2)));
        expectError(() -> expect("Test 21", LocalDate.of(2020, 1, 5)).to.be.between(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 1)));
        expectError(() -> expect("Test 22", LocalDate.of(2020, 1, 1)).to.be.between(LocalDate.of(2020, 1, 2), LocalDate.of(2021, 1, 1)));
        expectError(() -> expect("Test 23", LocalDate.now()).to.be.nullValue());
        expectError(() -> expect("Test 24", nullDate).to.not.be.nullValue());
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

        expectError(() -> expect("Test 13", 10).to.be.between(10.1, 11));

        expectError(() -> expect("Test 14", 10).to.be(11));
        expectError(() -> expect("Test 15", 10.1).to.be(10.11));
        expectError(() -> expect("Test 16", 10).to.not.be(10.0));

        expectError(() -> expect("Test 17", 10).to.be.moreThan(10));
        expectError(() -> expect("Test 18", 10).to.be.moreThan(10.1));
        expectError(() -> expect("Test 19", -5).to.be.moreThan(0));

        expectError(() -> expect("Test 20", 10).to.be.lessThan(10));
        expectError(() -> expect("Test 21", 10.5).to.be.lessThan(10.4));
        expectError(() -> expect("Test 22", 0).to.be.lessThan(-1));

        expectError(() -> expect("Test 23", 10).to.be.moreThanOrEqual(11));
        expectError(() -> expect("Test 24", 10.1).to.be.lessThanOrEqual(10.0));
        expectError(() -> expect("Test 25", -10).to.be.moreThanOrEqual(-9.9));

        expectError(() -> expect("Test 26", 11.1).to.be.between(10, 11));
        expectError(() -> expect("Test 27", 9.9).to.be.between(10, 11));
        expectError(() -> expect("Test 28", 5).to.be.between(6, 7));

        expectError(() -> expect("Test 27", 100L).to.be.lessThan(50));
        expectError(() -> expect("Test 28", 0.01f).to.be.moreThan(0.1));
        expectError(() -> expect("Test 29", nullNumber).to.be.moreThan(0.1));
    }

    @Test
    public void testGroup() {
        try {
            assertionGroup(g -> {
                g.expect("test").to.be("test1");
                g.expect("test").to.be("test2");
                g.expect(1).to.be(1);
                g.expect(1).to.be(0);
            });
        } catch (AssertionGroupError ex) {
            expect(ex.getMessage()).to.contain("Expected 'test' to equal 'test1'.");
            expect(ex.getMessage()).to.contain("Expected 'test' to equal 'test2'.");
            expect(ex.getMessage()).to.contain("Expected '1' to equal '0'.");
        }
    }

    private void expectError(AssertionErrorAction action) {
        try {
            action.expectAssertionError();
        } catch (AssertionError ex) {
            System.out.println("Error message = \"" + ex.getMessage() + "\"");
            return;
        }
        throw new AssertionError("Expected AssertionError.");
    }

    private void expectError(@NonNull AssertionErrorAction action, @NonNull String errorMessage) {
        try {
            action.expectAssertionError();
        } catch (AssertionError ex) {
            if (ex.getMessage() == null || !ex.getMessage().equals(errorMessage)) {
                throw new AssertionError("Unexpected AssertionError message. Actual: [" + ex.getMessage() + "] Expected: [" + errorMessage + "]");
            }
            return;
        }
        throw new AssertionError("Expected AssertionError.");
    }

    interface AssertionErrorAction {
        void expectAssertionError();
    }
}
