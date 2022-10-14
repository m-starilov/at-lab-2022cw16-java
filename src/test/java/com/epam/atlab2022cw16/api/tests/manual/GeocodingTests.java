package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.utils.DataUtils;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.*;

import java.io.IOException;

@JiraTicketsLink(id = 16316,
        description = "Test API endpoint (https://geocoding-api.open-meteo.com/v1/search) with/without required/unrequired " +
                "parameters using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16316")
@Tags({
        @Tag("api"),
        @Tag("manual")
})
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
        Response actual = requestSpecification
                .param("name", "Berlin")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithRequiredParams.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithRequiredAndNonRequiredValidParameters() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("name", "Kiev")
                .param("count", 1)
                .param("format", "json")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithRequiredAndNonRequiredValidParam.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithOnlyNonRequiredParameters() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("count", 1)
                .param("format", "json")
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithOnlyNonRequiredParams.json");
        JsonUtils.assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWith3CharactersInNameParameter() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("name", "Ber")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWith3CharactersInName.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWith2CharactersInNameParameter() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("name", "Be")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithOnly2CharactersInName.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithOneCharacterInNameParameter() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("name", "B")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithOneCharacterInName.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestWithInvalidNameParam() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("nam", "Berlin")
                .get();
        Assertions.assertEquals(400, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithInvalidNameParam.json");
        JsonUtils.assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestLanguageParameter() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("name", "Berlin")
                .param("language", "ru")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingAPIWithLanguageParam.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void getRequestInvalidLanguageParameter() throws JSONException, IOException {
        Response actual = requestSpecification
                .param("name", "Berlin")
                .param("language", "rus")
                .get();
        Assertions.assertEquals(200, actual.statusCode());
        String expected = DataUtils.getStringFromFile("/json/GeocodingWithInvalidLanguageParam.json");
        JsonUtils.assertEquals(expected, actual.body().asString(), JsonUtils.getComparatorForGenerationTime());
    }
}

