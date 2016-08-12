package com.goeuro.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class CsvLocationFinder {

    private static final Logger LOGGER = Logger.getLogger(CsvLocationFinder.class);
    private static final String CSV_PATH_PROPERTY = "csvPath";
    public  static final String DEFAULT_CSV_PATH = "./report/locations.csv";

    private CsvLocationFinder(){}

    public static Path findCsvFilePath(String propertiesFile) {
        Path filePath = findCsvFilePathInResources(propertiesFile);
        if (!Files.exists(filePath)) {
            createFile(filePath);
        }
        return filePath;
    }

    private static void createFile(Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        } catch (IOException e) {
            LOGGER.error("Exception occurred during file object creation: " + filePath);
            LOGGER.debug(e);
            throw new RuntimeException("Impossible to create new file object");
        }
    }

    private static Path findCsvFilePathInResources(String propertiesFile) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream(propertiesFile)) {
            return extractCsvPathFromResources(is);
        } catch (IOException e) {
            LOGGER.error("Exception occurred during extracting csv file path from properties " +
                    "\n Default csv file location has been used: " + DEFAULT_CSV_PATH);
            LOGGER.debug(e);
            return Paths.get(DEFAULT_CSV_PATH);
        }
    }

    private static Path extractCsvPathFromResources(InputStream is) throws IOException {
        if (is == null) {
            LOGGER.error("Selected properties file has not been found. " +
                    "\n Default csv file location has been used: " + DEFAULT_CSV_PATH);
            return Paths.get(DEFAULT_CSV_PATH);
        } else {
            return extractPathFromPropertiesFile(is);
        }
    }

    private static Path extractPathFromPropertiesFile(InputStream is) throws IOException {
        String path = getCsvPathPropertyValue(is);
        if (path == null) {
            LOGGER.error("Property " + CSV_PATH_PROPERTY + " has not been found in selected properties file" +
                    "\n Default csv file location has been used: " + DEFAULT_CSV_PATH);
            return Paths.get(DEFAULT_CSV_PATH);
        } else {
            return Paths.get(path);
        }
    }

    private static String getCsvPathPropertyValue(InputStream is) throws IOException {
        Properties properties = new Properties();
        properties.load(is);
        return properties.getProperty(CSV_PATH_PROPERTY);
    }
}
