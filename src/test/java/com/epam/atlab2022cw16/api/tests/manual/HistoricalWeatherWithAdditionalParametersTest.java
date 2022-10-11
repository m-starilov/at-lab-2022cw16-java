package com.epam.atlab2022cw16.api.tests.manual;

import com.epam.atlab2022cw16.api.tests.AbstractAPIBaseTest;
import com.epam.atlab2022cw16.api.utils.DataUtils;
import com.epam.atlab2022cw16.api.utils.JsonUtils;
import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Tags({
        @Tag("api"),
        @Tag("manual")
})
@JiraTicketsLink(id = 16312,
        description = "Test API endpoint (https://archive-api.open-meteo.com/v1/era5) with additional params (&hourly= , &daily=)",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16312")
public class HistoricalWeatherWithAdditionalParametersTest extends AbstractAPIBaseTest {

    private RequestSpecification withHourlyAndDailyParamsRequestSpec;
    private RequestSpecification baseRequestSpec;

    @BeforeEach
    public void initTest() {
        baseRequestSpec = RestAssured.given(new RequestSpecBuilder()
                .setBaseUri("https://archive-api.open-meteo.com")
                .setBasePath("v1/era5")
                .setContentType(ContentType.JSON)
                .addParam("latitude", 52f)
                .addParam("longitude", 13f)
                .build());
        withHourlyAndDailyParamsRequestSpec = RestAssured.given(baseRequestSpec)
                .param("hourly", "temperature_2m", "relativehumidity_2m", "dewpoint_2m", "apparent_temperature",
                        "pressure_msl", "surface_pressure", "precipitation", "rain", "snowfall", "cloudcover", "cloudcover_low",
                        "cloudcover_mid", "cloudcover_high", "shortwave_radiation", "direct_radiation", "diffuse_radiation",
                        "direct_normal_irradiance", "windspeed_10m", "windspeed_100m", "winddirection_10m", "winddirection_100m",
                        "windgusts_10m", "et0_fao_evapotranspiration", "vapor_pressure_deficit", "soil_temperature_0_to_7cm",
                        "soil_temperature_7_to_28cm", "soil_temperature_28_to_100cm", "soil_temperature_100_to_255cm",
                        "soil_moisture_0_to_7cm", "soil_moisture_7_to_28cm", "soil_moisture_28_to_100cm",
                        "soil_moisture_100_to_255cm")
                .param("daily", "temperature_2m_max", "temperature_2m_min", "apparent_temperature_max",
                        "apparent_temperature_min", "sunrise", "sunset", "shortwave_radiation_sum", "precipitation_sum", "rain_sum",
                        "snowfall_sum", "precipitation_hours", "windspeed_10m_max", "windgusts_10m_max", "winddirection_10m_dominant",
                        "et0_fao_evapotranspiration");
    }

    @Test
    public void requestWithValidTimezoneParameterForOneDay() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/historicalWeatherWithAllParamsForOneDay.json");
        Response response = withHourlyAndDailyParamsRequestSpec
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-01")
                .queryParam("timezone", "auto")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expectedBody, response.getBody().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void requestWithValidTimezoneParameterForOneMonth() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/historicalWeatherAllParamsForMonth.json");
        Response response = withHourlyAndDailyParamsRequestSpec
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-31")
                .queryParam("timezone", "auto")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expectedBody, response.getBody().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void requestWithValidTimezoneParameterForOneYear() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/historicalWeatherAllParamsForOneYear.json");
        Response response = withHourlyAndDailyParamsRequestSpec
                .queryParam("start_date", "2021-01-01")
                .queryParam("end_date", "2021-12-31")
                .queryParam("timezone", "auto")
                .get()
                .andReturn();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expectedBody, response.getBody().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void requestWithAlternateParametersUnits() throws JSONException, IOException {
        String expectedBody = DataUtils.getStringFromFile("/json/historicalWeatherWithAlternativeParams.json");
        Response response = baseRequestSpec
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-01")
                .queryParam("daily", "temperature_2m_max","precipitation_hours","windspeed_10m_max")
                .queryParam("timezone", "Europe/London")
                .queryParam("temperature_unit", "fahrenheit")
                .queryParam("windspeed_unit", "ms")
                .queryParam("precipitation_unit", "inch")
                .queryParam("timeformat", "unixtime")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expectedBody, response.getBody().asString(), JsonUtils.getComparatorForGenerationTime());
    }

    @Test
    public void requestWithInvalidTimezoneParameter() throws IOException, JSONException {
        String expectedBody = DataUtils.getStringFromFile("/json/400InvalidTimezone.json");
        Response response = withHourlyAndDailyParamsRequestSpec
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-01")
                .queryParam("timezone", "null")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody,response.getBody().asString());
    }

    @Test
    public void requestWithoutTimezoneParameter() throws IOException, JSONException {
        String expectedBody = DataUtils.getStringFromFile("/json/400TimezoneIsRequired.json");
        Response response = withHourlyAndDailyParamsRequestSpec
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-01")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody, response.getBody().asString());
    }

    @Test
    public void requestWithInvalidParameterName() throws IOException, JSONException {
        String expectedBody = DataUtils.getStringFromFile("/json/400CannotInitInvalidDailyVariable.json");
        Response response = baseRequestSpec
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-01")
                .queryParam("daily", "temperature2mmax")
                .queryParam("timezone", "auto")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody, response.getBody().asString(), JsonUtils.getComparatorForInvalidInitializationOfVariable());
    }
}
