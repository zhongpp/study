package com.zpp.springboot.start.listeners;

import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by Format
 * on 2017/5/1.
 */
public class SimpleApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationPreparedEvent) {
            System.out.println("===== custom prepare event in initializer");
        } else if(event instanceof ApplicationStartingEvent) {
            System.out.println("===== custom started event in initializer");
        } else if(event instanceof ApplicationReadyEvent) {
            System.out.println("===== custom ready event in initializer");
        } else if (event instanceof ApplicationFailedEvent) {
            System.out.println("===== custom faile event in initializer");
        }
    }
}