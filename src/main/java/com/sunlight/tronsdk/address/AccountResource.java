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
}
