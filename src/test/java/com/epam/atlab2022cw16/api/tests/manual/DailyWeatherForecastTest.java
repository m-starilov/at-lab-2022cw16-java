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

import static com.epam.atlab2022cw16.api.utils.DateUtils.getISO8601CurrentDate;
import static com.epam.atlab2022cw16.api.utils.FileUtils.getStringFromFile;
import static com.epam.atlab2022cw16.api.utils.JsonUtils.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Tags({
        @Tag("api"),
        @Tag("manual")
})
@JiraTicketsLink(id = 16303,
        description = "Test API endpoint (https://api.open-meteo.com/v1/forecast) with daily parameters",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16303")
public class DailyWeatherForecastTest extends AbstractAPIBaseTest {

    @BeforeEach
    public void initTest() {
        baseRequestSpec
                .baseUri("https://api.open-meteo.com")
                .basePath("/v1/forecast")
                .param("latitude", 41.625f)
                .param("longitude", 41.625f);
    }

    @Test
    public void requestWithTimezoneParameter() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16303/ExpectedResponseMinimumDailyWeatherForecast.json");
        Response response = baseRequestSpec
                .queryParam("daily", "temperature_2m_max")
                .queryParam("timezone", "Europe/London")
                .get();
        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expected, response.getBody().asString(),
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization());
    }

    @Test
    public void requestWithTimezoneStartAndEndDatesParameters() throws JSONException, IOException {
        String expected = getStringFromFile("/json/16303/ExpectedResponseDailyWeatherForecastForOneDay.json");
        Response response = baseRequestSpec
                .queryParam("daily", "temperature_2m_max")
                .queryParam("timezone", "Europe/London")
                .queryParam("start_date", getISO8601CurrentDate())
                .queryParam("end_date", getISO8601CurrentDate())
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expected, response.getBody().asString(),
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization());
    }

    @Test
    public void requestWithTimezonePrecipitationUnitPastDaysAndAllDailyParameters() throws JSONException, IOException {
        String expected = getStringFromFile(
                "/json/16303/ExpectedResponseDailyWeatherForecastWithAllParametersFor10Days.json");
        Response response = baseRequestSpec
                .queryParam("daily", "weathercode", "temperature_2m_max", "temperature_2m_min",
                        "apparent_temperature_max", "apparent_temperature_min", "sunrise", "sunset", "precipitation_sum",
                        "rain_sum", "showers_sum", "snowfall_sum", "precipitation_hours", "windspeed_10m_max",
                        "windgusts_10m_max", "winddirection_10m_dominant", "shortwave_radiation_sum", "et0_fao_evapotranspiration")
                .queryParam("timezone", "Europe/London")
                .queryParam("precipitation_unit", "inch")
                .queryParam("past_days", 3)
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expected, response.getBody().asString(),
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyWeathercodeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMinSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMaxSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMinSizeCustomization(),
                        JsonUtils.getArrayDailySunriseSizeCustomization(),
                        JsonUtils.getArrayDailySunsetSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationSumSizeCustomization(),
                        JsonUtils.getArrayDailyRainSumSizeCustomization(),
                        JsonUtils.getArrayDailyShowersSumSizeCustomization(),
                        JsonUtils.getArrayDailySnowfallSumSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationHoursSizeCustomization(),
                        JsonUtils.getArrayDailyWindspeed10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWindgusts10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWinddirection10mDominantSizeCustomization(),
                        JsonUtils.getArrayDailyShortwaveRadiationSumSizeCustomization(),
                        JsonUtils.getArrayDailyEt0FaoEvapotranspirationSizeCustomization());
    }

    @Test
    public void requestWithTimezonePrecipitationUnitStartAndEndDatesAndAllDailyParameters() throws JSONException, IOException {
        String expected = getStringFromFile(
                "/json/16303/ExpectedResponseDailyWeatherForecastWithPrecipitationUnitInchAndAllDailyParametersForOneDay" +
                        ".json");
        Response response = baseRequestSpec
                .queryParam("daily", "weathercode", "temperature_2m_max", "temperature_2m_min",
                        "apparent_temperature_max", "apparent_temperature_min", "sunrise", "sunset", "precipitation_sum",
                        "rain_sum", "showers_sum", "snowfall_sum", "precipitation_hours", "windspeed_10m_max",
                        "windgusts_10m_max", "winddirection_10m_dominant", "shortwave_radiation_sum", "et0_fao_evapotranspiration")
                .queryParam("timezone", "Europe/London")
                .queryParam("precipitation_unit", "inch")
                .queryParam("start_date", getISO8601CurrentDate())
                .queryParam("end_date", getISO8601CurrentDate())
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
        assertEquals(expected, response.getBody().asString(),
                        JsonUtils.getGenerationTimerCustomization(),
                        JsonUtils.getArrayDailyTimeSizeCustomization(),
                        JsonUtils.getArrayDailyWeathercodeSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyTemperature2mMinSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMaxSizeCustomization(),
                        JsonUtils.getArrayDailyApparentTemperatureMinSizeCustomization(),
                        JsonUtils.getArrayDailySunriseSizeCustomization(),
                        JsonUtils.getArrayDailySunsetSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationSumSizeCustomization(),
                        JsonUtils.getArrayDailyRainSumSizeCustomization(),
                        JsonUtils.getArrayDailyShowersSumSizeCustomization(),
                        JsonUtils.getArrayDailySnowfallSumSizeCustomization(),
                        JsonUtils.getArrayDailyPrecipitationHoursSizeCustomization(),
                        JsonUtils.getArrayDailyWindspeed10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWindgusts10mMaxSizeCustomization(),
                        JsonUtils.getArrayDailyWinddirection10mDominantSizeCustomization(),
                        JsonUtils.getArrayDailyShortwaveRadiationSumSizeCustomization(),
                        JsonUtils.getArrayDailyEt0FaoEvapotranspirationSizeCustomization());
    }

    @Test
    public void requestWithoutTimezone() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16303/400TimezoneIsRequired.json");
        Response response = baseRequestSpec
                .queryParam("daily", "temperature_2m_max")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody, response.getBody().asString());
    }

    @Test
    public void requestWithInvalidDailyParameter() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16303/400CannotInitInvalidDailyVariable.json");
        Response response = baseRequestSpec
                .queryParam("daily", "temperature2mmax")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody, response.getBody().asString());
    }

    @Test
    public void requestWithAllInvalidDailyParameter() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16303/400CannotInitInvalidDailyVariable.json");
        Response response = baseRequestSpec
                .queryParam("daily", "weathercode", "temperature2mmax", "temperature2mmin",
                        "apparenttemperaturemax", "apparenttemperaturemin", "sunrise", "sunset", "precipitationsum",
                        "rainsum", "showerssum", "snowfallsum", "precipitationhours", "windspeed10mmax",
                        "windgusts10mmax", "winddirection10mdominant", "shortwaveradiationsum", "et0faoevapotranspiration")
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody, response.getBody().asString());
    }

    @Test
    public void requestWithTimezoneStartAndEndDatesAndAllInvalidDailyParameters() throws JSONException, IOException {
        String expectedBody = getStringFromFile("/json/16303/400CannotInitInvalidDailyVariable.json");
        Response response = baseRequestSpec
                .queryParam("daily", "weathercode", "temperature2mmax", "temperature2mmin",
                        "apparenttemperaturemax", "apparenttemperaturemin", "sunrise", "sunset", "precipitationsum",
                        "rainsum", "showerssum", "snowfallsum", "precipitationhours", "windspeed10mmax",
                        "windgusts10mmax", "winddirection10mdominant", "shortwaveradiationsum", "et0faoevapotranspiration")
                .queryParam("timezone", "Europe/London")
                .queryParam("start_date", getISO8601CurrentDate())
                .queryParam("end_date", getISO8601CurrentDate())
                .get();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
        assertEquals(expectedBody, response.getBody().asString());
    }
}
