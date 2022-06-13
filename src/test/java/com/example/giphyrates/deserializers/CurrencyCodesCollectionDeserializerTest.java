package com.example.giphyrates.deserializers;

import com.example.giphyrates.model.CurrencyCodesCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ObjectMapper.class)
class CurrencyCodesCollectionDeserializerTest {

    @Autowired
    private ObjectMapper objectMapper;
    private URL json;

    @BeforeEach
    void setUp() {
        json = this.getClass()
                .getResource("/json/currencies.json");
    }

    @Test
    void deserialize_whenCurrenciesJsonPassed_thenJsonIsDeserializedToCurrencyCodesCollection() throws IOException {
        Set<String> expectedCurrencyCodes = Set.of("AED", "AFN", "ALL");

        CurrencyCodesCollection currencyCodesCollection = objectMapper.readValue(json, CurrencyCodesCollection.class);

        assertEquals(expectedCurrencyCodes, currencyCodesCollection.getCurrencyCodes());
    }
}
