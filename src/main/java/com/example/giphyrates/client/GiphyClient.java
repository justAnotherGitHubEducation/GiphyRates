package com.example.giphyrates.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@FeignClient(name = "giphyClient", url = "${giphy.url}")
public interface GiphyClient {

    @GetMapping("/random")
    ResponseEntity<Map> getGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}
