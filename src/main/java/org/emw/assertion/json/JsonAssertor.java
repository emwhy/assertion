package org.emw.assertion.json;

import org.json.JSONException;

public interface JsonAssertor {
    default JsonObjectAssertion assertJson(String json) {
        if (JsonHelper.jsonType(json) == JsonType.Object) {
            return new JsonObjectAssertion(json);
        } else {
            throw new JSONException("This is potentially a Json array rather than an object. Use 'assertJsonArray' instead.");
        }
    }

    default JsonArrayAssertion assertJsonArray(String json) {
        if (JsonHelper.jsonType(json) == JsonType.Array) {
            return new JsonArrayAssertion(json);
        } else {
            throw new JSONException("This is potentially a Json object rather than an array. Use 'assertJson' instead.");
        }
    }
}
