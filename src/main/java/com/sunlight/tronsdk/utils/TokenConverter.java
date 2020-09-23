package com.sunlight.tronsdk.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: sunlight
 * @date: 2020/9/22 16:09
 */
public class TokenConverter {

    public static BigDecimal tokenHexValueToBigDecimal(String hexValue, Integer tokenDecimal){
        BigInteger rawValue=new BigInteger(hexValue,16);
        return new BigDecimal(rawValue).divide(new BigDecimal(BigInteger.valueOf(10).pow(tokenDecimal)));
    }

    public static BigInteger tokenBigDecimalToBigInteger(BigDecimal value,Integer tokenDecimal){
        return value.toBigInteger().multiply(BigInteger.valueOf(10).pow(tokenDecimal));
    }

    public static BigInteger tokenHexValueToBigInteger(String hexValue){
        return new BigInteger(hexValue,16);
    }
}

