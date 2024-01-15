package com.ig.amr;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Test {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void log4j2LoggerTest(){
        System.out.println("안녕 난 println이야");
        logger.info("안녕 난 logger야");
        logger.error("안녕 난 logger야 error");
    }
}