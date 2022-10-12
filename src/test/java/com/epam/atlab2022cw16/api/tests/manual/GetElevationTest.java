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
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("manual")
})
@JiraTicketsLink(id = 16313,
        description = "Test API endpoint (https://api.open-meteo.com/v1/elevation) with/without required parameters",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16313")
public class GetElevationTest {
    private RequestSpecification requestSpec;

    @BeforeEach
    void createSpec() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://api.open-meteo.com")
                .setBasePath("v1/elevation")
                .setContentType(ContentType.JSON)
                .build());
    }

    @Test
    public void requestWithOnePareBoundaryValues() throws IOException, JSONException {
        String expected = getStringFromFile("/json/elevation200Response.json");
        Response response = requestSpec
                .param("latitude", 41.64f)
                .param("longitude", 41.64f)
                .get();
        assertEquals(200, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithArrayOfValues() throws IOException, JSONException {
        String expected = getStringFromFile("/json/elevation200ArrayResponse.json");
        Response response = requestSpec
                .param("latitude", 41.64f, 52.52f, 64.1353f, -35.2820f)
                .param("longitude", 41.64f, 13.41f, -21.8952f, 149.1286f)
                .get();
        assertEquals(200, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithoutParameters() throws IOException, JSONException {
        String expected = getStringFromFile("/json/400LatitudeAndLongitudeMustNotBeEmpty.json");
        Response response = requestSpec.get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithOutOfReachValueOfLatitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/400LatitudeMustBeInRange.json");
        Response response = requestSpec
                .param("latitude", -91f)
                .param("longitude", 41.64f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithDifferentAmountOfValues() throws IOException, JSONException {
        String expected = getStringFromFile("/json/400LatitudeAndLongitudeMustHaveTheSameAmountOfElements.json");
        Response response = requestSpec
                .param("latitude", 41.64f, 52.52f, 64.1353f, -35.2820f)
                .param("longitude", 13.41f, -21.8952f, 149.1286f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithWrongDataFormatInLatitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/400FloatRequiredForLatitude.json");
        Response response = requestSpec
                .param("latitude", "41,64")
                .param("longitude", 41.64f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithWrongDataFormatInLatitudeAndLongitude() throws IOException, JSONException {
        String expected = getStringFromFile("/json/400FloatRequiredForLatitude.json");
        Response response = requestSpec
                .param("latitude", "41,64")
                .param("longitude", "41,64")
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

    @Test
    public void requestWithWrongParamName() throws IOException, JSONException {
        String expected = getStringFromFile("/json/400LatitudeAndLongitudeMustHaveTheSameAmountOfElements.json");
        Response response = requestSpec
                .param("latitude_wrong", 41.64f)
                .param("longitude", 41.64f)
                .get();
        assertEquals(400, response.statusCode());
        JsonUtils.assertEquals(expected, response.body().asString());
    }

}
