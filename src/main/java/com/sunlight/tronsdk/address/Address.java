package com.sunlight.tronsdk.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址业务实体
 * @author: sunlight
 * @date: 2020/7/24 16:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 地址
     */
    private String address;
}
