package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
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
@JiraTicketsLink(id = 16360,
        description = "[BDD][API]Geocoding API testing",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16360")
public class GeocodingTests extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://geocoding-api.open-meteo.com")
                .basePath("v1/search");
    }

    @Test
    public void getRequestWithRequiredValidParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithRequiredParams.json");
        String actual = baseRequestSpec
                .given()
                    .param("name", "Berlin")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithRequiredAndNonRequiredValidParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithRequiredAndNonRequiredValidParam.json");
        String actual = baseRequestSpec
                .given()
                    .param("name", "Kiev")
                    .param("count", 1)
                    .param("format", "json")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithOnlyNonRequiredParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithOnlyNonRequiredParams.json");
        String actual = baseRequestSpec
                .given()
                    .param("count", 1)
                    .param("format", "json")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestWith3CharactersInNameParameter() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWith3CharactersInName.json");
        String actual = baseRequestSpec
                .given()
                    .param("name", "Ber")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWith2CharactersInNameParameter() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithOnly2CharactersInName.json");
        String actual = RestAssured.given(baseRequestSpec)
                .given()
                    .param("name", "Be")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithOneCharacterInNameParameter() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithOneCharacterInName.json");
        String actual = RestAssured.given(baseRequestSpec)
                .given()
                    .param("name", "B")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithInvalidNameParam() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithInvalidNameParam.json");
        String actual = baseRequestSpec
                .given()
                    .param("nam", "Berlin")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestLanguageParameter() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingAPIWithLanguageParam.json");
        String actual = baseRequestSpec
                .given()
                    .param("name", "Berlin")
                    .param("language", "ru")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestInvalidLanguageParameter() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16360/GeocodingWithInvalidLanguageParam.json");
        String actual = baseRequestSpec
                .given()
                    .param("name", "Berlin")
                    .param("language", "rus")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expected, actual, JsonUtils.getGenerationTimerCustomization());
    }
}
