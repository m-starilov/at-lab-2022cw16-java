package com.epam.atlab2022cw16.api.utils;

import org.json.JSONArray;
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

    public static CustomComparator getComparatorForLatitudeOutOfRange() {
        return createCustomComparator(getCustomizationForLatitudeOutOfRange());
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

    public static Customization getArrayDailyTimeSizeCustomization() {
        return getArrayCustomizationForSimpleDate("daily.time");
    }

    public static Customization getArrayDailyWeathercodeSizeCustomization() {
        return getArrayCustomizationForDouble("daily.weathercode");
    }

    public static Customization getArrayDailyTemperature2mMaxSizeCustomization() {
        return getArrayCustomizationForDouble("daily.temperature_2m_max");
    }

    public static Customization getArrayDailyTemperature2mMinSizeCustomization() {
        return getArrayCustomizationForDouble("daily.temperature_2m_min");
    }

    public static Customization getArrayDailyApparentTemperatureMaxSizeCustomization() {
        return getArrayCustomizationForDouble("daily.apparent_temperature_max");
    }

    public static Customization getArrayDailyApparentTemperatureMinSizeCustomization() {
        return getArrayCustomizationForDouble("daily.apparent_temperature_min");
    }

    public static Customization getArrayDailySunriseSizeCustomization() {
        return getArrayCustomizationForDateWithTime("daily.sunrise");
    }

    public static Customization getArrayDailySunsetSizeCustomization() {
        return getArrayCustomizationForDateWithTime("daily.sunset");
    }

    public static Customization getArrayDailyPrecipitationSumSizeCustomization() {
        return getArrayCustomizationForDouble("daily.precipitation_sum");
    }

    public static Customization getArrayDailyRainSumSizeCustomization() {
        return getArrayCustomizationForDouble("daily.rain_sum");
    }

    public static Customization getArrayDailyShowersSumSizeCustomization() {
        return getArrayCustomizationForDouble("daily.showers_sum");
    }

    public static Customization getArrayDailySnowfallSumSizeCustomization() {
        return getArrayCustomizationForDouble("daily.snowfall_sum");
    }

    public static Customization getArrayDailyPrecipitationHoursSizeCustomization() {
        return getArrayCustomizationForDouble("daily.precipitation_hours");
    }

    public static Customization getArrayDailyWindspeed10mMaxSizeCustomization() {
        return getArrayCustomizationForDouble("daily.windspeed_10m_max");
    }

    public static Customization getArrayDailyWindgusts10mMaxSizeCustomization() {
        return getArrayCustomizationForDouble("daily.windgusts_10m_max");
    }

    public static Customization getArrayDailyWinddirection10mDominantSizeCustomization() {
        return getArrayCustomizationForDouble("daily.winddirection_10m_dominant");
    }

    public static Customization getArrayDailyShortwaveRadiationSumSizeCustomization() {
        return getArrayCustomizationForDouble("daily.shortwave_radiation_sum");
    }

    public static Customization getArrayDailyEt0FaoEvapotranspirationSizeCustomization() {
        return getArrayCustomizationForDouble("daily.et0_fao_evapotranspiration");
    }

    public static Customization getCustomizationForLatitudeOutOfRange() {
        return createCustomization("reason", "Latitude must be in range of -90 to 90°. Given: [+-]?[0-9]*[.]?[0-9]*.");
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

    private static Customization getArrayCustomizationForDouble(String jsonPath) {
        return  new Customization(jsonPath, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches("\\[([+-]?[0-9]*[.]?[0-9]+,?)+\\]");
        });
    }

    private static Customization getArrayCustomizationForSimpleDate(String jsonPath) {
        return  new Customization(jsonPath, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches("\\[(\"20(21|22)-[0-1][0-9]-[0-3][0-9]\",?)+\\]");
        });
    }

    private static Customization getArrayCustomizationForDateWithTime(String jsonPath) {
        return  new Customization(jsonPath, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches("\\[(\"20(21|22)-[0-1][0-9]-[0-3][0-9]T[0-2][0-9]:[0-6][0-9]\",?)+\\]");
        });
    }
}
