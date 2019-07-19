package io.keikai.demo;

import io.keikai.client.api.Spreadsheet;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Constants and configuration string.
 */
public class Configuration {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Configuration.class);
    /**
     * demo version, used to identify this application status at runtime
     */
    public static final String DEFAULT_KEIKAI_SERVER = "http://127.0.0.1:8888";
    public static final String INTERNAL_FILE_FOLDER = "book";
    public static final String KEIKAI_JS = "keikaiJs";
    public static final String XLSX = ".xlsx";

    public static final String KEIKAI_PROPERTIES = "keikai.properties";
    public static String urlForJava;
    public static String urlForBrowser;


    static public String getDefaultFileFolder() {
        return "/WEB-INF/" + INTERNAL_FILE_FOLDER + "/";
    }

    static public void loadKeikaiServerProperties() {
        try {
            InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream(KEIKAI_PROPERTIES);
            if (inputStream != null) {
                Properties prop = new Properties();
                prop.load(inputStream);
                urlForJava = prop.get("url-for-java").toString();
                urlForBrowser = prop.get("url-for-browser").toString();
            }
        } catch (IOException e) {
            logger.warn("failed to load keikai.properties", e);
        }
    }

    static public String getUriForBrowser(Spreadsheet spreadsheet, String elementId){
        return spreadsheet.getURI(elementId).replace(Configuration.urlForJava.replace("http:", ""), Configuration.urlForBrowser);
    }
}
