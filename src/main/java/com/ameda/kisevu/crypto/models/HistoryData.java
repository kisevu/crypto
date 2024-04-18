package com.ameda.kisevu.crypto.models;/*
*
@author ameda
@project crypto-ranking
*
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryData {
    private String timestamp;
    private double value;
}
