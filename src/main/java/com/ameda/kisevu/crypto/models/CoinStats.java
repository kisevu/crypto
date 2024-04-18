package com.ameda.kisevu.crypto.models;/*
*
@author ameda
@project crypto-ranking
*
*/

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CoinStats {
    private float total;
    private float referenceCurrencyRate;
    private float totalCoins;
    private float totalMarkets;
    private float totalExchanges;
    private String totalMarketCap;
    private String total24hVolume;
    private float btcDominance;
    private List<CoinInfo> bestCoins = new ArrayList<>();
    private List<CoinInfo> newestCoins = new ArrayList<>();
}
