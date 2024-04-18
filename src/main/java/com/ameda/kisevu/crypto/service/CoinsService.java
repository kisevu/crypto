package com.ameda.kisevu.crypto.service;/*
*
@author ameda
@project crypto-ranking
*
*/

import com.ameda.kisevu.crypto.models.*;
import com.ameda.kisevu.crypto.utils.HttpUtils;
import io.github.dengliming.redismodule.redisjson.RedisJSON;
import io.github.dengliming.redismodule.redisjson.args.GetArgs;
import io.github.dengliming.redismodule.redisjson.args.SetArgs;
import io.github.dengliming.redismodule.redisjson.utils.GsonUtils;
import io.github.dengliming.redismodule.redistimeseries.DuplicatePolicy;
import io.github.dengliming.redismodule.redistimeseries.RedisTimeSeries;
import io.github.dengliming.redismodule.redistimeseries.Sample;
import io.github.dengliming.redismodule.redistimeseries.TimeSeriesOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class CoinsService {
    private static final String GET_ICONS_API
            ="https://coinranking1.p.rapidapi.com/coins?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=24h&tiers=1&orderBy=marketCap&orderDirection=desc&limit=50&offset=0";
    public static final String REDIS_KEY_COINS = "coins";
    public static final String COIN_HISTORY_API = "https://coinranking1.p.rapidapi.com/coin/";
    public static final List<String> timePeriods = List.of("24h","7d","30d","3m","1y","3y","5y");
    public static final String COIN_HISTORY_TIME_PERIOD_PARAM="/history?timePeriod=";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisJSON redisJSON;
    @Autowired
    private RedisTimeSeries redisTimeSeries;


    public void fetchCoins(){
        log.info("inside fetch coins:{}","fetch coins from external API");
        ResponseEntity<Coins> coinsEntity = restTemplate.exchange(GET_ICONS_API,
                HttpMethod.GET,
                HttpUtils.getHttpEntity(),
                Coins.class);
        //store coins to redis json...
        storeCoinsToRedisJSON(coinsEntity.getBody());
    }
    private void storeCoinsToRedisJSON(Coins coins){
        redisJSON.set(REDIS_KEY_COINS,
                SetArgs.Builder.create(".", GsonUtils.toJson(coins)));
    }



    public void fetchCoinHistory(){
        log.info("inside fetch coin history:{}",true);
        List<CoinInfo>  allCoins = getAllCoinsFromRedisJSON();
        allCoins.forEach(coinInfo -> {
            timePeriods.forEach(s->{
                fetchCoinHistoryForTimePeriod(coinInfo,s);
            });
        });
    }

    private void fetchCoinHistoryForTimePeriod(CoinInfo coinInfo, String timePeriod) {
        log.info("Fetching coin history of {} for Time period:{}",coinInfo.getName(),timePeriod);
        String url = COIN_HISTORY_API + coinInfo.getUuid() + COIN_HISTORY_TIME_PERIOD_PARAM + timePeriod;
        ResponseEntity<CoinPriceHistory> coinPriceHistoryResponseEntity = restTemplate.exchange(url,
                HttpMethod.GET,
                HttpUtils.getHttpEntity(),
                CoinPriceHistory.class);
        log.info("Data fetched from API for coin History {} for time period {}:",coinInfo.getName(),timePeriod);
        storeCoinHistoryToRedisTimeSeries(coinPriceHistoryResponseEntity.getBody(),
                coinInfo.getSymbol(),
                timePeriod);
    }

    private void storeCoinHistoryToRedisTimeSeries(CoinPriceHistory coinPriceHistory, String symbol, String timePeriod) {
        log.info("Store coin history of {} for time Period {} into Redis Time series",symbol,timePeriod);
        List<CoinPriceHistoryExchangeRate> coinExchangeRate =
                coinPriceHistory.getData().getHistory();
        //format of storage:
        //Symbol:time period
        //BTC: 24h, BTC:1y, (ETH:3y) -> designates the key and under that key info and histroy is provided
        coinExchangeRate.stream()
                .filter(ch->ch.getPrice()!=null && ch.getTimestamp()!=null)
                .forEach(ch->{
                    redisTimeSeries.add(new Sample(symbol + ":"+timePeriod,
                            Sample.Value.of(Long.valueOf(ch.getTimestamp()),Double.valueOf(ch.getPrice()))),
                            new TimeSeriesOptions()
                                    .unCompressed()
                                    .duplicatePolicy(DuplicatePolicy.LAST));
                });
        log.info("storing coin history for time Period into Redis time series is completed successfully:{}","successfully.");
    }

    private List<CoinInfo> getAllCoinsFromRedisJSON() {
        CoinData coinData = redisJSON.get(REDIS_KEY_COINS,
                CoinData.class,
                new GetArgs().path(".data").indent("\t").newLine("\n").space(" "));
        return coinData.getCoins();
    }
}
