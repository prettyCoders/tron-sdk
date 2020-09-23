package com.sunlight.tronsdk.trc20;

import com.alibaba.fastjson.JSONObject;
import com.sunlight.tronsdk.SdkConfig;
import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.context.HttpContext;
import com.sunlight.tronsdk.transaction.TransactionSender;
import com.sunlight.tronsdk.transaction.TransactionResult;
import com.sunlight.tronsdk.utils.TokenConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sunlight
 * @date: 2020/9/22 14:30
 */
public class Trc20Helper {

    public static TransactionResult transfer(
            String senderPrivateKey,
            String receiverAddress,
            BigDecimal value,
            String contractAddress,
            Long feeLimit) throws Exception {
        String result = TransactionSender.sendTransaction(
                senderPrivateKey,
                new Trc20TransferBuilder(
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey),
                        receiverAddress,
                        value,
                        contractAddress,
                        feeLimit
                )
        );
        return TransactionResult.parse(result);
    }

    public static BigDecimal balanceOf(String ownerAddress,  String contractAddress) throws Exception {
        String method = "balanceOf(address)";
        String response = triggerSmartContract(
                contractAddress,
                method,
                ownerAddress,
                "0000000000000000000000" + AddressHelper.toHexString(ownerAddress));
        JSONObject result = JSONObject.parseObject(response);
        String hexValue = result.getJSONArray("constant_result").getString(0);
        return TokenConverter.tokenHexValueToBigDecimal(hexValue, decimals(ownerAddress,contractAddress));
    }

    public static Integer decimals(String ownerAddress, String contractAddress) throws Exception {
        String method = "decimals()";
        String response = triggerSmartContract(
                contractAddress,
                method,
                ownerAddress,
                null);
        JSONObject result = JSONObject.parseObject(response);
        String hexValue = result.getJSONArray("constant_result").getString(0);
        return TokenConverter.tokenHexValueToBigInteger(hexValue).intValue();
    }

    private static String triggerSmartContract(String contractAddress, String method, String ownerAddress, String parameter) throws Exception {
        Map<String, String> params = new HashMap<>();
        String hexOwnerAddress = AddressHelper.toHexString(ownerAddress);
        String hexContractAddress = AddressHelper.toHexString(contractAddress);
        params.put("contract_address", hexContractAddress);
        params.put("function_selector", method);
        params.put("parameter", parameter);
        params.put("owner_address", hexOwnerAddress);
        try {
            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
            String route = "/wallet/triggersmartcontract";
            ResponseEntity<String> responseEntity = HttpContext.restTemplate.exchange(
                    SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }
}
