package com.goeuro.utils;

import com.goeuro.services.location.LocationElements;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public final class CityLocationsExtractor {

    private static final Logger LOGGER = Logger.getLogger(CityLocationsExtractor.class);

    private CityLocationsExtractor(){}

    public static List<Map<String, Object>> generateLocationsFromJsonArray(String json) {
        try {
            return extractDataFromJsonArray(json);
        } catch (JSONException e) {
            LOGGER.error("Exception occurred during extracting locations from JSON.");
            LOGGER.debug(e);
            return Collections.emptyList();
        }
    }

    private static List<Map<String, Object>> extractDataFromJsonArray(String json) {
        List<Map<String, Object>> locations = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            locations.add(createLocationElement(jsonObj));
        }
        return locations;
    }

    private static Map<String, Object> createLocationElement(JSONObject jsonObj) {
        Map<String, Object> location = new HashMap<>();
        location.put(LocationElements.ID, jsonObj.getInt(LocationElements.ID));
        location.put(LocationElements.NAME, jsonObj.getString(LocationElements.NAME));
        location.put(LocationElements.TYPE, jsonObj.getString(LocationElements.TYPE));
        location.put(LocationElements.LATITUDE, getGeoPosition(jsonObj, LocationElements.LATITUDE));
        location.put(LocationElements.LONGITUDE, getGeoPosition(jsonObj, LocationElements.LONGITUDE));
        return location;
    }

    private static double getGeoPosition(JSONObject jsonObj, String coordinateType) {
        JSONObject geoPosition = jsonObj.getJSONObject(LocationElements.GEO_POSITION);
        return geoPosition.getDouble(coordinateType);
    }

}
