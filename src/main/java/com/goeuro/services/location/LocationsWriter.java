package com.goeuro.services.location;

import java.nio.file.Path;

public interface LocationsWriter {

    void writeLocations(String data);
    Path getPath();
}
