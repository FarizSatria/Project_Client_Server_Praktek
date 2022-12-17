/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar.commandapp;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Acer
 */
@Slf4j
@Component
public class LogCommandLineRunner implements CommandLineRunner{

    @Override
    public void run(String... args) throws Exception {
        log.info("Log Command Line Runner : {}", Arrays.toString(args));
    }
    
}
