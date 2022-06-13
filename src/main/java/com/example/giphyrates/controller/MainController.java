package com.example.giphyrates.controller;


import com.example.giphyrates.service.DefaultGifRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class MainController {

    private final DefaultGifRatesService defaultGifRatesService;

    @GetMapping(value = "/{currency-code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getCurrencyExchangeRateAsGif(
            @PathVariable(name = "currency-code") final String currencyCode) {

        return ResponseEntity.ok().contentType(MediaType.IMAGE_GIF)
                .body(defaultGifRatesService.getCurrencyExchangeRateAsGif(currencyCode));
    }
}