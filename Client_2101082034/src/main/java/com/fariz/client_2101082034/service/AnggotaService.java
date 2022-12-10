/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fariz.client_2101082034.service;

import com.fariz.client_2101082034.model.Anggota;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 *
 * @author Acer
 */
public class AnggotaService {
    private String url = "http://localhost:9001";
    
    public Anggota getAnggota(Long anggotaId){
        Anggota anggota = Unirest.get(url + "/anggota/"+anggotaId)
                .asObject(Anggota.class)
                .getBody();
        return anggota;
    }
    
    public Anggota saveAnggota(Anggota anggota){
        HttpResponse<JsonNode> response = Unirest.post(url + "/anggota")
                .header("Content-Type", "application/json")
                .body(anggota)
                .asJson();
        Gson gson = new Gson();
        return gson.fromJson(response.getBody().toString(), Anggota.class);
    }
}
