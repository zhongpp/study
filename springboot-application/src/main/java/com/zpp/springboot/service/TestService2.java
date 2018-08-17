package com.zpp.springboot.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author pingpingZhong
 *         on 2017/5/23.
 */
@Service
public class TestService2 implements CommandLineRunner {
    public void run(String... args) throws Exception {
        System.out.println("spring boot running1" + this.getClass().getName());
    }
}
