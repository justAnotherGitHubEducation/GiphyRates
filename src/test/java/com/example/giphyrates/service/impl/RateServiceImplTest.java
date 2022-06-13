package com.example.giphyrates.service.impl;

import com.example.giphyrates.client.RateClient;
import com.example.giphyrates.model.CurrencyCodesCollection;
import com.example.giphyrates.model.ExchangeRates;
import com.example.giphyrates.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.when;

@TestInstance(PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RateServiceImplTest {

    private CurrencyCodesCollection expectedCurrencyCodesCollection;
    private static final String CURRENCY_CODE = "AAA";
    @Value("${openexchangeratesapi.id}")
    private String openExchangeRateApiId;

    @Value("${basecurrency}")
    private  String  base;
    @Value("${giphy.rich}")
    private  String richTag;
    @Value("${giphy.broke}")
    private  String brokeTag;
    @Value("${giphy.zero}")
    private  String zeroTag;

    @Autowired
    private RateService rateService;

    @MockBean
    private RateClient rateClient;


    @BeforeEach
    void setUp() {
        expectedCurrencyCodesCollection = new CurrencyCodesCollection(Set.of(CURRENCY_CODE));
        when(rateService.getCurrencyCodesCollection()).thenReturn(expectedCurrencyCodesCollection);
    }

    @Test
    void getCurrencyCodesCollection_whenMethodInvoked_thenCurrencyCodesCollectionReturned() {
        CurrencyCodesCollection currencyCodesCollection = rateService.getCurrencyCodesCollection();

        assertSame(expectedCurrencyCodesCollection, currencyCodesCollection);
    }

    @Test
    void whenCurrencyCodeNotFound_thenThrowsException() {

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> rateService.checkRate(""));
        assertEquals("Currency code not found", exception.getMessage());
    }


    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("getArguments")
    void hasCurrencyIncreased(String name, ExchangeRates latest, ExchangeRates historical, String result) {
        when(rateClient.getCurrentRate(openExchangeRateApiId)).thenReturn(latest);
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        when(rateClient.getYesterdayRate(yesterdayDate, openExchangeRateApiId)).thenReturn(historical);

        assertEquals(result, rateService.checkRate(CURRENCY_CODE));
    }

    private Stream<Arguments> getArguments() {

        return Stream.of(
                Arguments.of(
                        "whenYesterdayHigherThanLatestRates_thenReturns_ZeroTag",
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,2.0d )).build(),
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 2.0d,base,2.0d )).build(),
                        brokeTag
                ),
                Arguments.of(
                        "whenYesterdayAndLatestRatesEqual_thenReturns_ZeroTag",
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,2.0d )).build(),
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,2.0d )).build(),
                        zeroTag
                ),
                Arguments.of(
                        "whenLatestRatesHigherThanYesterday_thenReturns_RichTag",
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 2.0d,base,2.0d )).build(),
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,2.0d )).build(),
                        richTag),

                Arguments.of(
                        "whenYesterdayAndLatestRatesEqual_AndBaseOfYesterdayIsDec_thenReturns_brokeTag",
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,2.0d )).build(),
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,1.0d )).build(),
                        brokeTag),
                Arguments.of(
                        "whenYesterdayAndLatestRatesEqual_AndBaseOfLatestRatesIsDec_thenReturns_richTag",
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,1.0d )).build(),
                        ExchangeRates.builder().rates(Map.of(CURRENCY_CODE, 1.0d,base,2.0d )).build(),
                        richTag)
        );



    }
}