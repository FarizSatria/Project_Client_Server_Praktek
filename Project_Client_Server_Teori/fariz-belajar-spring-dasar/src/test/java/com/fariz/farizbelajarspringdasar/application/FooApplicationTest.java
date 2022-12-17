/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar.application;

import data.Foo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Acer
 */
@SpringBootTest(classes = FooApplication.class)
public class FooApplicationTest {
    
    @Autowired
    Foo foo;
    
    @Test
    void testSpringBoot(){
        Assertions.assertNotNull(foo);
    }
}
