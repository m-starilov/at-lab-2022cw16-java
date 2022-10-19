package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.FileUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16366,
        description = "Check Api Elevation",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16366")
public class ElevationAPITest extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://api.open-meteo.com")
                .basePath("v1/elevation");
    }

    @Test
    void shouldReturnOk200AndBodyOnRequestWithOnePareBoundaryValues() throws JSONException, IOException {
        String actualResponseBody = baseRequestSpec
                .given()
                    .queryParam("latitude", -90f)
                    .queryParam("longitude", 90f)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWithOnePareBoundaryValues.json"), actualResponseBody);
    }

    @Test
    void shouldReturnOk200AndBodyOnRequestWithTwoParesValidValues() throws JSONException, IOException {
        String actualResponseBody = baseRequestSpec
                .given()
                    .queryParam("latitude", -90f, -70f)
                    .queryParam("longitude", 90f, 70f)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWithTwoParesValidValues.json"), actualResponseBody);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithoutValidValues() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWithoutValidValues.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithOutOfReachValues() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", -91f)
                    .queryParam("longitude", 90f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWithOutOfReachValues.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithDifferentNumberOfValues() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", -90f, 70f)
                    .queryParam("longitude", 90f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWithDifferentNumberOfValues.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithWrongTypeOfValue() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", "-90,7")
                    .queryParam("longitude", 90f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWrongTypeOfValue.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithWrongTypeOfAllValues() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", "-90,7")
                    .queryParam("longitude", "90f.5")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWrongTypeOfValue.json"), actualResponse);
    }

    @Test
    void shouldReturn400ErrorAndErrorMessageOnRequestWithWrongParamName() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitudes", -90f)
                    .queryParam("longitude", 90f)
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(getStringFromFile("/json/16366/ExpectedResponseWrongParamName.json"), actualResponse);
    }
}
