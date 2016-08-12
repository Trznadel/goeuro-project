package com.goeuro.services.location;

import com.goeuro.utils.CityLocationsExtractor;
import org.apache.log4j.Logger;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

public class LocationsCsvWriter implements LocationsWriter {

    private static Logger LOGGER = Logger.getLogger(LocationsCsvWriter.class);
    private final File destinationFile;

    public LocationsCsvWriter(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    @Override
    public void writeLocations(String data) {
        try (OutputStream outStream = new FileOutputStream(destinationFile, true);
             Writer writer = new OutputStreamWriter(outStream, StandardCharsets.UTF_8.displayName());
             BufferedWriter buffWriter = new BufferedWriter(writer);
             ICsvMapWriter csvWriter = new CsvMapWriter(buffWriter, CsvPreference.STANDARD_PREFERENCE)) {

            writeDataIntoCsvFile(data, csvWriter);
        } catch (IOException e) {
            LOGGER.error("Exception occurred during writing data into csv file.");
            LOGGER.debug(e);
        }
    }

    private void writeDataIntoCsvFile(String json, ICsvMapWriter csvWriter) throws IOException {
        csvWriter.writeHeader(LocationElements.ID, LocationElements.NAME, LocationElements.TYPE, LocationElements.LATITUDE, LocationElements.LONGITUDE);
        for (Map<String, Object> location : CityLocationsExtractor.generateLocationsFromJsonArray(json)) {
            csvWriter.write(location, LocationElements.ID, LocationElements.NAME, LocationElements.TYPE, LocationElements.LATITUDE, LocationElements.LONGITUDE);
        }
        csvWriter.writeHeader("");
    }

    @Override
    public Path getPath() {
        return destinationFile.toPath();
    }

}
