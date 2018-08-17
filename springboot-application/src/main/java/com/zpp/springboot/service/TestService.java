package com.zpp.springboot.service;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author pingpingZhong
 * on 2017/5/23.
 */
@Service
public class TestService implements CommandLineRunner {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestService.class);

    public void run(String... args) throws Exception {
        logger.trace("trace========msg");
        logger.debug("debug========msg");
        logger.info("info==========msg");
        logger.warn("warn==========msg");
        logger.error("error========msg");
        System.out.println("spring boot running"+this.getClass().getName());

    }
}
