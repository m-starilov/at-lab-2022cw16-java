package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.http.ContentType;
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
@JiraTicketsLink(id = 16354,
        description = "Check create, add and delete cookies",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16354")
public class BDDCookiesTest extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("http://httpbin.org")
                .contentType(ContentType.TEXT);
    }

    @Test
    void shouldHasEmptyCookiesAndResponseCodeOk200() throws IOException, JSONException {
        String expectedBody = getStringFromFile("/json/16354/ExpectedResponseEmptyCookies.json");
        String actualBody = baseRequestSpec
                .when()
                    .get("/cookies")
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void shouldHasOneCookieAndResponseCodeOk200() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16354/ExpectedResponseAddOneCookie.json");
        String actualBody = baseRequestSpec
                .when()
                    .get("/cookies/set/first/one")
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
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
        String expectedBody = getStringFromFile("/json/16354/ResponseAddTwoCookiesWithBug.json");
        String actualBody = baseRequestSpec
                .given()
                    .cookies("first", "one")
                .when()
                    .get("/cookies/set/second/two")
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void shouldGetAllCookies() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16354/ExpectedResponseTwoCookies.json");
        String actualBody = baseRequestSpec
                .given()
                    .cookies("first", "one", "second", "two")
                .when()
                    .get("/cookies")
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
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
        String expectedBody = getStringFromFile("/json/16354/ExpectedResponseTwoCookies.json");
        String actualBody = baseRequestSpec
                .given()
                    .header("Cookie", "first=one;second=two")
                .when()
                    .get("/cookies/delete?first=one")
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
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
        String expectedBody = getStringFromFile("/json/16354/ExpectedResponseTwoCookies.json");
        String actualBody = baseRequestSpec
                .given()
                    .cookies( "first", "one","second", "two")
                .when()
                    .get("/cookies/delete?second=two")
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedBody, actualBody);
    }
}
