package com.ameda.kisevu.crypto.config;/*
*
@author ameda
@project crypto-ranking
*
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomWebConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
