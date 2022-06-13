package com.example.giphyrates.service.impl;


import com.example.giphyrates.service.DefaultGifRatesService;
import com.example.giphyrates.service.GifService;
import com.example.giphyrates.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultGifRatesServiceImpl implements DefaultGifRatesService {

    private final RateService rateService;
    private final GifService gifService;

    @Override
    public byte[] getCurrencyExchangeRateAsGif(String currencyCode) {

        String resultTag = rateService.checkRate(currencyCode);
        return  gifService.getGif(resultTag);
    }
}
