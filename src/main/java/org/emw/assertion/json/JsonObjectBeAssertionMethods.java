package org.emw.assertion.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertion.AssertionMethods;
import org.emw.assertion.bool.BooleanAssertor;
import org.emw.assertion.number.NumberAssertor;
import org.emw.assertion.string.StringAssertor;

public class JsonObjectBeAssertionMethods extends AssertionMethods {
    private final @Nullable Object obj;
    private final boolean negated;
    private final boolean ignoreCase;
    public final JsonStringAssertions string;
    public final JsonNumberAssertions number;
    public final JsonBooleanAssertions bool;

    protected JsonObjectBeAssertionMethods(@Nullable Object obj, boolean negated, boolean ignoreCase) {
        super(null, "", negated, ignoreCase);
        this.obj = obj;
        this.negated = negated;
        this.ignoreCase = ignoreCase;
        this.string = new JsonStringAssertions(this.obj);
        this.number = new JsonNumberAssertions(this.obj);
        this.bool = new JsonBooleanAssertions(this.obj);
    }

    public void nullValue() {
        assertCondition(() -> {
            if (this.obj == null) {
                throw new AssertionError("Node does not exist.");
            } else if (negated) {
                if (String.valueOf(obj).equals("null")) {
                    throw new AssertionError("Node is null.");
                }
            } else if (!String.valueOf(obj).equals("null")) {
                throw new AssertionError("Node is not null.");
            }
        });
    }

    public void stringType() {
        assertCondition(() -> {
            if (this.obj == null) {
                throw new AssertionError("Node does not exist.");
            } else if (negated) {
                if (this.obj instanceof String) {
                    throw new AssertionError("Node is a string.");
                }
            } else if (!(this.obj instanceof String)) {
                throw new AssertionError("Node is not a string.");
            }
        });
    }

    public void numberType() {
        assertCondition(() -> {
            if (this.obj == null) {
                throw new AssertionError("Node does not exist.");
            } else if (negated) {
                if (this.obj instanceof Number) {
                    throw new AssertionError("Node is a number.");
                }
            } else if (!(this.obj instanceof Number)) {
                throw new AssertionError("Node is not a number.");
            }
        });
    }

    public void booleanType() {
        assertCondition(() -> {
            if (this.obj == null) {
                throw new AssertionError("Node does not exist.");
            } else if (negated) {
                if (String.valueOf(obj).equals("true") || String.valueOf(obj).equals("false")) {
                    throw new AssertionError("Node is a boolean.");
                }
            } else if (!String.valueOf(obj).equals("true") && !String.valueOf(obj).equals("false")) {
                throw new AssertionError("Node is not a boolean.");
            }
        });
    }

    public class JsonStringAssertions {
        private final @Nullable Object actual;
        private final JsonStringAssertor assertor = new JsonStringAssertor();

        private JsonStringAssertions(@Nullable Object actual) {
            this.actual = actual;
        }

        private String actualString() {
            if (actual == null) {
                throw new AssertionError("Node is null.");
            } else {
                return ((String) this.actual).trim();
            }
        }

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

    public class JsonNumberAssertions {
        private final @Nullable Object actual;
        private final JsonNumberAssertor assertor = new JsonNumberAssertor();

        private JsonNumberAssertions(@Nullable Object actual) {
            this.actual = actual;
        }

        private Number actualNumber() {
            if (actual == null) {
                throw new AssertionError("Node is null.");
            } else {
                return (Number) actual;
            }
        }

        public void moreThan(@NonNull Number expected) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.moreThan(expected);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.moreThan(expected);
                }
            });
        }

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

        public void moreThanOrEqual(@NonNull Number expected) {
            assertCondition(() -> {
                final Number actualNumber = this.actualNumber();

                if (negated) {
                    assertor.expect("JsonNode Number", actualNumber).to.not.be.moreThanOrEqual(expected);
                } else {
                    assertor.expect("JsonNode Number", actualNumber).to.be.moreThanOrEqual(expected);
                }
            });
        }

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

    public class JsonBooleanAssertions {
        private final @Nullable Object actual;
        private final JsonBooleanAssertor assertor = new JsonBooleanAssertor();

        private JsonBooleanAssertions(@Nullable Object actual) {
            this.actual = actual;
        }

        private boolean actualBoolean() {
            if (actual == null) {
                throw new AssertionError("Node is null.");
            } else if (String.valueOf(actual).equals("true")) {
                return true;
            } else if (String.valueOf(actual).equals("false")) {
                return false;
            } else {
                throw new AssertionError("Node is not a boolean.");
            }
        }

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

    private class JsonStringAssertor implements StringAssertor {
    }

    private class JsonNumberAssertor implements NumberAssertor {
    }

    private class JsonBooleanAssertor implements BooleanAssertor {
    }
}
