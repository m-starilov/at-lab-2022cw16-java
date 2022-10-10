package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.DataUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})

@JiraTicketsLink(id = 16366,
        description = "Check Api Elevation",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16366")

public class ElevationAPITest {
    private static final String URL = "https://api.open-meteo.com/v1/elevation";
    private RequestSpecification requestSpec;

    @BeforeEach
    void createSpec() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .addFilter(new ErrorLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build());
    }

    @Test
    void shouldReturnOk200AndBodyOnRequestWithOnePareBoundaryValues() throws JSONException, IOException {
        String actualResponseBody = requestSpec
                .when()
                    .queryParam("latitude", -90f)
                    .queryParam("longitude", 90f)
                    .get()
                .then()
                    .statusCode(200)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWithOnePareBoundaryValues.json"), actualResponseBody);
    }

    @Test
    void shouldReturnOk200AndBodyOnRequestWithTwoParesValidValues() throws JSONException, IOException {
        String actualResponseBody = requestSpec
                .when()
                    .queryParam("latitude", -90f, -70f)
                    .queryParam("longitude", 90f, 70f)
                    .get()
                .then()
                    .statusCode(200)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWithTwoParesValidValues.json"), actualResponseBody);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithoutValidValues() throws JSONException, IOException {
        String actualResponse = requestSpec
                .when()
                    .get()
                .then()
                    .statusCode(400)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWithoutValidValues.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithOutOfReachValues() throws JSONException, IOException {
        String actualResponse = requestSpec
                .when()
                    .queryParam("latitude", -91f)
                    .queryParam("longitude", 90f)
                    .get()
                .then()
                    .statusCode(400)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWithOutOfReachValues.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithDifferentNumberOfValues() throws JSONException, IOException {
        String actualResponse = requestSpec
                .when()
                    .queryParam("latitude", -90f, 70f)
                    .queryParam("longitude", 90f)
                    .get()
                .then()
                    .statusCode(400)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWithDifferentNumberOfValues.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithWrongTypeOfValue() throws JSONException, IOException {
        String actualResponse = requestSpec
                .when()
                    .queryParam("latitude", "-90,7")
                    .queryParam("longitude", 90f)
                    .get()
                .then()
                    .statusCode(400)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWrongTypeOfValue.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithWrongTypeOfAllValues() throws JSONException, IOException {
        String actualResponse = requestSpec
                .when()
                    .queryParam("latitude", "-90,7")
                    .queryParam("longitude", "90f.5")
                    .get()
                .then()
                    .statusCode(400)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWrongTypeOfValue.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithWrongParamName() throws JSONException, IOException {
        String actualResponse = requestSpec
                .when()
                    .queryParam("latitudes", -90f)
                    .queryParam("longitude", 90f)
                    .get()
                .then()
                    .statusCode(400)
                    .extract().asString();
        assertEquals(getStringFromFile("/json/elevation/ExpectedResponseWrongParamName.json"), actualResponse);
    }
}
