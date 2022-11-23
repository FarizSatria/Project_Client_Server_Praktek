/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.buku.service.controller;

import com.fariz.buku.service.entity.Buku;
import com.fariz.buku.service.service.BukuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/buku")
public class BukuController {
    @Autowired
    private BukuService bukuService;
    
    @PostMapping("/")
    public Buku saveBuku(@RequestBody Buku buku){
        return bukuService.saveBuku(buku);
    }
    
    @GetMapping("/{id}")
    public Buku findBukuById(@PathVariable("id") Long bukuId){
        return bukuService.findBukuById(bukuId);
    }
}
