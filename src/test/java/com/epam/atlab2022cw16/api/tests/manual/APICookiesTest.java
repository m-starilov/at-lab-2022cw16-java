package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static com.epam.atlab2022cw16.api.utils.DataUtils.getStringFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({
        @Tag("api"),
        @Tag("manual")
})

@JiraTicketsLink(id = 16333,
        description = "Check create, add and delete cookies",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16333")

public class APICookiesTest {
    private RequestSpecification requestSpec;

    @BeforeEach
    void createSpec() {
        requestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("http://httpbin.org")
                .setContentType(ContentType.TEXT)
                .setConfig(RestAssuredConfig.config()
                        .httpClient(httpClientConfig()
                                .setParam(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY))
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .defaultCharsetForContentType(StandardCharsets.UTF_8, ContentType.URLENC))
                )
                .build());
    }

    @Test
    void shouldHasEmptyCookiesAndResponseCodeOk200() throws IOException, JSONException {
        Response response = requestSpec
                .get("/cookies");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        JsonUtils.assertEquals(getStringFromFile("/json/cookies/ExpectedResponseEmptyCookies.json"), actualResponse);
    }

    @Test
    void shouldHasOneCookieAndResponseCodeOk200() throws JSONException, IOException {
        Response response = requestSpec.get("/cookies/set/first/one");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        JsonUtils.assertEquals(getStringFromFile("/json/cookies/ExpectedResponseAddOneCookie.json"), actualResponse);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * {
     * "cookies": {
     * "first": "one",
     * "second": "two"
     * }
     * }
     * But actual result is:
     * {
     * "cookies": {
     * "first": "one,second=two"
     * }
     * }
     */
    @Test
    void shouldHasTwoCookiesAndResponseCodeOk200() throws JSONException, IOException {
        Response response = requestSpec.cookies("first", "one")
                .get("/cookies/set/second/two");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        JsonUtils.assertEquals(getStringFromFile("/json/cookies/ResponseAddTwoCookiesWithBug.json"), actualResponse);
    }

    @Test
    void shouldGetAllCookies() throws JSONException, IOException {
        Response response = requestSpec
                .cookies("first", "one", "second", "two")
                .get("/cookies");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        JsonUtils.assertEquals(getStringFromFile("/json/cookies/ExpectedResponseTwoCookies.json"), actualResponse);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * {
     * "cookies": {
     * "second": "two"
     * }
     * }
     * But actual result is:
     * {
     * "cookies": {
     * "first": "one",
     * "second": "two"
     * }
     * }
     */
    @Test
    void shouldDeleteFirstCookie() throws JSONException, IOException {
        Response response = requestSpec.header("Cookie", "first=one;second=two")
                .get("/cookies/delete?first=one");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        JsonUtils.assertEquals(getStringFromFile("/json/cookies/ExpectedResponseTwoCookies.json"), actualResponse);
    }

    /**
     * There is a bug.
     * Expected result must be:
     * {
     * "cookies": {
     * }
     * }
     * But actual result is:
     * {
     * "cookies": {
     * "first": "one",
     * "second": "two"
     * }
     * }
     */
    @Test
    void shouldDeleteSecondCookie() throws JSONException, IOException {
        Response response = requestSpec.cookies("first", "one", "second", "two")
                .get("/cookies/delete?second=two");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        JsonUtils.assertEquals(getStringFromFile("/json/cookies/ExpectedResponseTwoCookies.json"), actualResponse);
    }
}
