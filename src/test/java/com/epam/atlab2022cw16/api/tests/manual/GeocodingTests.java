package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.FileUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("manual")
})
@JiraTicketsLink(id = 16316,
        description = "Test API endpoint (https://geocoding-api.open-meteo.com/v1/search) with/without required/unrequired " +
                "parameters using happy path, positive and negative tests",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16316")
public class GeocodingTests extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://geocoding-api.open-meteo.com")
                .basePath("v1/search");
    }

    @Test
    public void getRequestWithRequiredValidParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "Berlin")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithRequiredParams.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithRequiredAndNonRequiredValidParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "Kiev")
                .param("count", 1)
                .param("format", "json")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithRequiredAndNonRequiredValidParam.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithOnlyNonRequiredParameters() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("count", 1)
                .param("format", "json")
                .get();
        assertEquals(400, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithOnlyNonRequiredParams.json");
        assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestWith3CharactersInNameParameter() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "Ber")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWith3CharactersInName.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWith2CharactersInNameParameter() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "Be")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithOnly2CharactersInName.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithOneCharacterInNameParameter() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "B")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithOneCharacterInName.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestWithInvalidNameParam() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("nam", "Berlin")
                .get();
        assertEquals(400, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithInvalidNameParam.json");
        assertEquals(expected, actual.body().asString());
    }

    @Test
    public void getRequestLanguageParameter() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "Berlin")
                .param("language", "ru")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingAPIWithLanguageParam.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    public void getRequestInvalidLanguageParameter() throws JSONException, IOException {
        Response actual = baseRequestSpec
                .param("name", "Berlin")
                .param("language", "rus")
                .get();
        assertEquals(200, actual.statusCode());
        String expected = getStringFromFile("/json/16316/GeocodingWithInvalidLanguageParam.json");
        assertEquals(expected, actual.body().asString(), JsonUtils.getGenerationTimerCustomization());
    }
}

