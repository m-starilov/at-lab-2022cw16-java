package com.epam.atlab2022cw16.api.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileUtils {

    public static String getStringFromFile(String fileName) throws IOException {
        URL path = FileUtils.class.getResource(fileName);
        assert path != null;
        return org.apache.commons.io.FileUtils.readFileToString(new File(path.getFile()), "utf-8");
    }
}
