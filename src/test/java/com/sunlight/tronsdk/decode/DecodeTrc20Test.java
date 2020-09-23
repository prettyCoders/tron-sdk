package com.sunlight.tronsdk.decode;

import com.alibaba.fastjson.JSON;
import com.sunlight.tronsdk.trc20.decode.TransferMessage;
import com.sunlight.tronsdk.trc20.decode.Trc20DataDecoder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * @author: sunlight
 * @date: 2020/9/18 10:44
 */
public class DecodeTrc20Test {
    private static final Logger LOGGER= LoggerFactory.getLogger(DecodeTrc20Test.class);
    @Test
    public void decoderTest() throws Exception {
        String transferData =
                "a9059cbb000000000000000000000000" +
                "bc106858d5597e8f860ab259199a3ebd3b541da7000000000000" +
                "00000000000000000000000000000000000000000000000f4240";
        String transferFromData =
                "23b872dd000000000000000000000000" +
                "bc106858d5597e8f860ab259199a3ebd3b541da7000000000000" +
                "295883812dd775615947718e88a8e6c7a8717cff000000000000" +
                "00000000000000000000000000000000000000000000000f4240";

        TransferMessage bo= Trc20DataDecoder.decode(transferData);
        Assert.assertEquals(bo.getTo(),"TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y");
        assert bo.getValue().equals(BigInteger.valueOf( 1000000L));
        LOGGER.info("transfer:\n"+JSON.toJSONString(bo));

        bo = Trc20DataDecoder.decode(transferFromData);
        Assert.assertEquals(bo.getTo(),"TDjpjxouXXUkTvLjaTAB6e2UdUFVt9PBsK");
        assert bo.getValue().equals(BigInteger.valueOf( 1000000L));
        LOGGER.info("transferFrom:\n"+JSON.toJSONString(bo));
    }
}
