package io.keikai.demo.web;

import static io.keikai.demo.Configuration.loadKeikaiServerProperties;

import java.io.InputStream;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        readManifest(servletContextEvent.getServletContext());
        loadKeikaiServerProperties();
    }

    /* create manifest during building a WAR
     */
    private void readManifest(ServletContext servletContext) {
        try {
            InputStream inputStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
            servletContext.setAttribute("manifest", new Manifest(inputStream).getMainAttributes());
        } catch (Exception e) {
            logger.warn("failed to read MANIFEST.MF", e);
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}