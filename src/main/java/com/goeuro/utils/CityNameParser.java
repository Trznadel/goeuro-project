package com.goeuro.utils;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class CityNameParser {

    private static final Logger LOGGER = Logger.getLogger(CityNameParser.class);
    private Scanner scanner;

    public CityNameParser() {
        this(System.in);
    }

    public CityNameParser(InputStream is) {
        this.scanner = new Scanner(is);
    }

    public List<String> parseNames(String[] args){
        List<String> cityNames = removeEmptyElements(args);
        while (cityNames.isEmpty()) {
            cityNames = askForCitiesNames(cityNames);
        }
        return cityNames;
    }

    private List<String> askForCitiesNames(List<String> cityNames) {
        LOGGER.info("Please enter cities names: ");
        String cities = scanner.nextLine();
        if(cities != null ) {
            cityNames = removeEmptyElements(cities.split(" "));
        }
        return cityNames;
    }

    private List<String> removeEmptyElements(String[] args) {
        List<String> cityNames = new ArrayList<>(Arrays.asList(args));
        cityNames.removeAll(Collections.singletonList(""));
        return cityNames.stream().map(String::trim).collect(Collectors.toList());
    }

}
