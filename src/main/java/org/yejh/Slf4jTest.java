package org.yejh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
    // private static final Logger LOG = Logger.getLogger(Log4jTest.class); // Log4J
    private static final Logger LOG = LoggerFactory.getLogger(Slf4jTest.class);

    private static void method1() {
        LOG.info("--- {} method1 ---", "info");
        LOG.debug("--- debug method1 ---");
    }

    public static void main(String[] args) {
        LOG.error("--- start--- ");
        Slf4jTest.method1();
        LOG.error("--- end ---");
    }
}
