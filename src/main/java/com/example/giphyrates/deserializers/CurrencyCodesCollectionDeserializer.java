package com.example.giphyrates.deserializers;

import com.example.giphyrates.model.CurrencyCodesCollection;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class CurrencyCodesCollectionDeserializer extends StdDeserializer<CurrencyCodesCollection> {

    public CurrencyCodesCollectionDeserializer() {
        this(null);
    }

    public CurrencyCodesCollectionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CurrencyCodesCollection deserialize(final JsonParser parser,
                                               final DeserializationContext ctxt) throws IOException {
        Set<String> currencyCodes = new TreeSet<>();
        Iterator<String> fieldNames = getFieldNames(parser);
        fieldNames.forEachRemaining(currencyCodes::add);
        return new CurrencyCodesCollection(currencyCodes);
    }

    private Iterator<String> getFieldNames(final JsonParser parser) throws IOException {
        return parser.getCodec()
                .readTree(parser)
                .fieldNames();
    }
}

