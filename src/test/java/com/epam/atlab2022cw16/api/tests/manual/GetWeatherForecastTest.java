package com.epam.atlab2022cw16.api.tests.manual;

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

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.DataUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.getComparatorForCurrentWeather;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.getComparatorForGenerationTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("manual")
})
@JiraTicketsLink(id = 16298,
        description = "Test API endpoint (https://api.open-meteo.com/v1/forecast) with/without required/unrequired " +
                "parameters using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16298")
public class GetWeatherForecastTest {
    private RequestSpecification requestSpec;

    @BeforeEach
    void createSpec() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://api.open-meteo.com")
                .setBasePath("v1/forecast")
                .setContentType(ContentType.JSON)
                .build());
    }

    @Test
    public void requestWithValidLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast200MinimalResponse.json");
        Response response = requestSpec
                .param("latitude", 41.64f)
                .param("longitude", 41.64f)
                .get();
        assertEquals(200, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString(), getComparatorForGenerationTime());
    }

    @Test
    public void requestWithCurrentWeatherAndValidLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast200WithCurrentWeatherResponse.json");
        Response response = requestSpec
                .param("latitude", 41.64f)
                .param("longitude", 41.64f)
                .param("current_weather", true)
                .get();
        assertEquals(200, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString(), getComparatorForCurrentWeather());
    }

    @Test
    public void requestRightBorderLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast400NoDataForThisLocationResponse.json");
        Response response = requestSpec
                .param("latitude", 90f)
                .param("longitude", 180f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestLeftBorderLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast200LeftBorderResponse.json");
        Response response = requestSpec
                .param("latitude", -90f)
                .param("longitude", -180f)
                .get();
        assertEquals(200, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString(), getComparatorForGenerationTime());
    }

    @Test
    public void requestOutOfBorderLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast400LatitudeMustBeInRangeResponse.json");
        Response response = requestSpec
                .param("latitude", -91f)
                .param("longitude", -181f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestInvalidLatitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast400FloatRequiredForLatitudeResponse.json");
        Response response = requestSpec
                .param("latitude", "41,64")
                .param("longitude", 41.64f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestInvalidLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast400FloatRequiredForLongitudeResponse.json");
        Response response = requestSpec
                .param("latitude", 41.64f)
                .param("longitude", "41,64")
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithoutParameters() throws IOException, JSONException {
        String expected = getStringFromFile("/json/weatherForecast400FloatRequiredForLatitudeResponse.json");
        Response response = requestSpec.get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

}
