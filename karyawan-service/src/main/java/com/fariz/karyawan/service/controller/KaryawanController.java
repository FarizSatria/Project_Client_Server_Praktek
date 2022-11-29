/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.karyawan.service.controller;

import com.fariz.karyawan.service.entity.Karyawan;
import com.fariz.karyawan.service.service.KaryawanService;
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
@RequestMapping("/karyawan")
public class KaryawanController {
    @Autowired
    private KaryawanService karyawanService;
    
    @PostMapping("/")
    public Karyawan saveKaryawan(@RequestBody Karyawan karyawan){
        return karyawanService.saveKaryawan(karyawan);
    }
    
    @GetMapping("/{id}")
    public Karyawan findKaryawanById(@PathVariable("id") Long karyawanId){
        return karyawanService.findKaryawanById(karyawanId);
    }
}
