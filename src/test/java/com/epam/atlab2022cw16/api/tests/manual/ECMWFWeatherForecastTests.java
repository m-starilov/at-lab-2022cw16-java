package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.utils.DataUtils;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.*;

import java.io.IOException;

@JiraTicketsLink(id = 16339,
        description = "Test API endpoint (https://api.open-meteo.com/v1/ecmwf) with/without required/unrequired " +
                "parameters using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16339")
@Tags({
        @Tag("api"),
        @Tag("manual")
})
public class ECMWFWeatherForecastTests {
    private RequestSpecification requestSpecification;


    @BeforeEach
    void createSpec() {
        requestSpecification = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://api.open-meteo.com/v1/ecmwf")
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .build());
    }

    @Test
    public void getRequestWithRequiredValidParameters() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("latitude", 52.52f)
                .param("longitude", 13.41)
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/ECMWFWeatherForecastWithRequiredParams.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithRequiredAndNonRequiredValidParameters() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("latitude", 52.52f)
                .param("longitude", 13.41)
                .param("hourly", "surface_air_pressure")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/ECMFWeatherForecastWithNonRequiredParams.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForHourlyWeather());
    }

    @Test
    public void getRequestWithOnlyNonRequiredParameters() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("hourly", "skin_temperature")
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/ECMFWeatherRequestWithOnlyNonRequiredParameters.json");
        JsonUtils.assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWithInvalidParameter() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("latitude", 52.52f)
                .param("longitude", 13.41)
                .param("hourly", "temperature_")
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/EMCFWeatherRequestWithInvalidParameter.json");
        JsonUtils.assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWithOutOfBoundLongitudeAndLatitude() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("latitude", 92.52f)
                .param("longitude", 13.41)
                .param("hourly", "soil_temperature_0_7cm")
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/ECMFWeatherRequestWithOutOfBoundLongitudeAndLatitude.json");
        JsonUtils.assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWithValidDateParameters() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("latitude", 51.52f)
                .param("longitude", 13.41)
                .param("hourly", "soil_temperature_0_7cm")
                .param("start_date", "2022-08-08")
                .param("end_date", "2022-09-10")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/ECMFWeatherForecastWithValidDateParameters.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithOutOfAllowedRange() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("latitude", 51.52f)
                .param("longitude", 13.41)
                .param("hourly", "soil_temperature_0_7cm")
                .param("start_date", "2022-06-07")
                .param("end_date", "2022-10-16")
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/ECMFWeatherRequestWithOutOfAllowedRange.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForOutOfBoundStartDate());
    }
}

