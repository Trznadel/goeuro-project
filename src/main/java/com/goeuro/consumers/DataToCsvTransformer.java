package com.goeuro.consumers;

import com.google.inject.Inject;
import com.goeuro.services.CityDataExtractor;
import com.goeuro.services.location.LocationsWriter;
import org.apache.log4j.Logger;

public class DataToCsvTransformer implements DataTransformer {

    private static final Logger LOGGER = Logger.getLogger(DataToCsvTransformer.class);
    private LocationsWriter writer;
    private CityDataExtractor cityExtractor;

    @Inject
    public DataToCsvTransformer(LocationsWriter writer, CityDataExtractor cityExtractor) {
        this.writer = writer;
        this.cityExtractor = cityExtractor;
    }

    public void processData(String name) {
        String json = cityExtractor.getCityData(name);
        writer.writeLocations(json);
        LOGGER.info(name + " city data has been extracted to file: " + writer.getPath());
    }

}
