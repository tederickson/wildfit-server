package com.wildfit.server.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Tried JsonComponent, Modules, and several other options.
 * Nothing worked so have to anoint every String field with this CustomStringDeserializer.
 */
public class CustomStringDeserializer extends StdDeserializer<String> {
    public CustomStringDeserializer() {
        this(null);
    }

    public CustomStringDeserializer(Class<String> t) {
        super(t);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        final var text = parser.getText();
        return org.apache.commons.lang3.StringUtils.trimToNull(text);
    }
}
