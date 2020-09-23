package com.sunlight.tronsdk.constant;

/**
 * TRC20 方法枚举
 * @author: sunlight
 * @date: 2020/9/17 17:40
 */
public enum  Trc20Method {
    /**
     * TRC20 transfer
     */
    TRANSFER("transfer(address,uint256)", "TRC20 transfer"),

    /**
     * TRC20 transferFrom
     */
    TRANSFER_FROM("transferFrom(address,address,uint256)", "TRC20 transferFrom");

    private final String method;
    private final String description;

    Trc20Method(String method, String description) {
        this.method = method;
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }
}
