package com.epam.atlab2022cw16.api.utils;

import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.util.ArrayList;
import java.util.List;

import static com.epam.atlab2022cw16.api.utils.DataUtils.getISO8601CurrentDate;

public class JsonUtils {

    public static CustomComparator getComparatorForGenerationTime() {
        return createCustomComparator(getGenerationTimerCustomization());
    }

    public static CustomComparator getComparatorForCurrentWeather() {
        return createCustomComparator(getCurrentWeatherCustomization());
    }

    public static Customization getGenerationTimerCustomization() {
        return createCustomizationForDouble("generationtime_ms");
    }

    public static Customization[] getCurrentWeatherCustomization() {
        List<Customization> customizations = new ArrayList<>();
        customizations.add(createCustomizationForDouble("generationtime_ms"));
        customizations.add(createCustomizationForDouble("current_weather.temperature"));
        customizations.add(createCustomizationForDouble("current_weather.windspeed"));
        customizations.add(createCustomizationForDouble("current_weather.winddirection"));
        customizations.add(createCustomizationForDouble("current_weather.weathercode"));
        customizations.add(createCustomization("current_weather.time",
                getISO8601CurrentDate() + "T[0-9]{2}:[0-9]{2}"));
        return customizations.toArray(Customization[]::new);
    }

    public static void assertEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.NON_EXTENSIBLE);
    }

    public static void assertEquals(String expected, String actual, CustomComparator customComparator) throws JSONException {
        JSONAssert.assertEquals(expected, actual, customComparator);
    }

    private static CustomComparator createCustomComparator(Customization... customizations) {
        return new CustomComparator(JSONCompareMode.NON_EXTENSIBLE, customizations);
    }

    private static Customization createCustomization(String path, String pattern) {
        return new Customization(path, new RegularExpressionValueMatcher<>(pattern));
    }

    private static Customization createCustomizationForDouble(String path) {
        return new Customization(path, new RegularExpressionValueMatcher<>("[+-]?[0-9]*[.][0-9]+"));
    }

}
