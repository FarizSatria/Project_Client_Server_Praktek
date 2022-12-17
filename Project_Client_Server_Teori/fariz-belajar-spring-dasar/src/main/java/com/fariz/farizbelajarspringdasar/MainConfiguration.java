/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar;

import com.fariz.farizbelajarspringdasar.configuration.BarConfiguration;
import com.fariz.farizbelajarspringdasar.configuration.FooConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Acer
 */
@Configuration
@Import({
    FooConfiguration.class,
    BarConfiguration.class
})
public class MainConfiguration {
    
}
