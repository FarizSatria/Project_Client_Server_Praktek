/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.gaji.service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Acer
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gaji {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gajiId;
    private Long karyawanId;
    private Long golonganId;
    private String tanggal;
    private int tunjanganAnak;
    private int tunjanganSuamiIstri;
    private double totalGaji;
}
