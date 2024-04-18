package com.ameda.kisevu.crypto.controller;/*
*
@author ameda
@project crypto-ranking
*
*/

import com.ameda.kisevu.crypto.models.CoinInfo;
import com.ameda.kisevu.crypto.models.HistoryData;
import com.ameda.kisevu.crypto.service.CoinsService;
import com.ameda.kisevu.crypto.utils.Utility;
import io.github.dengliming.redismodule.redistimeseries.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/coins")
@Slf4j
public class CoinsRankingController {
    @Autowired
    private CoinsService coinsService;

    @GetMapping
    public ResponseEntity<List<CoinInfo>> fetchAllCoins(){
        return new ResponseEntity<>(
                coinsService.fetchCoinsFromRedisJSON(),
                HttpStatus.OK
        );
    }
    @GetMapping("/{symbol}/{timePeriod}")
    public ResponseEntity<List<HistoryData>> fetchCoinHistoryPerTimePeriod(
            @PathVariable("symbol") String symbol,
            @PathVariable("timePeriod") String timePeriod
    ){
        List<Sample.Value> coinsTimeSeriesData
                = coinsService.fetchCoinHistoryPerTimePeriodFromRedisTS(symbol,timePeriod);
        List<HistoryData> coinsHistory
                = coinsTimeSeriesData.stream()
                .map(v->new HistoryData(
                        Utility.convertUnixTimeToDate(v.getTimestamp()),
                        Utility.round(v.getValue(),2)
                )).toList();
        return new ResponseEntity<>(coinsHistory,HttpStatus.OK);
    }
}
