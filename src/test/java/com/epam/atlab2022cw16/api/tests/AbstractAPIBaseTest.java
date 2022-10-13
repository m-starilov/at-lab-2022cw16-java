package com.epam.atlab2022cw16.api.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.junit.jupiter.api.BeforeEach;

import java.nio.charset.StandardCharsets;

import static io.restassured.config.HttpClientConfig.httpClientConfig;

public abstract class AbstractAPIBaseTest {

    protected RequestSpecification baseRequestSpec;

    @BeforeEach
    public void setRequestSpecBuilder() {
        baseRequestSpec = RestAssured.given(new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ErrorLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .setConfig(RestAssuredConfig.config()
                        .httpClient(httpClientConfig()
                                .setParam(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY))
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .defaultCharsetForContentType(StandardCharsets.UTF_8, ContentType.URLENC)))
                .build());
    }

}
