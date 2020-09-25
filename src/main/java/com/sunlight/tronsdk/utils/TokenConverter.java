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
        return tokenBigIntegerToBigDecimal(rawValue,tokenDecimal);
    }

    public static BigInteger tokenBigDecimalToBigInteger(BigDecimal value,Integer tokenDecimal){
        return value.multiply(new BigDecimal(BigInteger.valueOf(10).pow(tokenDecimal))).toBigInteger();
    }

    public static BigDecimal tokenBigIntegerToBigDecimal(BigInteger value,Integer tokenDecimal){
        return new BigDecimal(value).divide(new BigDecimal(BigInteger.valueOf(10).pow(tokenDecimal)));
    }

    public static BigInteger tokenHexValueToBigInteger(String hexValue){
        return new BigInteger(hexValue,16);
    }
}

