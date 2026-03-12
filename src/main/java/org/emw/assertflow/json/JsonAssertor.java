package org.emw.assertflow.json;

import org.emw.assertflow.AssertionGroup;
import org.emw.assertflow.bool.BooleanAssertor;
import org.emw.assertflow.collection.CollectionAssertor;
import org.emw.assertflow.date.DateAssertor;
import org.emw.assertflow.datetime.DateTimeAssertor;
import org.emw.assertflow.number.NumberAssertor;
import org.emw.assertflow.string.StringAssertor;
import org.emw.assertflow.time.TimeAssertor;
import org.json.JSONException;
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
    default JsonNodeAssertion assertJson(String json) {
        if (JsonHelper.jsonType(json) == JsonType.Object) {
            return new JsonNodeAssertion(json);
        } else {
            throw new JSONException("This is potentially a JSON array rather than an object. Use 'assertJsonArray' instead.");
        }
    }

    /**
     * Start the assertion context for a JSON array.
     * @param json JSON text
     * @return JSON array assertion context
     */
    default JsonNodesAssertion assertJsonArray(String json) {
        if (JsonHelper.jsonType(json) == JsonType.Array) {
            return new JsonNodesAssertion(json);
        } else {
            throw new JSONException("This is potentially a JSON object rather than an array. Use 'assertJson' instead.");
        }
    }
}
