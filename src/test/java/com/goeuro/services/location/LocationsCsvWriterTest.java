package com.goeuro.services.location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class LocationsCsvWriterTest {

    private static final String testOneObjectJsonArray = "[{\"_id\":376217,\"name\":\"Berlin\",\"type\":\"location\"," + "\"geo_position\":{\"latitude\":52.52437,\"longitude\":13.41053}}]";
    private static final String testMultipleObjectsJsonArray1 = "[{\"_id\":451301,\"key\":null,\"name\":\"Pilzno\",\"fullName\":\"Pilzno, Poland\"," +
            "\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Poland\",\"geo_position\":{\"latitude\":49.97883,\"longitude\":21.29228}," +
            "\"locationId\":151011,\"inEurope\":true,\"countryId\":173,\"countryCode\":\"PL\",\"coreCountry\":true,\"distance\":null,\"names\":{}," +
            "\"alternativeNames\":{}}]";
    private static final String testMultipleObjectsJsonArray2 = "[{\"_id\":376627,\"key\":null,\"name\":\"Herne\",\"fullName\":\"Herne, Germany\"," +
            "\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.5388,\"longitude\":7.22572}," +
            "\"locationId\":8796,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null," +
            "\"names\":{\"zh\":\"黑尔讷\",\"ru\":\"Херне\"},\"alternativeNames\":{}},{\"_id\":380426,\"key\":null,\"name\":\"Herne Bay\"," +
            "\"fullName\":\"Herne Bay, United Kingdom\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"United Kingdom\"," +
            "\"geo_position\":{\"latitude\":51.373,\"longitude\":1.12857},\"locationId\":12615,\"inEurope\":true,\"countryId\":75," +
            "\"countryCode\":\"GB\",\"coreCountry\":true,\"distance\":null,\"names\":{},\"alternativeNames\":{}},{\"_id\":369351," +
            "\"key\":null,\"name\":\"Herne\",\"fullName\":\"Herne, Belgium\",\"iata_airport_code\":null,\"type\":\"location\"," +
            "\"country\":\"Belgium\",\"geo_position\":{\"latitude\":50.72423,\"longitude\":4.03481},\"locationId\":1510,\"inEurope\":true," +
            "\"countryId\":22,\"countryCode\":\"BE\",\"coreCountry\":true,\"distance\":null,\"names\":{},\"alternativeNames\":{}}]";
    private static final String LOCATIONS_CSV_TEST = "target\\locationsTest.csv";
    private LocationsCsvWriter locationsCsvWriter;


    @Before
    public void setup() {
        locationsCsvWriter = new LocationsCsvWriter(new File(LOCATIONS_CSV_TEST));
    }

    @After
    public void cleanup() throws IOException {
        Path fileToDeletePath = Paths.get(LOCATIONS_CSV_TEST);
        if (Files.exists(fileToDeletePath)) {
            Files.delete(fileToDeletePath);
        }
    }

    @Test
    public void writeOneObjectJsonArrayToCsvFile() throws IOException {
        locationsCsvWriter.writeLocations(testOneObjectJsonArray);

        String csvContent = extractCsvFileContent();

        assertThat(csvContent, is("_id,name,type,latitude,longitude" + "376217,Berlin,location,52.52437,13.41053"));
    }

    @Test
    public void writeMultipleObjectsJsonArrayToCsvFile() throws IOException {
        locationsCsvWriter.writeLocations(testMultipleObjectsJsonArray2);

        String csvContent = extractCsvFileContent();

        assertThat(csvContent, is("_id,name,type,latitude,longitude376627,Herne,location,51.5388,7.22572380426,Herne Bay," +
                "location,51.373,1.12857369351,Herne,location,50.72423,4.03481"));
    }

    @Test
    public void writeTwoJsonArraysWithMultipleObjectsToSameCsvFile() throws IOException {
        locationsCsvWriter.writeLocations(testMultipleObjectsJsonArray1);
        locationsCsvWriter.writeLocations(testMultipleObjectsJsonArray2);

        String csvContent = extractCsvFileContent();

        assertThat(csvContent, is("_id,name,type,latitude,longitude451301,Pilzno,location,49.97883,21.29228_id,name,type," +
                "latitude,longitude376627,Herne,location,51.5388,7.22572380426,Herne Bay,location,51.373,1.12857369351," +
                "Herne,location,50.72423,4.03481"));
    }

    @Test
    public void writeOnlyHeaderWhenJsonObjectToCsvFile() throws IOException {
        String jsonObject = testOneObjectJsonArray.replace("[", "").replace("]", "");
        locationsCsvWriter.writeLocations(jsonObject);

        String csvContent = extractCsvFileContent();

        assertThat(csvContent, is("_id,name,type,latitude,longitude"));
    }

    @Test
    public void writeOnlyHeaderWhenInvalidJsonToCsvFile() throws IOException {
        locationsCsvWriter.writeLocations("invalidJson");

        String csvContent = extractCsvFileContent();

        assertThat(csvContent, is("_id,name,type,latitude,longitude"));
    }

    @Test
    public void writeOnlyHeaderWhenEmptyJson() throws IOException {
        locationsCsvWriter.writeLocations("");

        String csvContent = extractCsvFileContent();

        assertThat(csvContent, is("_id,name,type,latitude,longitude"));
    }

    private String extractCsvFileContent() throws IOException {
        String content = new String(Files.readAllBytes(locationsCsvWriter.getPath()));
        return content.replace("\n", "").replace("\r", "");
    }

}