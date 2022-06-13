package com.example.giphyrates.service.impl;

import com.example.giphyrates.client.GiphyClient;
import com.example.giphyrates.service.GifService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Сервис для работы с Giphy.com
 */

@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {

    private final GiphyClient giphyClient;

    @Value("${giphyapi.id}")
    private String giphyApiId;

    @Override
    public byte[] getGif(String tag) {

        Map response = null;
        response = giphyClient.getGif(giphyApiId,tag).getBody();

        Map<String, String> gifInfo = (Map<String, String>) response.get("data");
        byte[] result =  getGIFFrom(gifInfo.get("id"));
        return result;
    }

    public byte[] getGIFFrom(String gifId) {

        String gifURL = "https://i.giphy.com/" + gifId + ".gif";
        try (InputStream inputStream = new BufferedInputStream((new URL(gifURL)).openStream())) {
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
