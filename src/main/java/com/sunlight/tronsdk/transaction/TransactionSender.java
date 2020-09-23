package com.sunlight.tronsdk.transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sunlight.tronsdk.context.HttpContext;
import com.sunlight.tronsdk.SdkConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * 离线签名转账SDK
 *
 * @author: sunlight
 * @date: 2020/7/29 9:29
 */
public class TransactionSender {

    /**
     * 转账（支持TRX、TRC10、TRC20转账）
     *
     * @param senderPrivateKey 发送方的私钥，十六进制
     * @return 转账结果
     * @throws Exception 异常
     */
    public static String sendTransaction(String senderPrivateKey, TransactionDataBuilder builder) throws Exception {
        byte[] transactionBytes = builder.build();
        byte[] signedTransactionBytes = signTransaction(transactionBytes, ByteArray.fromHexString(senderPrivateKey));
        return broadcast(signedTransactionBytes);
    }


    /**
     * 交易签名
     *
     * @param transactionBytes 待签名数据
     * @param privateKey       交易创建者私钥
     * @return 签名后的数据
     * @throws InvalidProtocolBufferException 异常
     */
    private static byte[] signTransaction(byte[] transactionBytes, byte[] privateKey) throws InvalidProtocolBufferException {
        Protocol.Transaction transaction = Protocol.Transaction.parseFrom(transactionBytes);
        byte[] rawData = transaction.getRawData().toByteArray();
        byte[] hash = Sha256Sm3Hash.hash(rawData);
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        byte[] sign = ecKey.sign(hash).toByteArray();
        return transaction.toBuilder().addSignature(ByteString.copyFrom(sign)).build().toByteArray();
    }

    /**
     * 调用节点Http接口广播交易
     *
     * @param signedTransactionBytes 交易数据
     * @return 交易广播结果
     */
    private static String broadcast(byte[] signedTransactionBytes){
        String signedDataStr = ByteArray.toHexString(signedTransactionBytes);
        Map<String, String> params = new HashMap<>(1);
        params.put("transaction", signedDataStr);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(params, HttpContext.standardHeaders);
        String route = "/wallet/broadcasthex";
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = HttpContext.restTemplate.exchange(SdkConfig.getInstance().getNodeServer() + route, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }
}
