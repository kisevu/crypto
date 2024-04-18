package com.ameda.kisevu.crypto.models;/*
*
@author ameda
@project crypto-ranking
*
*/

import lombok.Data;

@Data
public class CoinPriceHistory {
    private String status;
    private CoinPriceHistoryData data;
}
