package com.goeuro.services;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonArrayCityExtractorTestIT {

    private static final String SERVICE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
    private CityDataExtractor cityDataExtractor;

    @Before
    public void setup() {
        cityDataExtractor = new JsonArrayCityExtractor(SERVICE_URL);
    }

    @Test(timeout = 3000)
    public void getDataForCityWithNoSpecialChars() {
        String result = cityDataExtractor.getCityData("Pilzno");

        assertThat(result, is("[{\"_id\":451301,\"key\":null,\"name\":\"Pilzno\",\"fullName\":\"Pilzno, Poland\"," +
                "\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Poland\",\"geo_position\":{\"latitude\":49.97883," +
                "\"longitude\":21.29228},\"locationId\":151011,\"inEurope\":true,\"countryId\":173,\"countryCode\":\"PL\"," +
                "\"coreCountry\":true,\"distance\":null,\"names\":{},\"alternativeNames\":{}}]"));
    }

    @Test(timeout = 3000)
    public void getDataForCityWithSpecialChars() throws Exception {
        String result = cityDataExtractor.getCityData("Brno");

        assertThat(result, is("[{\"_id\":375495,\"key\":null,\"name\":\"Brno\",\"fullName\":\"Brno, Czech Republic\"," +
                "\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Czech Republic\"," +
                "\"geo_position\":{\"latitude\":49.19510269165039,\"longitude\":16.60753059387207},\"locationId\":7659," +
                "\"inEurope\":true,\"countryId\":55,\"countryCode\":\"CZ\",\"coreCountry\":true,\"distance\":null," +
                "\"names\":{\"de\":\"Brünn\",\"zh\":\"布尔诺\",\"ru\":\"Брно\"},\"alternativeNames\":{}},{\"_id\":313894," +
                "\"key\":null,\"name\":\"Brno\",\"fullName\":\"Brno (BRQ), Czech Republic\",\"iata_airport_code\":\"BRQ\"," +
                "\"type\":\"airport\",\"country\":\"Czech Republic\",\"geo_position\":{\"latitude\":49.14516,\"longitude\":16.6944}," +
                "\"locationId\":null,\"inEurope\":true,\"countryId\":55,\"countryCode\":\"CZ\",\"coreCountry\":true,\"distance\":null," +
                "\"names\":{\"de\":\"Brünn\"},\"alternativeNames\":{}}]"));
    }

    @Test(timeout = 3000)
    public void getDataWhenEmptyCityName() {
        String result = cityDataExtractor.getCityData("");

        assertThat(result, is("{\"message\":\"400: Bad Request\",\"errorClass\":\"TypeMismatchException\"," +
                "\"description\":\"Could not mold value to type Long\",\"items\":null}"));
    }

    @Test(timeout = 3000)
    public void getDataWhenInvalidCityName() {
        String result = cityDataExtractor.getCityData("invalidCityName");

        assertThat(result, is("[]"));
    }
}