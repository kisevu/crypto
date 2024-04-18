package com.ameda.kisevu.crypto.utils;/*
*
@author ameda
@project crypto-ranking
*
*/

import com.ameda.kisevu.crypto.service.CoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartUp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CoinsService coinsService;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        coinsService.fetchCoins();
//        coinsService.fetchCoinHistory();
    }
}
