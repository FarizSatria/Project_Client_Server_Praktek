/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar.runnerapp;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Acer
 */
@Slf4j
@Component
public class SimpleRunner implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> profiles = args.getOptionValues("profiles");
        log.info("Profiles : {}", profiles);
    }
    
}
