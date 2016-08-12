package com.goeuro.utils;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.goeuro.utils.CsvLocationFinder.DEFAULT_CSV_PATH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CsvLocationFinderTest {

    private static final String LOCATIONS_CSV_TEST = "target\\locationsTest.csv";
    private static final String TEST_CONFIG_PROPERTIES = "testConfig.properties";

    @After
    public void cleanup() throws IOException {
        Path fileToDeletePath = Paths.get(LOCATIONS_CSV_TEST);
        if (Files.exists(fileToDeletePath)) {
            Files.delete(fileToDeletePath);
        }
    }

    @Test
    public void useDefaultCsvLocationWhenCsvPropertyNotExists() throws IOException, InterruptedException {
        Path path = CsvLocationFinder.findCsvFilePath("emptyConfig.properties");

        assertThat(path, is(Paths.get(DEFAULT_CSV_PATH)));
    }

    @Test
    public void useDefaultCsvLocationWhenConfigFileNotExists() throws IOException, InterruptedException {
        Path path = CsvLocationFinder.findCsvFilePath("nonExistingFile");

        assertThat(path, is(Paths.get(DEFAULT_CSV_PATH)));
    }

    @Test
    public void useCsvLocationFromConfigProperties() throws IOException, InterruptedException {
        Path path = CsvLocationFinder.findCsvFilePath(TEST_CONFIG_PROPERTIES);

        assertThat(path, is(Paths.get(LOCATIONS_CSV_TEST)));
    }

    @Test
    public void searchForCsvPathInTwoConfigProperties() throws IOException, InterruptedException {
        Path path = CsvLocationFinder.findCsvFilePath("nonExistingFile");
        assertThat(path, is(Paths.get(DEFAULT_CSV_PATH)));

        path = CsvLocationFinder.findCsvFilePath(TEST_CONFIG_PROPERTIES);
        assertThat(path, is(Paths.get(LOCATIONS_CSV_TEST)));
    }

}