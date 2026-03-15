package org.emw.assertfire.json;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emw.assertfire.AssertionGroup;
import org.emw.assertfire.bool.BooleanAssertor;
import org.emw.assertfire.collection.CollectionAssertor;
import org.emw.assertfire.date.DateAssertor;
import org.emw.assertfire.datetime.DateTimeAssertor;
import org.emw.assertfire.number.NumberAssertor;
import org.emw.assertfire.string.StringAssertor;
import org.emw.assertfire.time.TimeAssertor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

/**
 * Entry point for JSON assertion logic.
 * <p>
 * This interface provides default methods to initialize assertion contexts for both
 * JSON objects and JSON arrays.
 *
 * @see StringAssertor
 * @see BooleanAssertor
 * @see CollectionAssertor
 * @see DateAssertor
 * @see NumberAssertor
 * @see AssertionGroup
 * @see DateTimeAssertor
 * @see TimeAssertor
 */
public interface JsonAssertor {
    /**
     * Start the assertion context for a JSON object.
     * @param json JSON text
     * @return JSON assertion context
     */
    default JsonNodeAssertion assertJson(@NonNull String json) {
        if (JsonHelper.jsonType(json) == JsonType.Object) {
            return new JsonNodeAssertion(new JSONObject(json));
        } else {
            throw new JSONException("This is potentially a JSON array rather than an object. Use 'assertJsonArray' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON object.
     * @param jsonUrl A path to JSON file.
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSON assertion context
     */
    default JsonNodeAssertion assertJson(@Nullable URL jsonUrl, @NonNull String... formatterArgs) {
        final Object o = JsonHelper.loadJsonFile(jsonUrl, formatterArgs);

        if (o instanceof JSONObject) {
            return this.assertJson((JSONObject) o);
        } else {
            throw new JSONException("This is potentially a JSON array rather than an object. Use 'assertJsonArray' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON object.
     * @param jsonPath A path to JSON file.
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSON assertion context
     */
    default JsonNodeAssertion assertJson(@NonNull Path jsonPath, @NonNull String... formatterArgs) {
        return assertJson(jsonPath.toFile(), formatterArgs);
    }

    /**
     * Start the assertion context for a JSON object.
     * @param jsonFile A file that contain JSON data
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSON assertion context
     */
    default JsonNodeAssertion assertJson(@NonNull File jsonFile, @NonNull String... formatterArgs) {
        final Object o = JsonHelper.loadJsonFile(jsonFile, formatterArgs);

        if (o instanceof JSONObject) {
            return this.assertJson((JSONObject) o);
        } else {
            throw new JSONException("This is potentially a JSON array rather than an object. Use 'assertJsonArray' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON object.
     * @param json JSONObject
     * @return JSON assertion context
     */
    default JsonNodeAssertion assertJson(@NonNull JSONObject json) {
        return new JsonNodeAssertion(json);
    }

    /**
     * Start the assertion context for a JSON array.
     * @param json JSON text
     * @return JSON array assertion context
     */
    default JsonNodesAssertion assertJsonArray(@NonNull String json) {
        if (JsonHelper.jsonType(json) == JsonType.Array) {
            return new JsonNodesAssertion(new JSONArray(json));
        } else {
            throw new JSONException("This is potentially a JSON object rather than an array. Use 'assertJson' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON array.
     * @param jsonUrl A path to JSON file.
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSON assertion context
     */
    default JsonNodesAssertion assertJsonArray(@Nullable URL jsonUrl, @NonNull String... formatterArgs) {
        final Object o = JsonHelper.loadJsonFile(jsonUrl, formatterArgs);

        if (o instanceof JSONArray) {
            return this.assertJsonArray((JSONArray) o);
        } else {
            throw new JSONException("This is potentially a JSON object rather than an array. Use 'assertJson' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON array.
     * @param jsonPath A path to JSON file
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSON array assertion context
     */
    default JsonNodesAssertion assertJsonArray(@NonNull Path jsonPath, @NonNull String... formatterArgs) {
        return assertJsonArray(jsonPath.toFile(), formatterArgs);
    }

    /**
     * Start the assertion context for a JSON array.
     * @param jsonFile A file that contain JSON
     * @param formatterArgs If the text in jsonFile contains format specifier (%s, %d, etc.), these are used for the formatting.
     * @return JSON array assertion context
     */
    default JsonNodesAssertion assertJsonArray(@NonNull File jsonFile, @NonNull String... formatterArgs) {
        final Object o = JsonHelper.loadJsonFile(jsonFile, formatterArgs);

        if (o instanceof JSONArray) {
            return this.assertJsonArray((JSONArray) o);
        } else {
            throw new JSONException("This is potentially a JSON object rather than an array. Use 'assertJson' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON array.
     * @param jsonArray JSONArray
     * @return JSON array assertion context
     */
    default JsonNodesAssertion assertJsonArray(@NonNull JSONArray jsonArray) {
        return new JsonNodesAssertion(new JSONArray(jsonArray));
    }
}
