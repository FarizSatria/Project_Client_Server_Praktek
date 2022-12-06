/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.gaji.service.service;

import com.fariz.gaji.service.VO.Golongan;
import com.fariz.gaji.service.VO.Karyawan;
import com.fariz.gaji.service.VO.ResponseTemplateVO;
import com.fariz.gaji.service.entity.Gaji;
import com.fariz.gaji.service.repository.GajiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Acer
 */
@Service
public class GajiService {
    @Autowired
    private GajiRepository gajiRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Gaji saveGaji(Gaji gaji){
        return gajiRepository.save(gaji);
    }
    
    public ResponseTemplateVO getGaji(Long gajiId){
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Gaji gaji = 
                gajiRepository.findByGajiId(gajiId);
        Karyawan karyawan = 
        restTemplate.getForObject("http://localhost:9006/karyawan/"
                + gaji.getKaryawanId(), Karyawan.class);
        Golongan golongan = 
        restTemplate.getForObject("http://localhost:9007/golongan/" 
                + gaji.getGolonganId(), Golongan.class);
        vo.setGaji(gaji);
        vo.setKaryawan(karyawan); 
        vo.setGolongan(golongan); 
        return vo;
    }
    
}
