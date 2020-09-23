package com.sunlight.tronsdk.trc20.decode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author: sunlight
 * @date: 2020/9/17 18:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferMessage {
    private String to;
    private BigInteger value;
}
