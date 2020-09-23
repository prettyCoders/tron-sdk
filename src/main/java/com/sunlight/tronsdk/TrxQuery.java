package com.sunlight.tronsdk;

import com.alibaba.fastjson.JSONObject;
import com.sunlight.tronsdk.address.AccountResource;
import com.sunlight.tronsdk.address.AddressHelper;
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

    public static String getBlockByHeight(BigInteger height) throws Exception {
        Map<String, BigInteger> params = new HashMap<>();
        params.put("num", height);
        HttpEntity<Map<String, BigInteger>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
        String route = "/wallet/getblockbynum";
        try {
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getTransactionById(String transactionId) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("value", transactionId);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
        String route = "/wallet/gettransactionbyid";
        try {
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public static AccountResource getAccountResource(String address) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("address", AddressHelper.toHexString(address));
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
        String route = "/wallet/getaccountresource";
        try {
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            JSONObject result = JSONObject.parseObject(responseEntity.getBody());
            Long freeNetUsed = result.getLong("freeNetUsed");
            Long freeNetLimit = result.getLong("freeNetLimit");
            Long energyUsed = result.getLong("EnergyUsed");
            Long energyLimit = result.getLong("EnergyLimit");
            Long totalNetLimit = result.getLong("TotalNetLimit");
            Long totalNetWeight = result.getLong("TotalNetWeight");
            Long totalEnergyLimit = result.getLong("TotalEnergyLimit");
            Long totalEnergyWeight = result.getLong("TotalEnergyWeight");
            return new AccountResource(
                    freeNetUsed == null ? 0L : freeNetUsed,
                    freeNetLimit == null ? 0L : freeNetLimit,
                    energyUsed == null ? 0L : energyUsed,
                    energyLimit == null ? 0L : energyLimit,
                    totalNetLimit == null ? 0L : totalNetLimit,
                    totalNetWeight == null ? 0L : totalNetWeight,
                    totalEnergyLimit == null ? 0L : totalEnergyLimit,
                    totalEnergyWeight == null ? 0L : totalEnergyWeight
            );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 查询地址带宽余额
     * @param address 地址（API设计成这样,莫法）
     * @return
     * @throws Exception
     */
    public static Long getAddressNetBalance(String address) throws Exception {
        AccountResource accountResource=getAccountResource(address);
        return accountResource.getFreeNetLimit()-accountResource.getFreeNetUsed();
    }

    /**
     * 查询地址能量余额
     * @param address 地址（API设计成这样,莫法）
     * @return
     * @throws Exception
     */
    public static Long getAddressEnergyBalance(String address) throws Exception {
        AccountResource accountResource=getAccountResource(address);
        return accountResource.getEnergyLimit()-accountResource.getEnergyUsed();
    }

    /**
     * 查询带宽费率,也就是冻结1个TRX能换取多少资源
     * @param address 地址（API设计成这样,莫法）
     * @return
     * @throws Exception
     */
    public static Long getNetRate(String address) throws Exception {
        AccountResource accountResource=getAccountResource(address);
        return accountResource.getTotalNetLimit()/accountResource.getTotalNetWeight();
    }

    /**
     * 查询能量费率,也就是冻结1个TRX能换取多少资源
     * @param address 地址（API设计成这样,莫法）
     * @return
     * @throws Exception
     */
    public static Long getEnergyRate(String address) throws Exception {
        AccountResource accountResource=getAccountResource(address);
        return accountResource.getTotalEnergyLimit()/accountResource.getTotalEnergyWeight();
    }


}
