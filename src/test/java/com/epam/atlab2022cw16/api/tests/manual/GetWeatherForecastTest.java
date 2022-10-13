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
@JiraTicketsLink(id = 16298,
        description = "Test API endpoint (https://api.open-meteo.com/v1/forecast) with/without required/unrequired " +
                "parameters using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16298")
public class GetWeatherForecastTest extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://api.open-meteo.com")
                .basePath("v1/forecast");
    }

    @Test
    public void requestWithValidLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/weatherForecast200MinimalResponse.json");
        Response response = baseRequestSpec
                .param("latitude", 41.64f)
                .param("longitude", 41.64f)
                .get();
        assertEquals(200, response.statusCode());
        assertEquals(expected, response.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void requestWithCurrentWeatherAndValidLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/weatherForecast200WithCurrentWeatherResponse.json");
        Response response = baseRequestSpec
                .param("latitude", 41.64f)
                .param("longitude", 41.64f)
                .param("current_weather", true)
                .get();
        assertEquals(200, response.statusCode());
        assertEquals(expected, response.body().asString(),
                JsonUtils.getGenerationTimerCustomization(),
                JsonUtils.getCurrentWeatherTemperatureCustomization(),
                JsonUtils.getCurrentWeatherWindSpeedCustomization(),
                JsonUtils.getCurrentWeatherWindDirectionCustomization(),
                JsonUtils.getCurrentWeatherWeatherCodeCustomization(),
                JsonUtils.getCurrentWeatherTimeCustomization());
    }

    @Test
    public void requestRightBorderLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/400NoDataForThisLocation.json");
        Response response = baseRequestSpec
                .param("latitude", 90f)
                .param("longitude", 180f)
                .get();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestLeftBorderLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/weatherForecast200LeftBorderResponse.json");
        Response response = baseRequestSpec
                .param("latitude", -90f)
                .param("longitude", -180f)
                .get();
        assertEquals(200, response.statusCode());
        assertEquals(expected, response.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void requestOutOfBorderLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/400LatitudeMustBeInRange.json");
        Response response = baseRequestSpec
                .param("latitude", -91f)
                .param("longitude", -181f)
                .get();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestInvalidLatitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/400FloatRequiredForLatitude.json");
        Response response = baseRequestSpec
                .param("latitude", "41,64")
                .param("longitude", 41.64f)
                .get();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestInvalidLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/400FloatRequiredForLongitude.json");
        Response response = baseRequestSpec
                .param("latitude", 41.64f)
                .param("longitude", "41,64")
                .get();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithoutParameters() throws IOException, JSONException {
        String expected = getStringFromFile("/json/16298/400FloatRequiredForLatitude.json");
        Response response = baseRequestSpec
                .get();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.body().asString());
    }

}
