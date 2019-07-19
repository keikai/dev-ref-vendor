package io.keikai.demo;

import java.util.logging.*;

public class KeikaiUtil {
    static public void enableSocketIOLog() {
        Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        log.setLevel(Level.FINER);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        log.addHandler(handler);
    }
}
