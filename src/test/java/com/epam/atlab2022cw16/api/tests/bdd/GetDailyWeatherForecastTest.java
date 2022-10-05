package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.DataUtils.getStringFromFile;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16340,
        description = "Test API (https://api.open-meteo.com/v1/forecast) with \"&daily=\" parameter and " +
                "its values in happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16340")
public class GetDailyWeatherForecastTest {
    private RequestSpecification requestSpec;

    @BeforeEach
    void createSpec() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://api.open-meteo.com")
                .setBasePath("v1/forecast")
                .setContentType(ContentType.JSON)
                .addParam("latitude", 41.64f)
                .addParam("longitude", 41.64f)
                .build());
    }

    @Test
    public void requestWithTimezoneParameter() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecastDailyOneValue200Response.json");
        Response response = requestSpec
            .given()
                .param("timezone", "Asia/Tbilisi")
                .param("daily", "temperature_2m_max")
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(), new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                JsonUtils.getGenerationTimerCustomization(),
                JsonUtils.getArrayDailyTimeSizeCustomization(),
                JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization()
        ));
    }

    @Test
    public void requestWithTimezoneStartAndEndDatesParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/weatherForecastDailyOneValueOneDay200Response.json");
        Response response = requestSpec
            .given()
                .param("daily", "weathercode")
                .param("timezone", "Asia/Tbilisi")
                .param("start_date", "2022-09-01")
                .param("end_date", "2022-09-01")
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization()));
    }

    @Test
    public void requestWithAllParamsAndPastDaysAndAllDailyValues() throws JSONException, IOException {
        String expected = getStringFromFile("/json/weatherForecastDailyPastDaysAllParamsAndAllValues200Response.json");
        Response response = requestSpec
            .given()
                .param("timezone", "Asia/Tbilisi")
                .param("temperature_unit", "fahrenheit")
                .param("windspeed_unit", "ms")
                .param("precipitation_unit", "inch")
                .param("past_days", 2)
                .param("daily", "weathercode",
                        "temperature_2m_max",
                        "temperature_2m_min",
                        "apparent_temperature_max",
                        "apparent_temperature_min",
                        "sunrise",
                        "sunset",
                        "precipitation_sum",
                        "rain_sum",
                        "showers_sum",
                        "snowfall_sum",
                        "precipitation_hours",
                        "windspeed_10m_max",
                        "windgusts_10m_max",
                        "winddirection_10m_dominant",
                        "shortwave_radiation_sum",
                        "et0_fao_evapotranspiration")
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyWeathercodeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMinSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMaxSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMinSizeCustomization(),
                        JsonUtils.getArrayDailySunriseSizeCustomization(),
                        JsonUtils.getArrayDailySunsetSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationSumSizeCustomization(),
                        JsonUtils.getArrayDailyRainSumSizeCustomization(),
                        JsonUtils.getArrayDailyShowersSumSizeCustomization(),
                        JsonUtils.getArrayDailySnowfallSumSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationHoursSizeCustomization(),
                        JsonUtils.getArrayDailyWindspeed10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWindgusts10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWinddirection10mDominantSizeCustomization(),
                        JsonUtils.getArrayDailyShortwaveRadiationSumSizeCustomization(),
                        JsonUtils.getArrayDailyEt0FaoEvapotranspirationSizeCustomization()
                ));
    }

    @Test
    public void requestWithAllParamsAndStartDateAndEndDateAndAllDailyParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/weatherForecastDailyStartDateEndDateAllParamsAndAllValues200Response.json");
        Response response = requestSpec
            .given()
                .param("timezone", "Asia/Tbilisi")
                .param("temperature_unit", "fahrenheit")
                .param("windspeed_unit", "ms")
                .param("precipitation_unit", "inch")
                .param("start_date", "2022-09-01")
                .param("end_date", "2022-09-01")
                .param("daily", "weathercode",
                        "temperature_2m_max",
                        "temperature_2m_min",
                        "apparent_temperature_max",
                        "apparent_temperature_min",
                        "sunrise",
                        "sunset",
                        "precipitation_sum",
                        "rain_sum",
                        "showers_sum",
                        "snowfall_sum",
                        "precipitation_hours",
                        "windspeed_10m_max",
                        "windgusts_10m_max",
                        "winddirection_10m_dominant",
                        "shortwave_radiation_sum",
                        "et0_fao_evapotranspiration")
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyWeathercodeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMinSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMaxSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMinSizeCustomization(),
                        JsonUtils.getArrayDailySunriseSizeCustomization(),
                        JsonUtils.getArrayDailySunsetSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationSumSizeCustomization(),
                        JsonUtils.getArrayDailyRainSumSizeCustomization(),
                        JsonUtils.getArrayDailyShowersSumSizeCustomization(),
                        JsonUtils.getArrayDailySnowfallSumSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationHoursSizeCustomization(),
                        JsonUtils.getArrayDailyWindspeed10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWindgusts10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWinddirection10mDominantSizeCustomization(),
                        JsonUtils.getArrayDailyShortwaveRadiationSumSizeCustomization(),
                        JsonUtils.getArrayDailyEt0FaoEvapotranspirationSizeCustomization()
                ));
    }

    @Test
    public void requestWithoutTimeZoneWithAllValues() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400TimezoneIsRequired.json");
        Response response = requestSpec
            .given()
                .param("daily", "weathercode",
                        "temperature_2m_max",
                        "temperature_2m_min",
                        "apparent_temperature_max",
                        "apparent_temperature_min",
                        "sunrise",
                        "sunset",
                        "precipitation_sum",
                        "rain_sum",
                        "showers_sum",
                        "snowfall_sum",
                        "precipitation_hours",
                        "windspeed_10m_max",
                        "windgusts_10m_max",
                        "winddirection_10m_dominant",
                        "shortwave_radiation_sum",
                        "et0_fao_evapotranspiration")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithAllValuesAndOneIncorrect() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400CannotInitInvalidDailyVariable.json");
        Response response = requestSpec
            .given()
                .param("timezone", "Asia/Tbilisi")
                .param("daily", "weathercode",
                        "temperature2mmax",
                        "temperature_2m_min",
                        "apparent_temperature_max",
                        "apparent_temperature_min",
                        "sunrise",
                        "sunset",
                        "precipitation_sum",
                        "rain_sum",
                        "showers_sum",
                        "snowfall_sum",
                        "precipitation_hours",
                        "windspeed_10m_max",
                        "windgusts_10m_max",
                        "winddirection_10m_dominant",
                        "shortwave_radiation_sum",
                        "et0_fao_evapotranspiration")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestPastDaysWithAllIncorrectValues() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400CannotInitInvalidDailyVariable.json");
        Response response = requestSpec
            .given()
                .param("timezone", "Asia/Tbilisi")
                .param("past_days", 2)
                .param("daily", "temperature2mmax",
                        "temperature2mmin",
                        "weather_code",
                        "apparenttemperaturemax",
                        "apparenttemperaturemin",
                        "sun_rise",
                        "sun_set",
                        "precipitationsum",
                        "rainsum",
                        "showerssum",
                        "snowfallsum",
                        "precipitationhours",
                        "windspeed10mmax",
                        "windgusts10mmax",
                        "winddirection10mdominant",
                        "shortwaveradiationsum",
                        "et0faoevapotranspiration")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithStartEndDatesWithAllIncorrectValues() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400CannotInitInvalidDailyVariable.json");
        Response response = requestSpec
            .given()
                .param("timezone", "Asia/Tbilisi")
                .param("start_date", "2022-09-01")
                .param("end_date", "2022-09-01")
                .param("daily", "temperature2mmax",
                        "temperature2mmin",
                        "weather_code",
                        "apparenttemperaturemax",
                        "apparenttemperaturemin",
                        "sun_rise",
                        "sun_set",
                        "precipitationsum",
                        "rainsum",
                        "showerssum",
                        "snowfallsum",
                        "precipitationhours",
                        "windspeed10mmax",
                        "windgusts10mmax",
                        "winddirection10mdominant",
                        "shortwaveradiationsum",
                        "et0faoevapotranspiration")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString());
    }

}
