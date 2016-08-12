package com.goeuro.consumers;

import com.goeuro.services.JsonArrayCityExtractor;
import com.goeuro.services.location.LocationsCsvWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataToCsvTransformerTest {

    @Mock private LocationsCsvWriter writerMock;
    @Mock private JsonArrayCityExtractor cityExtMock;
    private DataToCsvTransformer dataTransformer;

    @Before
    public void setup() {
        dataTransformer = new DataToCsvTransformer(writerMock, cityExtMock);
    }

    @Test
    public void writeDataToFileForEmptyCityName() {
        when(cityExtMock.getCityData("")).thenReturn("[]");

        dataTransformer.processData("");

        verify(cityExtMock).getCityData("");
        verify(writerMock).writeLocations("[]");
    }

    @Test
    public void writeDataToFileForOneCity() {
        when(cityExtMock.getCityData("city1")).thenReturn("json1");

        dataTransformer.processData("city1");

        verify(cityExtMock).getCityData("city1");
        verify(writerMock).writeLocations("json1");
    }

    @Test
    public void writeDataToFileForThreeCities() {
        when(cityExtMock.getCityData("city1")).thenReturn("json1");
        when(cityExtMock.getCityData("city2")).thenReturn("json2");
        when(cityExtMock.getCityData("city3")).thenReturn("json3");

        dataTransformer.processData("city1");
        dataTransformer.processData("city2");
        dataTransformer.processData("city3");

        verify(cityExtMock).getCityData("city1");
        verify(cityExtMock).getCityData("city2");
        verify(cityExtMock).getCityData("city3");
        verify(writerMock).writeLocations("json1");
        verify(writerMock).writeLocations("json2");
        verify(writerMock).writeLocations("json3");
    }

}