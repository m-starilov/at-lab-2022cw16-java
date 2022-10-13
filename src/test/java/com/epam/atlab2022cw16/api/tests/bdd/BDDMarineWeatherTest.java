package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.DateUtils.getISO8601CurrentDate;
import static com.epam.atlab2022cw16.api.utils.DateUtils.getISO8601PastDaysDate;
import static com.epam.atlab2022cw16.api.utils.FileUtils.getStringFromFile;
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

    @BeforeEach
    public void initTest() {
         baseRequestSpec
                        .baseUri("https://marine-api.open-meteo.com")
                        .basePath("v1/marine");
    }

    @Test
    public void requestWithoutAnyParameters() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/ExpectedResponseWithEmptyQuery.json");
        String actualBody = baseRequestSpec
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithLeftBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/400NoDataForThisLocation.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", -90f)
                    .queryParam("longitude", -180f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithOutOfLeftBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/400LatitudeMustBeInRange.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", -91f)
                    .queryParam("longitude", -181f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody, JsonUtils.getCustomizationForLatitudeOutOfRange());
    }

    @Test
    public void requestWithRightBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/400NoDataForThisLocation.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 90f)
                    .queryParam("longitude", 180f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithOutOfRightBorderCoordinatesValues() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/400LatitudeMustBeInRange.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 91f)
                    .queryParam("longitude", 181f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody, JsonUtils.getCustomizationForLatitudeOutOfRange());
    }

    @Test
    public void requestWithCoordinatesAndStartDateParameters() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/ExpectedResponseWithOnlyStartDate.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", getISO8601CurrentDate())
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithCoordinatesAndEndDateBeforeStartDateParameters() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/ExpectedResponseWithEndDateBeforeStartDate.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", getISO8601CurrentDate())
                    .queryParam("end_date", getISO8601PastDaysDate(3))
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void requestWithValidCoordinatesAndDaysParameters() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/marineWeather200MinimalValidResponse.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", getISO8601CurrentDate())
                    .queryParam("end_date", getISO8601CurrentDate())
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void requestWithValidCoordinatesStartEndDatesAndPastDaysParameters() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16352/ExpectedResponseWithPastDaysStartAndEndDaysParameters.json");
        String actualBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 41.625f)
                    .queryParam("longitude", 41.625f)
                    .queryParam("start_date", getISO8601CurrentDate())
                    .queryParam("end_date", getISO8601CurrentDate())
                    .queryParam("past_days", 3)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }
}
