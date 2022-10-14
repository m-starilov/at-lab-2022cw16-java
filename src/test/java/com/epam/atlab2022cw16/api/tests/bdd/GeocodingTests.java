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
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;


@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16360,
        description = "[BDD][API]Geocoding API testing",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16360")
public class GeocodingTests {
    private RequestSpecification requestSpecification;

    @BeforeEach
    void createSpec() {
        requestSpecification = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://geocoding-api.open-meteo.com/v1/search")
                .setContentType(ContentType.JSON)
                .build());
    }

    @Test
    public void getRequestWithRequiredValidParameters() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithRequiredParams.json");
        String actual = requestSpecification
                .given()
                    .param("name", "Berlin")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());

    }

    @Test
    public void getRequestWithRequiredAndNonRequiredValidParameters() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithRequiredAndNonRequiredValidParam.json");
        String actual = requestSpecification
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
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithOnlyNonRequiredParameters() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithOnlyNonRequiredParams.json");
        String actual = requestSpecification
                .given()
                    .param("count", 1)
                    .param("format", "json")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    @Test
    public void getRequestWith3CharactersInNameParameter() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWith3CharactersInName.json");
        String actual = requestSpecification
                .given()
                    .param("name", "Ber")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWith2CharactersInNameParameter() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithOnly2CharactersInName.json");
        String actual = RestAssured.given(requestSpecification)
                .given()
                    .param("name", "Be")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithOneCharacterInNameParameter() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithOneCharacterInName.json");
        String actual = RestAssured.given(requestSpecification)
                .given()
                    .param("name", "B")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithInvalidNameParam() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithInvalidNameParam.json");
        String actual = requestSpecification
                .given()
                    .param("nam", "Berlin")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual);
    }

    @Test
    public void getRequestLanguageParameter() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithLanguageParam.json");
        String actual = requestSpecification
                .given()
                    .param("name", "Berlin")
                    .param("language", "ru")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestInvalidLanguageParameter() throws JSONException, IOException {
        String expected = DataUtils.getStringFromFile("/json/GeocodingWithInvalidLanguageParam.json");
        String actual = requestSpecification
                .given()
                    .param("name", "Berlin")
                    .param("language", "rus")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        JsonUtils.assertEquals(expected, actual, JsonUtils.getComparatorForGenerationTime());
    }
}
