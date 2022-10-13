package com.epam.atlab2022cw16.ui.utils;

import com.google.common.io.BaseEncoding;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class ScreenshotListener implements AfterTestExecutionCallback {

    private ExtensionContext context;

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        if (extensionContext.getExecutionException().isPresent() && extensionContext.getTags().contains("ui")) {
            context = extensionContext;
            saveScreenshot();
        }
    }

    private void saveScreenshot() {
        byte[] byteScreenshot = ((TakesScreenshot) EnvironmentUtils.getDriver()).getScreenshotAs(OutputType.BYTES);
        File pngScreenshot = new File(".//target/screenshots/" + getScreenshotName() + ".png");
        try {
            FileUtils.writeByteArrayToFile(pngScreenshot, byteScreenshot);
            log.debug("RP_MESSAGE#BASE64#{}#{}",
                    BaseEncoding.base64().encode(byteScreenshot), pngScreenshot.getName());
            log.info("Screenshot added " + pngScreenshot.getPath());
        } catch (IOException e) {
            log.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }

    private String getScreenshotName() {
        String name = context.getRequiredTestClass().getSimpleName();
        name = name + "#" + context.getRequiredTestMethod().getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
        return name + "#" + ZonedDateTime.now().format(formatter);
    }

}
