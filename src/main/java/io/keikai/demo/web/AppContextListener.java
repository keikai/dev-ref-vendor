package io.keikai.demo.web;

import static io.keikai.demo.Configuration.loadKeikaiServerProperties;

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
        loadKeikaiServerProperties();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}