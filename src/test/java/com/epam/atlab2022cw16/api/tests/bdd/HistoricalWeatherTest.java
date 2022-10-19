package com.epam.atlab2022cw16.api.tests.bdd;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
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
@JiraTicketsLink(id = 16358,
        description = "Check Historical Weather API",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16358")
public class HistoricalWeatherTest extends AbstractAPIBaseTest {

    @BeforeEach
    void createSpec() {
        baseRequestSpec
                .baseUri("https://archive-api.open-meteo.com")
                .basePath("v1/era5");
    }

    @Test
    void shouldReturnOK200AndValidBodyOnRequestWithAutoTimezone() throws JSONException, IOException {
        String expectedResponseBody = getStringFromFile("/json/16358/ExpectedResponseWithAutoTimezone.json");
        String actualResponseBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 52.52f)
                    .queryParam("longitude", 13.41f)
                    .queryParam("start_date", "2022-01-01")
                    .queryParam("end_date", "2022-07-13")
                    .queryParam("timezone", "auto")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedResponseBody, actualResponseBody, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    void shouldReturnOK200AndValidBodyOnRequestWithAllValidParamsForOneYear() throws JSONException, IOException {
        String expectedResponseBody = getStringFromFile("/json/16358/ExpectedResponseAllParamsForOneYear.json");
        String actualResponseBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 52f)
                    .queryParam("longitude", 13f)
                    .queryParam("start_date", "2021-01-01")
                    .queryParam("end_date", "2021-12-31")
                    .queryParam("hourly", "temperature_2m", "relativehumidity_2m", "dewpoint_2m", "apparent_temperature", "pressure_msl", "surface_pressure", "precipitation", "rain", "snowfall", "cloudcover", "cloudcover_low", "cloudcover_mid", "cloudcover_high", "shortwave_radiation", "direct_radiation", "diffuse_radiation", "direct_normal_irradiance", "windspeed_10m", "windspeed_100m", "winddirection_10m", "winddirection_100m", "windgusts_10m", "et0_fao_evapotranspiration", "vapor_pressure_deficit", "soil_temperature_0_to_7cm", "soil_temperature_7_to_28cm", "soil_temperature_28_to_100cm", "soil_temperature_100_to_255cm", "soil_moisture_0_to_7cm", "soil_moisture_7_to_28cm", "soil_moisture_28_to_100cm", "soil_moisture_100_to_255cm")
                    .queryParam("daily", "temperature_2m_max", "temperature_2m_min", "apparent_temperature_max", "apparent_temperature_min", "sunrise", "sunset", "shortwave_radiation_sum", "precipitation_sum", "rain_sum", "snowfall_sum", "precipitation_hours", "windspeed_10m_max", "windgusts_10m_max", "winddirection_10m_dominant", "et0_fao_evapotranspiration")
                    .queryParam("timezone", "auto")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedResponseBody, actualResponseBody, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    void shouldReturnOK200AndValidBodyOnRequestWithAlternativeValidParams() throws JSONException, IOException {
        String expectedResponseBody = getStringFromFile("/json/16358/ExpectedResponseWithAlternativeParams.json");
        String actualResponseBody = baseRequestSpec
                .given()
                    .queryParam("latitude", 52f)
                    .queryParam("longitude", 13f)
                    .queryParam("start_date", "2022-01-01")
                    .queryParam("end_date", "2022-01-01")
                    .queryParam("daily", "temperature_2m_max", "precipitation_hours", "windspeed_10m_max")
                    .queryParam("timezone", "Europe/London")
                    .queryParam("temperature_unit", "fahrenheit")
                    .queryParam("windspeed_unit", "ms")
                    .queryParam("precipitation_unit", "inch")
                    .queryParam("timeformat", "unixtime")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .asString();
        assertEquals(expectedResponseBody, actualResponseBody, JsonUtils.getGenerationTimerCustomization());
    }

    @Test
    void shouldReturn400ErrorOnRequestWithInvalidTimezone() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", 52f)
                    .queryParam("longitude", 13f)
                    .queryParam("start_date", "2022-01-01")
                    .queryParam("end_date", "2022-01-01")
                    .queryParam("daily", "temperature_2m_max")
                    .queryParam("timezone", "null")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(actualResponse, getStringFromFile("/json/16358/ExpectedResultWithInvalidTimezone.json"));
    }

    @Test
    void shouldReturn400ErrorOnRequestWithoutTimezone() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", 52f)
                    .queryParam("longitude", 13f)
                    .queryParam("start_date", "2022-01-01")
                    .queryParam("end_date", "2022-01-01")
                    .queryParam("daily", "temperature_2m_max")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(actualResponse, getStringFromFile("/json/16358/ExpectedResponseWithoutTimezone.json"));
    }

    @Test
    void shouldReturn400ErrorOnRequestWithInvalidParamName() throws JSONException, IOException {
        String actualResponse = baseRequestSpec
                .given()
                    .queryParam("latitude", 52f)
                    .queryParam("longitude", 13f)
                    .queryParam("start_date", "2022-01-01")
                    .queryParam("end_date", "2022-01-01")
                    .queryParam("daily", "temperature_2m_maximus")
                    .queryParam("timezone", "auto")
                .when()
                    .get()
                .then()
                    .statusCode(400)
                .extract()
                    .asString();
        assertEquals(actualResponse, getStringFromFile("/json/16358/ExpectedResponseWithInfalidParamName.json"));
    }

}
