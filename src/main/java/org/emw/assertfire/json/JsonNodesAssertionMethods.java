package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.collection.CollectionAssertor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

public sealed class JsonNodesAssertionMethods extends JsonAssertionMethods permits JsonNodesAllAssertionMethods, JsonNodesAnyOrderAssertionMethods, JsonNodesAnyOrderOnlyAssertionMethods, JsonNodesCaseInsensitivelyAssertionMethods, JsonNodesCaseInsensitivityOnlyAssertionMethods, JsonNodesNotAssertionMethods, JsonNodesNotOnlyAssertionMethods {
    public final JsonNodesBeAssertionMethods be;

    protected JsonNodesAssertionMethods(@NonNull JsonAssertionGroup group, @Nullable JSONArray jsonArray, boolean negated, boolean ignoreCase, boolean inAnyOrder, @NonNull List<String> excludedNodes) {
        super(group, jsonArray, negated, ignoreCase, inAnyOrder, excludedNodes);
        this.be = new JsonNodesBeAssertionMethods(group, jsonArray, negated, ignoreCase, inAnyOrder, excludedNodes);
    }

    @Override
    protected void setThrowable(@NonNull Throwable throwable) {
        super.setThrowable(throwable);
        this.be.setThrowable(throwable);
    }
    /**
     * Assert that the current JSON node exists.
     */
    public void exist() {
        if (negated) {
            if (throwable() == null) {
                addToGroup(new AssertionError("Expected node to not exist."));
            }
        } else {
            final Throwable th = throwable();

            if (th != null) {
                if (th.getMessage() == null) {
                    addToGroup(new AssertionError("Expected node to exist."));
                } else {
                    addToGroup(new AssertionError("Expected node to exist: " + th.getMessage()));
                }
            }
        }
    }

    /**
     * Assert that all elements in the JSON array match the expected array of objects.
     * @param expectedArray varargs of expected objects
     */
    public void allMatch(@NonNull Object... expectedArray) {
        allMatch(List.of(expectedArray));
    }

    /**
     * Assert that all elements in the JSON array match the expected collection.
     * <p>
     * Supports order-sensitive or any-order matching based on the {@code inAnyOrder} flag,
     * and case-insensitive string comparison if {@code ignoreCase} is enabled.
     * @param expectedCollection collection of expected objects
     */
    public void allMatch(@NonNull Collection<?> expectedCollection) {
        assertCondition(() -> {
            final JSONArray jsonArray = jsonArray();

            if (jsonArray.length() != expectedCollection.size()) {
                throw new AssertionError("Expected JSON array size does not match with the actual size.");
            } else if (this.inAnyOrder) {
                final List<Object> expectedList = new ArrayList<>(expectedCollection).stream().map(JsonHelper::jsonMapper).sorted(new JsonHelper.JsonComparator()).toList();
                final JsonCollectionAssertor assertor = new JsonCollectionAssertor();

                if (expectedList.stream().allMatch(o -> o instanceof JSONArray || o instanceof JSONObject)) {
                    final List<Object> list = new ArrayList<>(jsonArray.toList()).stream().sorted(new JsonHelper.JsonComparator()).toList();
                    final JSONArray jArray = new JSONArray(list);

                    for (int i = 0; i < list.size(); i++) {
                        final @NonNull Object expected = expectedList.get(i) == null ? ""  : expectedList.get(i);
                        final String errorMessage = JsonHelper.matchJson(jArray.get(i), expected, this.excludedNodes(), this.ignoreCase);

                        if (!errorMessage.isEmpty()) {
                            if (negated) {
                                return;
                            } else {
                                throw new AssertionError(errorMessage);
                            }
                        }
                    }
                    if (negated) {
                        throw new AssertionError("Expected JSON array to not match in any order, but it does.");
                    }
                } else if (ignoreCase) {
                    assertor.expect("JSON Array", jsonArray.toList()).to.inAnyOrder.caseInsensitively.allMatch(expectedList);
                } else {
                    assertor.expect("JSON Array", jsonArray.toList()).to.inAnyOrder.allMatch(expectedList);
                }
            } else {
                final List<Object> expectedList = expectedCollection.stream().map(JsonHelper::jsonMapper).toList();
                final JsonCollectionAssertor assertor = new JsonCollectionAssertor();

                if (expectedList.stream().allMatch(o -> o instanceof JSONArray || o instanceof JSONObject)) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final @NonNull Object expected = expectedList.get(i) == null ? ""  : expectedList.get(i);
                        final String errorMessage = JsonHelper.matchJson(jsonArray.get(i), expected, this.excludedNodes(), this.ignoreCase);

                        if (!errorMessage.isEmpty()) {
                            if (negated) {
                                return;
                            } else {
                                throw new AssertionError(errorMessage);
                            }
                        }
                    }
                    if (negated) {
                        throw new AssertionError("Expected JSON array to not match, but it does.");
                    }
                } else if (ignoreCase){
                    assertor.expect("JSON Array", jsonArray.toList()).to.caseInsensitively.allMatch(expectedList);
                } else {
                    assertor.expect("JSON Array", jsonArray.toList()).to.allMatch(expectedList);
                }
            }
        });

    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding).
     * @param jsonUrl URL to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void be(@Nullable URL jsonUrl, String... formatterArgs) {
        be(JsonHelper.loadJsonFile(jsonUrl, formatterArgs));
    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding).
     * @param jsonPath Path to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void be(@NonNull Path jsonPath, String... formatterArgs) {
        be(JsonHelper.loadJsonFile(jsonPath, formatterArgs));
    }

    /**
     * Assert for the exact match, other than described by modifiers (not, caseInsensitively, excluding).
     * @param jsonFile JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void be(@NonNull File jsonFile, String... formatterArgs) {
        be(JsonHelper.loadJsonFile(jsonFile, formatterArgs));
    }

    /**
     * Assert that the JSON array matches the provided JSON string or JSONArray.
     * @param expected a JSON string or JSONArray representing a JSON array
     */
    public void be(@NonNull Object expected) {
        assertCondition(() -> {
            if (expected instanceof String) {
                final String expectedString = String.valueOf(expected);
                final Object expectedObject = JsonHelper.isJsonArray(expectedString) ? new JSONArray(expectedString) : expected;
                final JSONArray jsonArray = jsonArray();

                if (expectedObject instanceof JSONArray) {
                    final String errorMessage = JsonHelper.matchJson(jsonArray, expectedObject, this.excludedNodes(), this.ignoreCase);

                    if (negated) {
                        if (errorMessage.isEmpty()) {
                            throw new AssertionError("Expected JSON to not match, but they match.");
                        }
                    } else {
                        if (!errorMessage.isEmpty()) {
                            throw new AssertionError(errorMessage);
                        }
                    }
                } else {
                    if (!negated) {
                        throw new AssertionError("Expected JSON to match.");
                    }
                }
            } else if (expected instanceof JSONArray) {
                final JSONArray jsonArray = jsonArray();
                final String errorMessage = JsonHelper.matchJson(jsonArray, expected, this.excludedNodes(), this.ignoreCase);

                if (negated) {
                    if (errorMessage.isEmpty()) {
                        throw new AssertionError("Expected JSON to not match, but they match.");
                    }
                } else {
                    if (!errorMessage.isEmpty()) {
                        throw new AssertionError(errorMessage);
                    }
                }
            } else {
                if (!negated) {
                    throw new AssertionError("Expected JSON to match.");
                }
            }
        });
    }

    /**
     * Assert that expected JSON data is found within the actual JSON array.
     * @param containedJsonText the JSON string
     */
    public void findJson(@NonNull String containedJsonText) {
        if (JsonHelper.isJson(containedJsonText)) {
            findJson(new JSONObject(containedJsonText));
        } else if (JsonHelper.isJsonArray(containedJsonText)) {
            findJson(new JSONArray(containedJsonText));
        } else {
            throw new AssertionError("Not JSON.");
        }
    }


    /**
     * Assert that expected JSON data is found within the actual JSON array.
     * @param jsonUrl URL to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void findJson(@Nullable URL jsonUrl, String... formatterArgs) {
        findJson(JsonHelper.loadJsonFile(jsonUrl, formatterArgs));
    }

    /**
     * Assert that expected JSON data is found within the actual JSON array.
     * @param jsonPath Path to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void findJson(@NonNull Path jsonPath, String... formatterArgs) {
        findJson(JsonHelper.loadJsonFile(jsonPath, formatterArgs));
    }

    /**
     * Assert that expected JSON data is found within the actual JSON array.
     * @param jsonFile JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     */
    public void findJson(@NonNull File jsonFile, String... formatterArgs) {
        findJson(JsonHelper.loadJsonFile(jsonFile, formatterArgs));
    }

    /**
     * Assert that expected JSON data is found within the actual JSON array.
     * @param jsonToFind JSONObject or JSONArray to find
     */
    public void findJson(@NonNull Object jsonToFind) {
        assertCondition(() -> {
            final JSONArray jsonArray = jsonArray();

            if (negated) {
                if (JsonHelper.findJson(jsonArray, jsonToFind, this.excludedNodes(), this.ignoreCase)) {
                    throw new AssertionError("Expected to not find JSON data within actual JSON data.");
                }
            } else {
                if (!JsonHelper.findJson(jsonArray, jsonToFind, this.excludedNodes(), this.ignoreCase)) {
                    throw new AssertionError("Expected to find JSON data within actual JSON data.");
                }
            }
        });
    }

    /**
     * Assert that the JSON array contains the specified objects.
     * @param expectedArray varargs of objects expected to be present
     */
    public void contain(@NonNull Object... expectedArray) {
        contain(List.of(expectedArray));
    }

    /**
     * Assert that the JSON array contains elements in the specified collection.
     * @param expectedCollection collection of objects expected to be present
     */
    public void contain(@NonNull Collection<?> expectedCollection) {
        assertCondition(() -> {
            final JSONArray jsonArray = jsonArray();

            final List<Object> expectedList = expectedCollection.stream().map(JsonHelper::jsonMapper).toList();
            final JsonCollectionAssertor assertor = new JsonCollectionAssertor();
            int foundCount = 0;

            if (expectedList.stream().allMatch(o -> o instanceof JSONArray || o instanceof JSONObject)) {
                for (Object expected : expectedList) {
                    final boolean found = StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(o -> JsonHelper.matchJson(o, expected, this.excludedNodes(), this.ignoreCase).isEmpty());

                    if (found) {
                        foundCount++;
                    }
                }

                if (negated) {
                    if (foundCount == expectedList.size()) {
                        throw new AssertionError("Expected JSON array to not have expected JSON data, but it does.");
                    }
                } else {
                    if (foundCount != expectedList.size()) {
                        throw new AssertionError("Expected JSON array to have expected JSON data, but it does not.");
                    }
                }
            } else if (ignoreCase){
                assertor.expect("JSON Array", jsonArray.toList()).to.caseInsensitively.contain(expectedList);
            } else {
                assertor.expect("JSON Array", jsonArray.toList()).to.contain(expectedList);
            }
        });
    }


    private class JsonCollectionAssertor implements CollectionAssertor {
        private JsonCollectionAssertor() {}
    }
}
