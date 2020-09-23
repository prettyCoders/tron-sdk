package com.sunlight.tronsdk.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: sunlight
 * @date: 2020/9/23 11:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResource {
    /**
     * 带宽使用量
     */
    private Long freeNetUsed;
    /**
     * 带宽总量
     */
    private Long freeNetLimit;
    /**
     * 能量使用量
     */
    private Long energyUsed;
    /**
     * 能量总量
     */
    private Long energyLimit;
    /**
     * 全网带宽总量
     */
    private Long totalNetLimit;
    /**
     * 全网带宽权重
     */
    private Long totalNetWeight;
    /**
     * 全网能量总量
     */
    private Long totalEnergyLimit;
    /**
     * 全网能量权重
     */
    private Long totalEnergyWeight;
}
