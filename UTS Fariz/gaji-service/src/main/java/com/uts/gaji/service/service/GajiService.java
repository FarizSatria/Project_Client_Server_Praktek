/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uts.gaji.service.service;

import com.uts.gaji.service.VO.Pegawai;
import com.uts.gaji.service.VO.ResponseTemplateVO;
import com.uts.gaji.service.entity.Gaji;
import com.uts.gaji.service.repository.GajiRepository;
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
    
    public ResponseTemplateVO getGaji(Long GajiId){
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Gaji gaji = 
                gajiRepository.findByGajiId(GajiId);
        Pegawai pegawai = 
        restTemplate.getForObject("http://localhost:9003/pegawai/"
                + gaji.getPegawaiNip(), Pegawai.class);
        vo.setGaji(gaji);
        vo.setPegawai(pegawai); 
        return vo;
    }
    
}
