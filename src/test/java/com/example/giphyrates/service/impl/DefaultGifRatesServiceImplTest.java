package com.example.giphyrates.service.impl;

import com.example.giphyrates.client.RateClient;
import com.example.giphyrates.service.GifService;
import com.example.giphyrates.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DefaultGifRatesServiceImplTest {

    private static final String CURRENCY_CODE = "ABC";

    @Value("${giphy.rich}")
    private String richTag;

    @Value("${giphy.broke}")
    private String brokeTag;

    @MockBean
    private GifService gifService;
    @MockBean
    private RateService rateService;

    @Autowired
    private DefaultGifRatesServiceImpl defaultGifRatesServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCurrencyExchangeRateAsGif_whenCurrencyExchangeRateIncreased_thenRichGifReturned() {
        when(rateService.checkRate(CURRENCY_CODE)).thenReturn(richTag);
        when(gifService.getGif(richTag)).thenReturn(any());

        defaultGifRatesServiceImpl.getCurrencyExchangeRateAsGif(CURRENCY_CODE);

        verify(gifService).getGif(richTag);
        verify(rateService).checkRate(CURRENCY_CODE);

    }

    @Test
    void getCurrencyExchangeRateAsGif_whenCurrencyExchangeRateDecreased_thenBrokeGifReturned() {
        when(rateService.checkRate(CURRENCY_CODE)).thenReturn(brokeTag);
        when(gifService.getGif(brokeTag)).thenReturn(any());

        defaultGifRatesServiceImpl.getCurrencyExchangeRateAsGif(CURRENCY_CODE);

        verify(gifService).getGif(brokeTag);
        verify(rateService).checkRate(CURRENCY_CODE);
    }

}