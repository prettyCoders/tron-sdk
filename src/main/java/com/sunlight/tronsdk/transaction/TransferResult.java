package com.sunlight.tronsdk.transaction;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易广播结果
 * @author: sunlight
 * @date: 2020/7/27 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResult {
    /**
     * 交易Hash
     */
    private String hash;
    /**
     * 广播结果
     */
    private Boolean result;
    /**
     * 广播状态
     */
    private String code;
    /**
     * 消息
     */
    private String message;

    public static TransferResult parse(String result){
        JSONObject jsonResult=JSONObject.parseObject(result);
        return new TransferResult(
                jsonResult.getString("txid"),
                jsonResult.getBoolean("result"),
                jsonResult.getString("code"),
                jsonResult.getString("message")
        );
    }
}
