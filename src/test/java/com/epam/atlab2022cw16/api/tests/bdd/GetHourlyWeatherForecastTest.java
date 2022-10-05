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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16337,
        description = "Test API (https://api.open-meteo.com/v1/forecast) with '&hourly=' parameter and its values " +
                "using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16337")
public class GetHourlyWeatherForecastTest {
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
    public void requestHourlyWithoutValue() {
        requestSpec
            .given()
                .param("hourly")
            .when()
                .get()
            .then()
                .statusCode(200)
                .body("size()", equalTo(7))
                .body("latitude", equalTo(41.625f))
                .body("longitude", equalTo(41.625f))
                .body("generationtime_ms", greaterThan(0f))
                .body("utc_offset_seconds", equalTo(0))
                .body("timezone", equalTo("GMT"))
                .body("timezone_abbreviation", equalTo("GMT"))
                .body("elevation", equalTo(4.0f));
    }

    @Test
    public void requestHourlyWithOneValue() throws JSONException, IOException {
        String expected = getStringFromFile("/json/weatherForecastHourlyOneValue200Response.json");
        Response response = requestSpec
            .given()
                .param("hourly", "temperature_2m")
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayHourlyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mSizeCustomization()));
    }

    @Test
    public void requestHourlyWeatherWithAllValues() throws JSONException, IOException {
        String expected = getStringFromFile("/json/weatherForecastHourlyAllValues200Response.json");
        Response response = requestSpec
            .given()
                .param("temperature_unit", "fahrenheit")
                .param("windspeed_unit", "ms")
                .param("precipitation_unit", "inch")
                .param("timezone", "Asia/Tbilisi")
                .param("start_date", "2022-08-31")
                .param("end_date", "2022-08-31")
                .param("hourly", "temperature_2m",
                        "relativehumidity_2m",
                        "dewpoint_2m",
                        "apparent_temperature",
                        "precipitation","rain",
                        "showers",
                        "snowfall",
                        "snow_depth",
                        "freezinglevel_height",
                        "weathercode",
                        "pressure_msl",
                        "surface_pressure",
                        "cloudcover",
                        "cloudcover_low",
                        "cloudcover_mid",
                        "cloudcover_high",
                        "evapotranspiration",
                        "et0_fao_evapotranspiration",
                        "vapor_pressure_deficit",
                        "windspeed_10m",
                        "windspeed_80m",
                        "windspeed_120m",
                        "windspeed_180m",
                        "winddirection_10m",
                        "winddirection_80m",
                        "winddirection_120m",
                        "winddirection_180m",
                        "windgusts_10m",
                        "temperature_80m",
                        "temperature_120m",
                        "temperature_180m",
                        "soil_temperature_0cm",
                        "soil_temperature_6cm",
                        "soil_temperature_18cm",
                        "soil_temperature_54cm",
                        "soil_moisture_0_1cm",
                        "soil_moisture_1_3cm",
                        "soil_moisture_3_9cm",
                        "soil_moisture_9_27cm",
                        "soil_moisture_27_81cm")
           .when()
                .get()
           .then()
                .statusCode(200)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        JsonUtils.getGenerationTimerCustomization()));
    }

    @Test
    public void requestHourlyWeatherWithAllValuesInFuture() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400StartDateIsOutOfRangeResponse.json");
        Response response = requestSpec
            .given()
                .param("temperature_unit", "fahrenheit")
                .param("windspeed_unit", "ms")
                .param("precipitation_unit", "inch")
                .param("timezone", "Asia/Tbilisi")
                .param("start_date", "2023-08-31")
                .param("end_date", "2023-08-31")
                .param("hourly", "temperature_2m",
                        "relativehumidity_2m",
                        "dewpoint_2m",
                        "apparent_temperature",
                        "precipitation","rain",
                        "showers",
                        "snowfall",
                        "snow_depth",
                        "freezinglevel_height",
                        "weathercode",
                        "pressure_msl",
                        "surface_pressure",
                        "cloudcover",
                        "cloudcover_low",
                        "cloudcover_mid",
                        "cloudcover_high",
                        "evapotranspiration",
                        "et0_fao_evapotranspiration",
                        "vapor_pressure_deficit",
                        "windspeed_10m",
                        "windspeed_80m",
                        "windspeed_120m",
                        "windspeed_180m",
                        "winddirection_10m",
                        "winddirection_80m",
                        "winddirection_120m",
                        "winddirection_180m",
                        "windgusts_10m",
                        "temperature_80m",
                        "temperature_120m",
                        "temperature_180m",
                        "soil_temperature_0cm",
                        "soil_temperature_6cm",
                        "soil_temperature_18cm",
                        "soil_temperature_54cm",
                        "soil_moisture_0_1cm",
                        "soil_moisture_1_3cm",
                        "soil_moisture_3_9cm",
                        "soil_moisture_9_27cm",
                        "soil_moisture_27_81cm")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        JsonUtils.getStarDateIsOutOfRangeErrorCustomization()));
    }

    @Test
    public void requestHourlyWeatherWithAllValuesAndOneIncorrect() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400CannotInitVariableResponse.json");
        Response response = requestSpec
            .given()
                .param("temperature_unit", "fahrenheit")
                .param("windspeed_unit", "ms")
                .param("precipitation_unit", "inch")
                .param("timezone", "Asia/Tbilisi")
                .param("hourly", "temperature_2m_wrong",
                        "relativehumidity_2m",
                        "dewpoint_2m",
                        "apparent_temperature",
                        "precipitation","rain",
                        "showers",
                        "snowfall",
                        "snowdepth",
                        "freezinglevel_height",
                        "weathercode",
                        "pressure_msl",
                        "surface_pressure",
                        "cloudcover",
                        "cloudcover_low",
                        "cloudcover_mid",
                        "cloudcover_high",
                        "evapotranspiration",
                        "et0_fao_evapotranspiration",
                        "vapor_pressure_deficit",
                        "windspeed_10m",
                        "windspeed_80m",
                        "windspeed_120m",
                        "windspeed_180m",
                        "winddirection_10m",
                        "winddirection_80m",
                        "winddirection_120m",
                        "winddirection_180m",
                        "windgusts_10m",
                        "temperature_80m",
                        "temperature_120m",
                        "temperature_180m",
                        "soil_temperature_0cm",
                        "soil_temperature_6cm",
                        "soil_temperature_18cm",
                        "soil_temperature_54cm",
                        "soil_moisture_0_1cm",
                        "soil_moisture_1_3cm",
                        "soil_moisture_3_9cm",
                        "soil_moisture_9_27cm",
                        "soil_moisture_27_81cm")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestHourlyWeatherWithAllIncorrectValues() throws JSONException, IOException {
        String expected = getStringFromFile("/json/400CannotInitVariableResponse.json");
        Response response = requestSpec
            .given()                
                .param("temperature_unit", "fahrenheit")
                .param("windspeed_unit", "ms")
                .param("precipitation_unit", "inch")
                .param("timezone", "Asia/Tbilisi")
                .param("hourly", "temperature_2m_wrong",
                        "relativehumidity_2m_wrong",
                        "dewpoint_2m_wrong",
                        "apparent_temperature_wrong",
                        "precipitation_wrong","rain_wrong",
                        "showers_wrong",
                        "snowfall_wrong",
                        "snow_depth_wrong",
                        "freezinglevel_height_wrong",
                        "weathercode_wrong",
                        "pressure_msl_wrong",
                        "surface_pressure_wrong",
                        "cloudcover_wrong",
                        "cloudcover_low_wrong",
                        "cloudcover_mid_wrong",
                        "cloudcover_high_wrong",
                        "evapotranspiration_wrong",
                        "et0_fao_evapotranspiration_wrong",
                        "vapor_pressure_deficit_wrong",
                        "windspeed_10m_wrong",
                        "windspeed_80m_wrong",
                        "windspeed_120m_wrong",
                        "windspeed_180m_wrong",
                        "winddirection_10m_wrong",
                        "winddirection_80m_wrong",
                        "winddirection_120m_wrong",
                        "winddirection_180m_wrong",
                        "windgusts_10m_wrong",
                        "temperature_80m_wrong",
                        "temperature_120m_wrong",
                        "temperature_180m_wrong",
                        "soil_temperature_0cm_wrong",
                        "soil_temperature_6cm_wrong",
                        "soil_temperature_18cm_wrong",
                        "soil_temperature_54cm_wrong",
                        "soil_moisture_0_1cm_wrong",
                        "soil_moisture_1_3cm_wrong",
                        "soil_moisture_3_9cm_wrong",
                        "soil_moisture_9_27cm_wrong",
                        "soil_moisture_27_81cm_wrong")
            .when()
                .get()
            .then()
                .statusCode(400)
                .extract().response();
        JsonUtils.assertEquals(expected, response.body().asString());
    }

}
