package com.example.giphyrates.model;

import com.example.giphyrates.deserializers.CurrencyCodesCollectionDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonDeserialize(using = CurrencyCodesCollectionDeserializer.class)
public class CurrencyCodesCollection {

    private Set<String> currencyCodes;
}
