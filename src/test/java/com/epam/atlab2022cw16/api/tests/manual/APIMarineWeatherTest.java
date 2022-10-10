package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.atlab2022cw16.api.utils.DataUtils.getStringFromFile;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("manual")
})

@JiraTicketsLink(id = 16308,
        description = "Check Marine Weather API",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16308")

public class APIMarineWeatherTest extends AbstractAPIBaseTest {
    private RequestSpecification requestSpec;

    @BeforeEach
    void createSpec() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://marine-api.open-meteo.com/v1/marine")
                .setContentType(ContentType.JSON)
                .build());
    }

    @Test
    void shouldReturnError400AndErrorMessageOnEmptyQuery() throws JSONException, IOException {
        Response actual = requestSpec.get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithEmptyQuery.json");
        JsonUtils.assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void shouldReturnError400AndErrorMessageOnLeftBorderConditions() throws JSONException, IOException {
        Response actual = requestSpec.queryParam("latitude", -90f)
                .queryParam("longitude", -180f).get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithBorderConditions.json");
        JsonUtils.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldReturnError400AndErrorMessageOnOutOfLeftBorderConditions() throws JSONException, IOException {
        Response actual = requestSpec.queryParam("latitude", -91f)
                .queryParam("longitude", -181f).get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithOutOfLeftBorderConditions.json");
        assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    void shouldReturnError400AndErrorMessageOnRightBorderConditions() throws JSONException, IOException {
        Response actual = requestSpec.queryParam("latitude", 90f)
                .queryParam("longitude", 180f).get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithBorderConditions.json");
        JsonUtils.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldReturnError400AndErrorMessageOnOutOfRightBorderConditions() throws JSONException, IOException {
        Response actual = requestSpec.queryParam("latitude", 91f)
                .queryParam("longitude", 181f).get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithOutOfRightBorderConditions.json");
        JsonUtils.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldReturnError400AndErrorMessageOnRequestWithFutureStartDate() throws JSONException, IOException {
        String futureDate = LocalDate.now().plusDays(1).toString();
        Response actual = requestSpec.queryParam("latitude", 36.86f)
                .queryParam("longitude", 30.59f)
                .queryParam("start_date", futureDate).get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithFutureStartDate.json");
        JsonUtils.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldReturnError400AndErrorMessageOnRequestWithFutureStartDateAndEndDateBeforeStart() throws JSONException, IOException {
        String futureDate = LocalDate.now().plusDays(2).toString();
        Response actual = requestSpec.queryParam("latitude", 36.86f)
                .queryParam("longitude", 30.59f)
                .queryParam("start_date", futureDate)
                .queryParam("end_date", LocalDate.now().toString())
                .get();
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithFutureStartDateAndEndDateBeforeStart.json");
        JsonUtils.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldReturnOK200OnRequestWithFutureStartAndEndDates() throws JSONException, IOException {
        String futureStartDate = LocalDate.now().plusDays(1).toString();
        String futureEndDate = LocalDate.now().plusDays(2).toString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithFutureStartAndEndDates.json");
        Response actualResponse = requestSpec.queryParam("latitude", 36.86f)
                .queryParam("longitude", 30.59f)
                .queryParam("start_date", futureStartDate)
                .queryParam("end_date", futureEndDate)
                .get();
        Assertions.assertEquals(200, actualResponse.statusCode());
        JsonUtils.assertEquals(expectedResponse, actualResponse.asString(), JsonUtils.getComparatorForGenerationTime());
    }


    @Test
    void shouldReturnError400AndErrorMessageOnRequestWithPastDays() throws JSONException, IOException {
        String futureDate = LocalDate.now().plusDays(2).toString();
        Response actual = requestSpec.queryParam("latitude", 36.86f)
                .queryParam("longitude", 30.59f)
                .queryParam("start_date", LocalDate.now().toString())
                .queryParam("end_date", futureDate)
                .queryParam("past_days", 1)
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String actualResponse = actual.getBody().asString();
        String expectedResponse = getStringFromFile("/json/marineWeather/ExpectedResponseWithPastDays.json");
        assertEquals(expectedResponse, actualResponse, true);
    }
}
