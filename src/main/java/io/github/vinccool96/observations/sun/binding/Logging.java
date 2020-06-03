package io.github.vinccool96.observations.sun.binding;

import sun.util.logging.PlatformLogger;

public class Logging {

    private static class LoggerHolder {

        private static final PlatformLogger INSTANCE =
                PlatformLogger.getLogger("io.github.vinccool96.observations.beans");

    }

    public static PlatformLogger getLogger() {
        return LoggerHolder.INSTANCE;
    }

}
