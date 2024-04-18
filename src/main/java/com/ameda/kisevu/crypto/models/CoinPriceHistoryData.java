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
public class CoinPriceHistoryData {
    private String change;
    private List<CoinPriceHistoryExchangeRate> history = new ArrayList<> ();
}
