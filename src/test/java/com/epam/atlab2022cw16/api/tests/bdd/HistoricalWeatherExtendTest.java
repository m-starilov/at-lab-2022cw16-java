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
import static com.epam.atlab2022cw16.api.utils.FileUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16359,
        description = "[API] [BDD] Historical Weather",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16359?workflowName=EPAM+Standard+Workflow+1&stepId=1")
public class HistoricalWeatherExtendTest extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://archive-api.open-meteo.com")
                .basePath("v1/era5");
    }

    @Test
    public void getRequestWithValidRequiredParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithValidRequiredParams.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithInValidLatitudeValue() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithInValidRequiredParams.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestWithoutLatitudeOrLongitude() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithoutLatitudeOrLongitude.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestWithStartDateOutOfBound() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithStartDateOutOfBond.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestWithEndDateOutOfBound() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithEndDateOutOfBound.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual, JsonUtils.getReasonEndDateOutOfBoundCustomization());
    }

    @Test
    public void getRequestWithCurrentDate() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithCurrentDate.json");
        String actual =baseRequestSpec
                .given()
                    .param("latitude", 52.52)
                    .param("longitude", 13.41)
                    .param("start_date", getISO8601CurrentDate())
                    .param("end_date", getISO8601CurrentDate())
                    .param("hourly", "temperature_2m")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual,
                JsonUtils.getGenerationTimerCustomization(),
                JsonUtils.getArrayHourlyTimeSizeCustomization(),
                JsonUtils.getArrayHourlySurfaceAirPressureCustomization());
    }

    @Test
    public void getRequestWithoutEndDate() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithoutEndDate.json");
        String actual =baseRequestSpec
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
        assertEquals(expected, actual);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * "Parameter 'start_date' has a wrong format"
     */
    @Test
    public void getRequestWithWrongDateFormat() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithWrongDateFormat.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * Value of type 'String' required for key 'start_date'.
     */
    @Test
    public void getRequestWithNullDate() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16359/HistoricalWeatherWithWrongDateFormat.json");
        String actual = baseRequestSpec
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
        assertEquals(expected, actual);
    }

}
