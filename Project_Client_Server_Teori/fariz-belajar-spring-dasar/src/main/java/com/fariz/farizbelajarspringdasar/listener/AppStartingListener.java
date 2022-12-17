/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author Acer
 */
@Slf4j
public class AppStartingListener implements ApplicationListener<ApplicationStartedEvent>{

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Application starting");
    }
    
}
