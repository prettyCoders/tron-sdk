package com.sunlight.tronsdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunlight.tronsdk.context.HttpContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * TRX查询工具类
 *
 * @author: sunlight
 * @date: 2020/7/28 11:04
 */
public class TrxQuery {

    /**
     * 查询最新区块数据
     *
     * @return 数据
     * @throws Exception 异常
     */
    public static String getLatestBlock() throws Exception {
        HttpEntity<Object> httpEntity = new HttpEntity<>(HttpContext.standardHeaders);
        String route = "/wallet/getnowblock";
        try {
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getBlockByHeight(BigInteger height) throws Exception{
        Map<String, BigInteger> params = new HashMap<>();
        params.put("num", height);
        HttpEntity<Map<String, BigInteger>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
        String route = "/wallet/getblockbynum";
        try {
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        }catch (Exception e){
            throw e;
        }
    }

    public static String getTransactionById(String transactionId)throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("value", transactionId);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
        String route = "/wallet/gettransactionbyid";
        try {
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        }catch (Exception e){
            throw e;
        }
    }

}
