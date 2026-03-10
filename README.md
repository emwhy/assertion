# emw-Assertion

emw-Assertion is a library that provides fluent assertions, inspired by Chai (JavaSCript library) in BDD style. It can be used for any projects, such as UI test automation, API test automation and unit testing, that needs to assert various data types.

## Highlights

- Fluent, English-like assertion structure with "expect ... to ..." BDD style format.
- Fluent Json assertion.
- Assertion grouping.
- Supports assertions with string, numeric values and classes, collections, array, date objects (SQL Date and LocalDate), date/time object (LocalDateTime), and Time (LocalTime).

## Contents

  * [Fluent Assertions](#fluent-assertions)
  * [Json Assertion](#json-assertion)
  * [Assertion Groups](#assertion-groups)
  * [Setting Up emw-Assertion](#setting-up-emw-assertion)
  * [Implementing emw-Assertion](#implementing-emw-assertion)
  * [Finally...](#finally)


## Fluent Assertions

As you can see in these examples, it is written in very fluent manner. It's easy to read what is being asserted. With IDE's code completion tool, it is fast to write them.

```java
    // Strings.
    expect("Test 1", testText).to.startWith("te");
    expect("Test 2", testText).to.caseInsensitively.startWith("TES");
    expect("Test 3", testText).to.not.endWith("es");
    expect("Test 4", testText).to.caseInsensitively.not.endWith("ES");
    expect("Test 5", testText).to.be(testText);
    expect("Test 6", testText).to.caseInsensitively.not.be("ES");
    expect("Test 7", testText).to.contain("es");
    expect("Test 8", testText).to.caseInsensitively.contain("ES");
    expect("Test 9", testText).to.match("\\w+");
    expect("Test 10", testText).to.not.match("\\d+");
    expect("Test 11", testText).to.be.oneOf("test1", "test2", "test3");
    expect("Test 12", testText).to.caseInsensitively.be.oneOf("TEST1", "TEST2", "TEST3");

    // Numbers.
    expect("Test 1", i).to.be(10);
    expect("Test 2", i).to.be(10.1f);
    expect("Test 3", i).to.not.be(10);
    expect("Test 4", i).to.be.moreThan(10);
    expect("Test 5", i).to.be.lessThan(10.2f);
    expect("Test 6", i).to.be.moreThanOrEqual(9.9f);
    expect("Test 7", i).to.be.lessThanOrEqual(10.1f);
    expect("Test 8", i).to.be.moreThanOrEqual(10.0f);
    expect("Test 9", i).to.be.lessThanOrEqual(10.0);
    expect("Test 10", i).to.be.between(9.9, 11);

    // Collection
    expect("Test 1", testCollection).to.be("test1", "test2");
    expect("Test 2", testCollection).to.inAnyOrder.be("test2", "test1");
    expect("Test 3", testCollection).to.have("test2", "test1");
    expect("Test 4", testCollection).to.caseInsensitively.be("Test1", "test2");
    expect("Test 5", testCollection).to.caseInsensitively.inAnyOrder.be("Test2", "test1");
    expect("Test 6", testCollection).to.caseInsensitively.have("Test2");
    expect("Test 7", testCollection).to.haveSizeOf(2);
    expect("Test 8", testCollection).to.be.empty();

    // Boolean
    expect("Test 1", testBoolean).to.be.trueValue();
    expect("Test 2", testBoolean).to.not.be.trueValue();
    
    // Date
    expect("Test 1", testDate).to.be(LocalDate.of(2020, 1, 1));
    expect("Test 2", testDate).to.not.be(LocalDate.of(2020, 1, 2));
    expect("Test 3", testDate).to.be.sameDateAs(LocalDateTime.of(2020, 1, 1, 2, 3, 45));
    expect("Test 4", testDate).to.be.after(LocalDate.of(2019, 12, 31));
    expect("Test 5", testDate).to.be.before(LocalDate.of(2020, 1, 2));
    expect("Test 6", testDate).to.be.sameOrBefore(LocalDate.of(2020, 1, 1));
    expect("Test 7", testDate).to.be.sameOrAfter(LocalDate.of(2020, 1, 1));
    expect("Test 8", testDate).to.be.between(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 2));
    expect("Test 9", nullDate).to.be.nullValue();
    expect("Test 10", LocalDate.now()).to.not.be.nullValue();

    // Date/Time
    expect("Test 1", testDateTime).to.be.sameDateAs(LocalDate.of(2020, 1, 1));
    expect("Test 2", testDateTime).to.be(LocalDateTime.of(2020, 1, 1, 0, 0));
    expect("Test 3", testDateTime).to.not.be(LocalDateTime.of(2020, 1, 1, 12, 12));
    expect("Test 4", testDateTime).to.be.after(LocalDateTime.of(2019, 12, 31, 23, 59));
    expect("Test 5", testDateTime).to.be.before(LocalDateTime.of(2020, 1, 2, 0, 0));
    expect("Test 6", testDateTime).to.be.sameOrBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
    expect("Test 7", testDateTime).to.be.sameOrAfter(LocalDateTime.of(2020, 1, 1, 0, 0));
    expect("Test 8", testDateTime).to.be.between(LocalDateTime.of(2019, 12, 31, 23, 59), LocalDateTime.of(2020, 1, 1, 0, 0, 1));

    // Time
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

```

## Json Assertion

Json content often needs assertion particularly in API tests. But asserting Json content can be
difficult and cumbersome because flexibility of Json format. 

This library contains Json specific assertion to ease the difficulty of asserting a Json content.

### Feature Highlights

 * **Json-to-Json equality:** Even if Json data have named data in different orders, it would still recognize them as the same, as long as the content are the same.
 * **Excluding nodes:** Sometimes, everything should match except for a portion of Json (i.e., timestamp). A specific node can be excluded from the assertion.
 * **Data flexibility:** Assertion can be done on a Json data chunk, or individual data.
 * **Fluency:** The code can remain fluent as the rest of the library.
 * **Easily Access data:** It uses Json pointer to access or exclude data.

## Assertion Groups

AssertionGroup allows grouping multiple assertions.

All assertions within a group are tested before throwing*AssertionGroupError**. If multiple assertion failed, all errors are returned in exception message.

This is useful when multiple values need to be checked within the same context. It can save you from executing your tests multiple times to find out all the errors may exist in a context.

```java
    // This would throw AssertionError on the first "expect". Until this is fixed, you have no idea
    // there are other errors or not.
    expect("test").to.be("test1");
    expect("test").to.be("test2");
    expect(1).to.be(1);
    expect(1).to.be(0);
    
     // This would assert all of "expect" and show which ones fail, saving repeated execution of tests.
     assertionGroup(g -> {
         g.expect("test").to.be("test1");
         g.expect("test").to.be("test2");
         g.expect(1).to.be(1);
         g.expect(1).to.be(0);
     });    
```

The error message lists all of the encountered errors.

```
org.emw.assertion.exception.AssertionGroupError: 3 errors in group

	Error #1: java.lang.AssertionError: Expected 'test' to equal 'test1'.

	Error #2: java.lang.AssertionError: Expected 'test' to equal 'test2'.

	Error #3: java.lang.AssertionError: Expected '1' to equal '0'.

	Error Stack #1:
		at org.emw.assertion.AssertionMethods.assertCondition(Conditions.java:25)
		at org.emw.assertion.string.StringAssertionMethods.be(StringConditions.java:23)
		at org.emw.assertion.regression.AssertionTest.lambda$testGroup$69(AssertionTest.java:212)
		at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:71)
		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
		at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
		at jdk.proxy1/jdk.proxy1.$Proxy4.stop(Unknown Source)

	Error Stack #2:
		at org.emw.assertion.AssertionMethods.assertCondition(Conditions.java:25)
		at org.emw.assertion.string.StringAssertionMethods.be(StringConditions.java:23)
		at org.emw.assertion.regression.AssertionTest.lambda$testGroup$69(AssertionTest.java:213)
		at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:71)
		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
		at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
		at jdk.proxy1/jdk.proxy1.$Proxy4.stop(Unknown Source)

	Error Stack #3:
		at org.emw.assertion.AssertionMethods.assertCondition(Conditions.java:25)
		at org.emw.assertion.number.NumberAssertionMethods.be(NumberConditions.java:35)
		at org.emw.assertion.number.NumberAssertionMethods.be(NumberConditions.java:19)
		at org.emw.assertion.regression.AssertionTest.lambda$testGroup$69(AssertionTest.java:215)
		at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:71)
		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
		at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
		at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
		at jdk.proxy1/jdk.proxy1.$Proxy4.stop(Unknown Source)

	at org.emw.assertion.AssertionGroup.group(AssertionGroup.java:74)
	at org.emw.assertion.Assertor.assertionGroup(Assertor.java:41)
	at org.emw.assertion.Assertor.assertionGroup(Assertor.java:37)
	at org.emw.assertion.regression.AssertionTest.testGroup(AssertionTest.java:211)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.testng.internal.invokers.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:141)
	at org.testng.internal.invokers.TestInvoker.invokeMethod(TestInvoker.java:687)
```
### Grouping Different Assertion Solutions

If you have other favorite assertion solutions, you can also use that in conjunction with emw-Assertions's assertion grouping.

```java
     assertionGroup(g -> {
         g.expect("test").to.be("test1");
         g.expect("test").to.be("test2");
         g.expect(1).to.be(1);
         g.expect(1).to.be(0);
         
         // Using TestNG's Assert class in grouping.
         g.assertWith(() -> Assert.assertEquals(1, 0));
         g.assertWith(() -> Assert.assertEquals(testText, "test"));
     }); 
```

## Setting Up emw-Assertion

- Ensure that your project is setup with ***Java 17*** or higher.
- Download the latest **emw-assertion-release.jar** file from https://github.com/emwhy/assertion/releases/. The javadoc for the framework is packaged in  ***emw-assertion-release-javadoc.jar***. When configured, the documentation can be shown right from IDE (such as IntelliJ).
- Move the file to appropriate location in a project directory (i.e., ./lib).
- Add **emw-assertion-release.jar** to the Gradle dependency.
- Add dependency to ***json-java*** package.
```
dependencies {
    implementation(files("lib/emw-assertion-release.jar"));
    implementation("org.json:json:20251224")
}
```
- Reload your Gradle. You should now be able to use emw-Assertion classes.

## Implementing emw-Assertion

To get access to all emw-Assertion methods that include "expect" and "assertionGroup" methods, implement **Assertor** interface to your test class.

If you want all of your test classes to have access to emw-Assertion methods, create a base test class that implements **Assertor** interface.

Once the interface is implemented, all methods become available in any method within the test class.

```java
public class AssertionTest implements Assertor {
    ...
}

```

## Finally...

If you are working on UI test automation projects, please also take a look at [Selentic Framework](https://github.com/emwhy/selentic-framework).