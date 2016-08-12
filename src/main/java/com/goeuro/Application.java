package com.goeuro;

import com.goeuro.configuration.JsonArrayToCsvModule;
import com.goeuro.consumers.DataToCsvTransformer;
import com.goeuro.consumers.DataTransformer;
import com.goeuro.utils.CityNameParser;
import com.goeuro.utils.CsvLocationFinder;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.nio.file.Path;

public class Application {

    private static final String SERVICE_PATH = "http://api.goeuro.com/api/v2/position/suggest/en/";

    public static void main(String ...args) {
        Path filePath = CsvLocationFinder.findCsvFilePath("config.properties");
        Injector injector = Guice.createInjector(new JsonArrayToCsvModule(SERVICE_PATH, filePath.toFile()));
        DataTransformer dataTransformer = injector.getInstance(DataToCsvTransformer.class);
        new CityNameParser().parseNames(args).forEach(dataTransformer::processData);
    }
}
