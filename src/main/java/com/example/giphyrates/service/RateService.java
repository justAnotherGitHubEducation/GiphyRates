package com.example.giphyrates.service;


import com.example.giphyrates.model.CurrencyCodesCollection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RateService {

     String checkRate(String rate) ;
     CurrencyCodesCollection getCurrencyCodesCollection();
}
