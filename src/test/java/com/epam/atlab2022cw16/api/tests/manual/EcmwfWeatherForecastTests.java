package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.FileUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("manual")
})
@JiraTicketsLink(id = 16339,
        description = "Test API endpoint (https://api.open-meteo.com/v1/ecmwf) with/without required/unrequired " +
                "parameters using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16339")
public class EcmwfWeatherForecastTests extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://api.open-meteo.com")
                .basePath("v1/ecmwf");
    }

    @Test
    public void getRequestWithRequiredValidParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("latitude", 52.52f)
                .param("longitude", 13.41)
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16339/ECMWFWeatherForecastWithRequiredParams.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithRequiredAndNonRequiredValidParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("latitude", 52.52f)
                .param("longitude", 13.41)
                .param("hourly", "surface_air_pressure")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16339/ECMFWeatherForecastWithNonRequiredParams.json");
        assertEquals(expected, actual.body().asString(),
                JsonUtils.getGenerationTimerCustomization(),
                JsonUtils.getArrayHourlyTimeSizeCustomization(),
                JsonUtils.getArrayHourlySurfaceAirPressureCustomization());
    }

    @Test
    public void getRequestWithOnlyNonRequiredParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("hourly", "skin_temperature")
                .get();
        assertEquals(400, actual.statusCode());
        String expected = getStringFromFile("/json/16339/ECMFWeatherRequestWithOnlyNonRequiredParameters.json");
        assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWithInvalidParameter() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("latitude", 52.52f)
                .param("longitude", 13.41)
                .param("hourly", "temperature_")
                .get();
        assertEquals(400, actual.statusCode());
        String expected = getStringFromFile("/json/16339/EMCFWeatherRequestWithInvalidParameter.json");
        assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWithOutOfBoundLongitudeAndLatitude() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("latitude", 92.52f)
                .param("longitude", 13.41)
                .param("hourly", "soil_temperature_0_7cm")
                .get();
        assertEquals(400, actual.statusCode());
        String expected = getStringFromFile("/json/16339/ECMFWeatherRequestWithOutOfBoundLongitudeAndLatitude.json");
        assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWithValidDateParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("latitude", 51.52f)
                .param("longitude", 13.41)
                .param("hourly", "soil_temperature_0_7cm")
                .param("start_date", "2022-08-08")
                .param("end_date", "2022-09-10")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16339/ECMFWeatherForecastWithValidDateParameters.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithOutOfAllowedRange() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("latitude", 51.52f)
                .param("longitude", 13.41)
                .param("hourly", "soil_temperature_0_7cm")
                .param("start_date", "2022-06-07")
                .param("end_date", "2022-10-16")
                .get();
        assertEquals(400, actual.statusCode());
        String expected = getStringFromFile("/json/16339/ECMFWeatherRequestWithOutOfAllowedRange.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getStarDateIsOutOfRangeErrorCustomization());
    }
}

