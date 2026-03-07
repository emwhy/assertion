package org.emw.assertion.json;

import org.emw.assertion.Connector;

import java.util.List;

public class JsonObjectTo extends Connector {
    public final JsonObjectAssertionMethods to;
    protected final Object json;

    protected JsonObjectTo(Object json) {
        super(null, "");
        this.json = json;
        this.to = new JsonObjectAssertionMethods(json, false, false, List.of());
    }

}
