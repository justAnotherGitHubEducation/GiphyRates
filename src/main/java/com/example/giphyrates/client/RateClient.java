package com.example.giphyrates.client;

import com.example.giphyrates.model.CurrencyCodesCollection;
import com.example.giphyrates.model.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@FeignClient(name = "rateClient", url = "${openexchangerates.url}")
public interface RateClient {

    @RequestMapping(method = RequestMethod.GET,  value = "/latest.json?app_id={app_id}")
    ExchangeRates getCurrentRate(@RequestParam("app_id") String appId);


    @RequestMapping(method = RequestMethod.GET,  value = "/historical/{date}.json?app_id={app_id}")
    ExchangeRates getYesterdayRate(@PathVariable @DateTimeFormat(iso = DATE) final LocalDate date, @RequestParam("app_id") String appId);

    @RequestMapping(method = RequestMethod.GET,  value = "/currencies.json")
    CurrencyCodesCollection getCurrencyCodesCollection();

}

