package com.goeuro.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonArrayCityExtractor implements CityDataExtractor {
    private static final Logger LOGGER = Logger.getLogger(JsonArrayCityExtractor.class);
    private static final String EMPTY_JSON_ARRAY = "[]";
    private String url;

    public JsonArrayCityExtractor(String url) {
        this.url = url;
    }

    @Override
    public String getCityData(String cityName) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url + cityName);
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Exception occurred during getting data from service.");
            LOGGER.debug(e);
            return EMPTY_JSON_ARRAY;
        }
    }

}
