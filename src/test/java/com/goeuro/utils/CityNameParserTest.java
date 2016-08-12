package com.goeuro.utils;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CityNameParserTest {

    private CityNameParser parser;

    @Test
    public void takeOneCityNameFromInputStreamWhenNoNamesPassedToParser() {
        parser = new CityNameParser(new ByteArrayInputStream("city1".getBytes()));

        List<String> names = parser.parseNames(new String[0]);

        assertThat(names.size(), is(1));
        assertThat(names.get(0), is("city1"));
    }

    @Test
    public void takeThreeCityNameFromInputStreamWhenNoNamesPassedToParser() {
        parser = new CityNameParser(new ByteArrayInputStream("city1 city2 city3".getBytes()));

        List<String> names = parser.parseNames(new String[0]);
        names.sort(String::compareTo);

        assertThat(names.size(), is(3));
        assertThat(names.get(0), is("city1"));
        assertThat(names.get(1), is("city2"));
        assertThat(names.get(2), is("city3"));
    }

    @Test
    public void parseOneCityNameWithWhiteSpaces() {
        parser = new CityNameParser();

        List<String> names = parser.parseNames(new String[]{" city1 "});
        names.sort(String::compareTo);

        assertThat(names.size(), is(1));
        assertThat(names.get(0), is("city1"));
    }

    @Test
    public void parseThreeCityNameWithWhiteSpaces() {
        parser = new CityNameParser();

        List<String> names = parser.parseNames(new String[]{" city1 ", " city2 ", " city3 "});
        names.sort(String::compareTo);

        assertThat(names.size(), is(3));
        assertThat(names.get(0), is("city1"));
        assertThat(names.get(1), is("city2"));
        assertThat(names.get(2), is("city3"));
    }
}