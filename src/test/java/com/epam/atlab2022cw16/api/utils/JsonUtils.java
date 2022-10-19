package com.epam.atlab2022cw16.api.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import static com.epam.atlab2022cw16.api.utils.DateUtils.getISO8601CurrentDate;

public class JsonUtils {

    public static void assertEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.NON_EXTENSIBLE);
    }

    public static void assertEquals(String expected, String actual, Customization... customs) throws JSONException {
        JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.NON_EXTENSIBLE, customs));
    }

    public static Customization getGenerationTimerCustomization() {
        return createCustomizationForDouble("generationtime_ms");
    }

    public static Customization getCurrentWeatherTemperatureCustomization() {
        return createCustomizationForDouble("current_weather.temperature");
    }

    public static Customization getCurrentWeatherWindSpeedCustomization() {
        return createCustomizationForDouble("current_weather.windspeed");
    }

    public static Customization getCurrentWeatherWindDirectionCustomization() {
        return createCustomizationForDouble("current_weather.winddirection");
    }

    public static Customization getCurrentWeatherWeatherCodeCustomization() {
        return createRegexCustomization("current_weather.weathercode",
                "[0|1|2|3|45|48|51|53|55|56|57|61|63|65|66|67|71|73|75|77|80|81|82|85|86|95|96|99]");
    }

    public static Customization getCurrentWeatherTimeCustomization() {
        return createRegexCustomization("current_weather.time",
                getISO8601CurrentDate() + "T[0-2][0-9]:00");
    }

    public static Customization getReasonEndDateOutOfBoundCustomization() {
        return createRegexCustomization("reason",
                "Parameter 'end_date' is out of allowed range " +
                        "from \\d{4}-\\d{2}-\\d{2} to \\d{4}-\\d{2}-\\d{2}");
    }

    public static Customization getStarDateIsOutOfRangeErrorCustomization() {
        return createRegexCustomization("reason",
                "Parameter 'start_date' is out of allowed range " +
                        "from [19|20]+[0-9][0-9]-[0-1][0-9]-[0-3][0-9] " +
                        "to [19|20]+[0-9][0-9]-[0-1][0-9]-[0-3][0-9]");
    }

    public static Customization getArrayDailyTimeSizeCustomization() {
        return getArrayCustomizationForSimpleDate("daily.time");
    }

    public static Customization getArrayDailyWeathercodeSizeCustomization() {
        return getArrayCustomizationForWeatherCodes("daily.weathercode");
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

    public static Customization getArrayHourlyTimeSizeCustomization() {
        return getArrayCustomizationForDateWithTime("hourly.time");
    }

    public static Customization getArrayDailyTemperature2mSizeCustomization() {
        return getArrayCustomizationForDouble("hourly.temperature_2m");
    }

    public static Customization getArrayHourlySurfaceAirPressureCustomization() {
        return getArrayCustomizationForDouble("hourly.surface_air_pressure");
    }

    public static Customization getCustomizationForLatitudeOutOfRange() {
        return createRegexCustomization("reason",
                "Latitude must be in range of -90 to 90°. Given: [+-]?[0-9]*[.]?[0-9]*.");
    }

    public static Customization getCustomizationForInvalidInitializationOfVariable() {
        return createRegexCustomization("reason",
                "Cannot initialize \\w+ from invalid String value \\w+ for key \\w+");
    }

    private static Customization createRegexCustomization(String path, String pattern) {
        return new Customization(path, new RegularExpressionValueMatcher<>(pattern));
    }

    private static Customization createCustomizationForDouble(String path) {
        return new Customization(path, new RegularExpressionValueMatcher<>("[+-]?[0-9]*[.][0-9]+|null"));

    }

    private static Customization getArrayCustomizationForWeatherCodes(String path) {
        return new Customization(path, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches("\\[((0|1|2|3|45|48|51|53|55|56|57|61|63|65|66|67|71|73|75|" +
                            "77|80|81|82|85|86|95|96|99),?)+]");
        });
    }

    private static Customization getArrayCustomizationForDouble(String jsonPath) {
        return new Customization(jsonPath, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches("\\[([+-]?[0-9]*[.]?[0-9],?)+]");
        });
    }

    private static Customization getArrayCustomizationForSimpleDate(String jsonPath) {
        return new Customization(jsonPath, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches("\\[(\"20(21|22)-[0-1][0-9]-[0-3][0-9]\",?)+]");
        });
    }

    private static Customization getArrayCustomizationForDateWithTime(String jsonPath) {
        return new Customization(jsonPath, (actual1, expected1) -> {
            JSONArray actualValue = (JSONArray) actual1;
            JSONArray expectedValue = (JSONArray) expected1;
            return (actualValue.length() == expectedValue.length()) &&
                    actualValue.toString().matches(
                            "\\[(\"20(21|22)-[0-1][0-9]-[0-3][0-9]T[0-2][0-9]:[0-6][0-9]\",?)+]");
        });
    }

}
