package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.DataUtils;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16352,
        description = "Test API endpoint (https://marine-api.open-meteo.com/v1/marine) with latitude, longitude, " +
                "start date, end date and past days parameters",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16352")
public class BDDMarineWeatherTest extends AbstractAPIBaseTest {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void initTest() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                        .setBaseUri("https://marine-api.open-meteo.com")
                        .setBasePath("v1/marine")
                        .setContentType(ContentType.JSON)
                        .build()
                        .log().all());
    }

    @Test
    public void requestWithoutAnyParameters() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/ExpectedResponseWithEmptyQuery.json");
        String actualBody = requestSpec
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithLeftBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/400NoDataForThisLocation.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", -90f)
                    .queryParam("longitude", -180f)
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithOutOfLeftBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/400LatitudeMustBeInRange.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", -91f)
                    .queryParam("longitude", -181f)
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody, JsonUtils.getComparatorForLatitudeOutOfRange());
    }

    @Test
    public void requestWithRightBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/400NoDataForThisLocation.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", 90f)
                    .queryParam("longitude", 180f)
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithOutOfRightBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/400LatitudeMustBeInRange.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", 91f)
                    .queryParam("longitude", 181f)
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody, JsonUtils.getComparatorForLatitudeOutOfRange());
    }

    @Test
    public void requestWithCoordinatesAndStartDateParameters() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/ExpectedResponseWithOnlyStartDate.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", DataUtils.getISO8601CurrentDate())
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithCoordinatesAndEndDateBeforeStartDateParameters() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/ExpectedResponseWithEndDateBeforeStartDate.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", DataUtils.getISO8601CurrentDate())
                    .queryParam("end_date", DataUtils.getISO8601PastDaysDate(3))
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithValidCoordinatesAndDaysParameters() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/marineWeather200MinimalValidResponse.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", DataUtils.getISO8601CurrentDate())
                    .queryParam("end_date", DataUtils.getISO8601CurrentDate())
                    .get()
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody, JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void requestWithValidCoordinatesStartEndDatesAndPastDaysParameters() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/ExpectedResponseWithPastDaysStartAndEndDaysParameters.json");
        String actualBody = requestSpec
                .when()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", DataUtils.getISO8601CurrentDate())
                    .queryParam("end_date", DataUtils.getISO8601CurrentDate())
                    .queryParam("past_days", 3)
                    .get()
                .then()
                    .statusCode(400)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }
}
