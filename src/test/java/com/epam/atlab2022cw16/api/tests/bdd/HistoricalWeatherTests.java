package com.epam.atlab2022cw16.api.tests.bdd;

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


@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16359,
        description = "[API] [BDD] Historical Weather",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16359?workflowName=EPAM+Standard+Workflow+1&stepId=1")
public class HistoricalWeatherTests {
    private RequestSpecification requestSpecification;

    @BeforeEach
    void createSpec() {
        requestSpecification = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://archive-api.open-meteo.com/v1/era5")
                .setContentType(ContentType.JSON)
                .build());
    }

    @Test
    public void getRequestWithValidRequiredParameters() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithValidRequiredParams.json");
        String actual = requestSpecification
                .given()
                    .param("latitude", 52.52)
                    .param("longitude", 13.41)
                    .param("start_date", "2022-01-01")
                    .param("end_date", "2022-07-13")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());

    }

    @Test
    public void getRequestWithInValidLatitudeValue() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithInValidRequiredParams.json");
        String actual = requestSpecification
                .given()
                    .param("latitude", 90.52)
                    .param("longitude", 13.41)
                    .param("start_date", "2022-01-01")
                    .param("end_date", "2022-07-13")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    @Test
    public void getRequestWithoutLatitudeOrLongitude() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithoutLatitudeOrLongitude.json");
        String actual = requestSpecification
                .given()
                    .param("longitude", 13.41)
                    .param("start_date", "2022-01-01")
                    .param("end_date", "2022-07-13")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    @Test
    public void getRequestWithStartDateOutOfBound() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithStartDateOutOfBond.json");
        String actual = requestSpecification
                .given()
                    .param("latitude", 51.52)
                    .param("longitude", 13.41)
                    .param("start_date", "1958-12-31")
                    .param("end_date", "2022-07-13")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    @Test
    public void getRequestWithEndDateOutOfBound() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithEndDateOutOfBound.json");
        String actual = requestSpecification
                .given()
                    .param("latitude", 51.52)
                    .param("longitude", 13.41)
                    .param("start_date", "2000-12-31")
                    .param("end_date", "2030-01-02")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForOutOfBoundEndDate());
    }

    @Test
    public void getRequestWithCurrentDate() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithCurrentDate.json");
        String actual =requestSpecification
                .given()
                    .param("latitude", 52.52)
                    .param("longitude", 13.41)
                    .param("start_date", DataUtils.getISO8601CurrentDate())
                    .param("end_date", DataUtils.getISO8601CurrentDate())
                    .param("hourly", "temperature_2m")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForHourlyWeather());
    }

    @Test
    public void getRequestWithoutEndDate() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithoutEndDate.json");
        String actual =requestSpecification
                .given()
                    .param("latitude", 51.52)
                    .param("longitude", 13.41)
                    .param("start_date", "2022-09-30")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * "Parameter 'start_date' has a wrong format"
     */
    @Test
    public void getRequestWithWrongDateFormat() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithWrongDateFormat.json");
        String actual = requestSpecification
                .given()
                    .param("latitude", 51.52)
                    .param("longitude", 13.41)
                    .param("start_date", "1959-31-12")
                    .param("end_date", "2022-07-13")
                .when()
                    .get()
                .then()
                    .statusCode(500)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * Value of type 'String' required for key 'start_date'.
     */
    @Test
    public void getRequestWithNullDate() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/HistoricalWeatherWithWrongDateFormat.json");
        String actual = requestSpecification
                .given()
                    .param("latitude", 51.52)
                    .param("longitude", 13.41)
                    .param("start_date", "")
                    .param("end_date", "2022-07-13")
                .when()
                    .get()
                .then()
                    .statusCode(500)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }
}
