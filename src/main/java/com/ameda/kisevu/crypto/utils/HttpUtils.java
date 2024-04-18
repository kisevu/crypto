package com.ameda.kisevu.crypto.utils;/*
*
@author ameda
@project crypto-ranking
*
*/

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class HttpUtils {

    private static String apiHost = "coinranking1.p.rapidapi.com";
    private static String apiKey = "fb4ac9ae71msh8d1f518cf7e9c18p1e4d53jsn06e21081b576";

    public  static HttpEntity<String> getHttpEntity(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("X-RapidAPI-Host",apiHost);
        httpHeaders.set("X-RapidAPI-Key",apiKey);
        return new HttpEntity<>(null,httpHeaders);
    }
}
