package com.goeuro.configuration;

import com.google.inject.AbstractModule;
import com.goeuro.services.CityDataExtractor;
import com.goeuro.services.JsonArrayCityExtractor;
import com.goeuro.services.location.LocationsCsvWriter;
import com.goeuro.services.location.LocationsWriter;

import java.io.File;

public class JsonArrayToCsvModule extends AbstractModule {

    private String url;
    private File path;

    public JsonArrayToCsvModule(String url, File file) {
        this.url = url;
        this.path = file;
    }

    @Override
    protected void configure() {
        binder().bind(LocationsWriter.class).toInstance(new LocationsCsvWriter(path));
        binder().bind(CityDataExtractor.class).toInstance(new JsonArrayCityExtractor(url));
    }
}
