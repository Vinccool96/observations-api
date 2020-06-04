package io.github.vinccool96.observations.sun.binding;

import java.util.logging.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ErrorLoggingUtiltity {

    static {
        // initialize PlatformLogger
        Logging.getLogger();
    }

    // getLogManager will redirect existing PlatformLogger to the Logger
    private static final Logger logger =
            LogManager.getLogManager().getLogger("io.github.vinccool96.observations.beans");

    Level level;

    LogRecord lastRecord;

    Handler handler = new Handler() {

        @Override
        public void publish(LogRecord record) {
            lastRecord = record;
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    };

    public void start() {
        reset();
        level = logger.getLevel();
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);
    }

    public void stop() {
        logger.setLevel(level);
        logger.removeHandler(handler);
    }

    public void reset() {
        lastRecord = null;
    }

    public void checkFine(Class expectedException) {
        check(Level.FINE, expectedException);
    }

    public void check(Level expectedLevel, Class expectedException) {
        assertNotNull(lastRecord);
        assertEquals(expectedLevel, lastRecord.getLevel());
        assertEquals(expectedException, lastRecord.getThrown().getClass());
        reset();
    }

    public boolean isEmpty() {
        return lastRecord == null;
    }

}
