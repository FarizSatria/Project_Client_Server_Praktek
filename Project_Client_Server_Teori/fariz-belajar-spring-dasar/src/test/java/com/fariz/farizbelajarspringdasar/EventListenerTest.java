/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar;

import com.fariz.farizbelajarspringdasar.listener.LoginAgainSuccessListener;
import com.fariz.farizbelajarspringdasar.listener.LoginSuccessListener;
import com.fariz.farizbelajarspringdasar.listener.UserListener;
import com.fariz.farizbelajarspringdasar.service.UserService;
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
public class EventListenerTest {
    
    @Configuration
    @Import({
        UserService.class,
        LoginSuccessListener.class,
        LoginAgainSuccessListener.class,
        UserListener.class
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
    void testEvent(){
        
        UserService userService = applicationContext.getBean(UserService.class);
        userService.login("fariz", "fariz");
        userService.login("fariz", "salah");
        userService.login("kurniawan", "salah");
    }
}
