package com.example.giphyrates.service.impl;


import com.example.giphyrates.client.RateClient;
import com.example.giphyrates.model.CurrencyCodesCollection;
import com.example.giphyrates.model.ExchangeRates;
import com.example.giphyrates.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

/**
 * Сервис для работы с openexchangerates.org
 */
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    @Value("${basecurrency}")
    String base;
    @Value("${openexchangeratesapi.id}")
    private String openExchangeRateApiId;

    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.zero}")
    private String ZeroTag;
    @Value("${giphy.error}")
    private String errorTag;

    private final RateClient ratesClient;


    public String checkRate(String rate) {

        if (!isCurrencyCodePresent(rate)) {
            throw new IllegalArgumentException("Currency code not found");
        }

        ExchangeRates currentRates = ratesClient.getCurrentRate(openExchangeRateApiId);
        ExchangeRates yesterdayRates = ratesClient.getYesterdayRate(getYesterdayDate(), openExchangeRateApiId);

        Double YesterdayCoefficient = getCoefficient(yesterdayRates, rate);
        Double currentCoefficient = getCoefficient(currentRates, rate);

        int result = YesterdayCoefficient != null && currentCoefficient != null
                ? Double.compare(currentCoefficient, YesterdayCoefficient)
                : -101;

        String gifTag = null;
        switch (result) {
            case 1:
                gifTag = richTag;
                break;
            case -1:
                gifTag = brokeTag;
                break;
            case 0:
                gifTag = ZeroTag;
                break;
            case -101:
                gifTag = errorTag;
                break;
        }

        return gifTag;
    }


    public LocalDate getYesterdayDate() {
        return LocalDate.now()
                .minusDays(1);
    }

    private Double getCoefficient(ExchangeRates rates, String charCode) {
        Double result = null;
        Double targetRate = null;
        Double appBaseRate = null;
        Map<String, Double> map = null;
        if (rates != null && rates.getRates() != null) {
            map = rates.getRates();
            targetRate = map.get(charCode);
            appBaseRate = map.get(base);
        }
        if (targetRate != null && appBaseRate != null) {
            result = targetRate / appBaseRate;

            return result;
        }
        return result;
    }


    public CurrencyCodesCollection getCurrencyCodesCollection() {
        return ratesClient.getCurrencyCodesCollection();
    }

    private boolean isCurrencyCodePresent(final String currencyCode) {
        CurrencyCodesCollection currencyCodesCollection = getCurrencyCodesCollection();
        return currencyCodesCollection.getCurrencyCodes()
                .contains(currencyCode);
    }
}
