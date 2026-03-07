package org.emw.assertion.json;

import org.json.JSONArray;

public class JsonArrayTo {
    public final JsonArrayAssertionMethods to;
    protected final JSONArray jsonArray;

    protected JsonArrayTo(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.to = new JsonArrayAssertionMethods(jsonArray, false, false);
    }

}
