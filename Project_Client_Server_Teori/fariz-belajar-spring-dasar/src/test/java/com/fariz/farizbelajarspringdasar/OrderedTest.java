/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar;

import com.fariz.farizbelajarspringdasar.processor.IdGeneratorBeanPostProcessor;
import com.fariz.farizbelajarspringdasar.processor.PrefixIdGeneratorBeanPostProcessor;
import data.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Acer
 */
public class OrderedTest {
    
    @Configuration
    @Import({
        Car.class,
        IdGeneratorBeanPostProcessor.class,
        PrefixIdGeneratorBeanPostProcessor.class
    })
    public static class TestConfiguration{
        
    }
    
    private ConfigurableApplicationContext applicationContext;
    
    @BeforeEach
    void setUp(){
        applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);
        applicationContext.registerShutdownHook();
    }
    
    @Test
    void testCar(){
        Car car = applicationContext.getBean(Car.class);
        
        Assertions.assertNotNull(car.getId());
        Assertions.assertTrue(car.getId().startsWith("FARIZ-"));
    }
}
