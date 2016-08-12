package com.goeuro.utils;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.goeuro.services.location.LocationElements.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CityLocationsExtractorTest {

    @Test
    public void extractDataFromEmptyString() {
        List<Map<String, Object>> result = CityLocationsExtractor.generateLocationsFromJsonArray("");

        assertTrue(result.isEmpty());
    }

    @Test
    public void extractDataFromEmptyJsonArray() {
        List<Map<String, Object>> result = CityLocationsExtractor.generateLocationsFromJsonArray("[]");

        assertTrue(result.isEmpty());
    }

    @Test
    public void extractDataFromInvalidInput() {
        String invalidInput = "invalidInput";

        List<Map<String, Object>> result = CityLocationsExtractor.generateLocationsFromJsonArray(invalidInput);

        assertTrue(result.isEmpty());
    }

    @Test
    public void extractDataFromJsonObject() {
        String jsonObj = "{\"_id\":376217,\"name\":\"Berlin\",\"type\":\"location\",\"geo_position\":{\"latitude\":52.52437," +
                "\"longitude\":13.41053}}";

        List<Map<String, Object>> result = CityLocationsExtractor.generateLocationsFromJsonArray(jsonObj);

        assertTrue(result.isEmpty());
    }

    @Test
    public void extractDataFromOneElementJsonArray() {
        String jsonArr = "[{\"_id\":376217,\"name\":\"Berlin\",\"type\":\"location\",\"geo_position\":{\"latitude\":52.52437," +
                "\"longitude\":13.41053}}]";

        List<Map<String, Object>> result = CityLocationsExtractor.generateLocationsFromJsonArray(jsonArr);

        assertThat(result.size(), is(1));
        Map<String, Object> location = result.get(0);
        assertThat(location.size(), is(5));
        assertLocationParams(location, 376217, "Berlin", "location", 13.41053, 52.52437);
    }

    @Test
    public void extractDataFromTwoElementsJsonArray() {
        String jsonArr = "[{\"_id\":376217,\"key\":null,\"name\":\"Berlin\",\"fullName\":\"Berlin, Germany\"," +
                "\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\"," +
                "\"geo_position\":{\"latitude\":52.52437,\"longitude\":13.41053},\"locationId\":8384,\"inEurope\":true," +
                "\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Berlim\"," +
                "\"ru\":\"Берлин\",\"it\":\"Berlino\",\"is\":\"Berlín\",\"fi\":\"Berliini\",\"es\":\"Berlín\",\"zh\":\"柏林\"," +
                "\"cs\":\"Berlín\",\"ca\":\"Berlín\",\"nl\":\"Berlijn\"},\"alternativeNames\":{}}," +
                "{\"_id\":448103,\"key\":null,\"name\":\"Berlingo\",\"fullName\":\"Berlingo, Italy\",\"iata_airport_code\":null," +
                "\"type\":\"location\",\"country\":\"Italy\",\"geo_position\":{\"latitude\":45.50298,\"longitude\":10.04366}," +
                "\"locationId\":147721,\"inEurope\":true,\"countryId\":106,\"countryCode\":\"IT\",\"coreCountry\":true," +
                "\"distance\":null,\"names\":{},\"alternativeNames\":{}}]";

        List<Map<String, Object>> results = CityLocationsExtractor.generateLocationsFromJsonArray(jsonArr);
        sortResults(results);

        assertThat(results.size(), is(2));
        Map<String, Object> location = results.get(0);
        assertThat(location.size(), is(5));
        assertLocationParams(location, 376217, "Berlin", "location", 13.41053, 52.52437);

        location = results.get(1);
        assertThat(location.size(), is(5));
        assertLocationParams(location, 448103, "Berlingo", "location", 10.04366, 45.50298);
    }

    @Test
    public void extractDataFromJsonArrayWithMissingKeys() {
        String jsonArr = "[{\"x\":1,\"y\":2,\"z\":\"takesOneCityNameFromInputWhenNoNamesPassedToParser\"}]";

        List<Map<String, Object>> results = CityLocationsExtractor.generateLocationsFromJsonArray(jsonArr);

        assertTrue(results.isEmpty());
    }

    private void assertLocationParams(Map<String, Object> location, int id, String name, String type, double longitude, double latitude) {
        assertThat(location.get(ID), is(id));
        assertThat(location.get(NAME), is(name));
        assertThat(location.get(TYPE), is(type));
        assertThat(location.get(LONGITUDE), is(longitude));
        assertThat(location.get(LATITUDE), is(latitude));
    }

    private void sortResults(List<Map<String, Object>> results) {
        Collections.sort(results, (r1, r2) -> Integer.compare((int)r1.get(ID), (int)r2.get(ID)));
    }
}