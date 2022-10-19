package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.http.ContentType;
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
@JiraTicketsLink(id = 16333,
        description = "Check create, add and delete cookies",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16333")
public class APICookiesTest extends AbstractAPIBaseTest {
    
    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("http://httpbin.org")
                .contentType(ContentType.TEXT);
    }

    @Test
    void shouldHasEmptyCookiesAndResponseCodeOk200() throws IOException, JSONException {
        Response response = baseRequestSpec
                .get("/cookies");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        assertEquals(getStringFromFile("/json/16333/ExpectedResponseEmptyCookies.json"), actualResponse);
    }

    @Test
    void shouldHasOneCookieAndResponseCodeOk200() throws JSONException, IOException {
        Response response = baseRequestSpec
                .get("/cookies/set/first/one");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        assertEquals(getStringFromFile("/json/16333/ExpectedResponseAddOneCookie.json"), actualResponse);
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
        Response response = baseRequestSpec
                .cookies("first", "one")
                .get("/cookies/set/second/two");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        assertEquals(getStringFromFile("/json/16333/ResponseAddTwoCookiesWithBug.json"), actualResponse);
    }

    @Test
    void shouldGetAllCookies() throws JSONException, IOException {
        Response response = baseRequestSpec
                .cookies("first", "one", "second", "two")
                .get("/cookies");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        assertEquals(getStringFromFile("/json/16333/ExpectedResponseTwoCookies.json"), actualResponse);
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
        Response response = baseRequestSpec
                .header("Cookie", "first=one;second=two")
                .get("/cookies/delete?first=one");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        assertEquals(getStringFromFile("/json/16333/ExpectedResponseTwoCookies.json"), actualResponse);
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
        Response response = baseRequestSpec
                .cookies("first", "one", "second", "two")
                .get("/cookies/delete?second=two");
        assertEquals(200, response.statusCode());
        String actualResponse = response.getBody().asString();
        assertEquals(getStringFromFile("/json/16333/ExpectedResponseTwoCookies.json"), actualResponse);
    }
}
