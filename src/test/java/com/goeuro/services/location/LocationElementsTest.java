package com.goeuro.services.location;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class LocationElementsTest {

    @Test
    public void checkLocationParamsNames() {
        assertThat(LocationElements.ID, is("_id"));
        assertThat(LocationElements.NAME, is("name"));
        assertThat(LocationElements.TYPE, is("type"));
        assertThat(LocationElements.LATITUDE, is("latitude"));
        assertThat(LocationElements.LONGITUDE, is("longitude"));
        assertThat(LocationElements.GEO_POSITION, is("geo_position"));
    }
}