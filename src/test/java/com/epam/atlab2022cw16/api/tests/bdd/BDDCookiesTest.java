package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.DataUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
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

import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;
import static io.restassured.config.HttpClientConfig.httpClientConfig;

@Tags({
        @Tag("api"),
        @Tag("bdd")
})
@JiraTicketsLink(id = 16354,
        description = "Check create, add and delete cookies",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16354")
public class BDDCookiesTest extends AbstractAPIBaseTest {

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
        String expectedBody = DataUtils.getStringFromFile("/json/cookies/ExpectedResponseEmptyCookies.json");
        String actualBody = requestSpec
                .when()
                    .get("/cookies")
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    void shouldHasOneCookieAndResponseCodeOk200() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/cookies/ExpectedResponseAddOneCookie.json");
        String actualBody = requestSpec
                .when()
                    .get("/cookies/set/first/one")
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
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
        String expectedBody = DataUtils.getStringFromFile("/json/cookies/ResponseAddTwoCookiesWithBug.json");
        String actualBody = requestSpec
                .when()
                    .cookies("first", "one")
                    .get("/cookies/set/second/two")
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }

    @Test
    void shouldGetAllCookies() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/cookies/ExpectedResponseTwoCookies.json");
        String actualBody = requestSpec
                .when()
                    .cookies("first", "one", "second", "two")
                    .get("/cookies")
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
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
        String expectedBody = DataUtils.getStringFromFile("/json/cookies/ExpectedResponseTwoCookies.json");
        String actualBody = requestSpec
                .when()
                    .header("Cookie", "first=one;second=two")
                    .get("/cookies/delete?first=one")
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
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
        String expectedBody = DataUtils.getStringFromFile("/json/cookies/ExpectedResponseTwoCookies.json");
        String actualBody = requestSpec
                .when()
                    .cookies( "first", "one","second", "two")
                    .get("/cookies/delete?second=two")
                .then()
                    .statusCode(200)
                .extract().asString();

        assertEquals(expectedBody, actualBody);
    }
}
