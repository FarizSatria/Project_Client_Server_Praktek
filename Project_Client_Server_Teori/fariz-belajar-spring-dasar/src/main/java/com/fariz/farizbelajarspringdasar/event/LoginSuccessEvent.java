/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.farizbelajarspringdasar.event;

import data.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Acer
 */
public class LoginSuccessEvent extends ApplicationEvent{
    
    @Getter
    private final User user;
    
    public LoginSuccessEvent(User user){
        super(user);
        this.user = user;
    }
    
}
