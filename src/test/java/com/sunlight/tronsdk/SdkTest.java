package com.sunlight.tronsdk;

import com.alibaba.fastjson.JSON;
import com.sunlight.tronsdk.transaction.TransferResult;
import com.sunlight.tronsdk.trc10.Trc10Helper;
import com.sunlight.tronsdk.trc20.Trc20Helper;
import com.sunlight.tronsdk.trx.TrxHelper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.protos.contract.Common;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: sunlight
 * @date: 2020/7/29 10:43
 */
public class SdkTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SdkTest.class);
    private final String senderPrivateKey = "";
    private final String receiverAddress = "";

    @Test
    @Before
    public void setup() {
        SdkConfig.getInstance().setNodeServer("http://52.53.189.99:8090");
    }

    /**
     * 转TRX
     *
     * @throws Exception
     */
    @Test
    public void testSendTrxTransaction() throws Exception {
        TransferResult resultBo = TrxHelper.transfer(senderPrivateKey, receiverAddress, BigDecimal.valueOf(0.1));
        LOGGER.info("resultBo:" + JSON.toJSONString(resultBo));
    }

    /**
     * 转TRC10
     *
     * @throws Exception
     */
    @Test
    public void testSendTrc10Transaction() throws Exception {
        //assetName 可以查TRC10转账记录获得,暂时不支持使用assetId
        String assetName = "31303032303030";
        Integer coinDecimal = 1000000;
        TransferResult resultBo = Trc10Helper.transfer(senderPrivateKey, receiverAddress, BigDecimal.ONE, assetName, coinDecimal);
        LOGGER.info("resultBo:" + JSON.toJSONString(resultBo));
    }


    /**
     * 转TRC20
     *
     * @throws Exception
     */
    @Test
    public void sendTrc20TransactionTest() throws Exception {
        TransferResult resultBo = Trc20Helper.transfer(
                senderPrivateKey, receiverAddress, BigDecimal.ONE,
                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t", 10000000L);
        LOGGER.info("resultBo:" + JSON.toJSONString(resultBo));

    }

    /**
     * 查TRC20余额
     *
     * @throws Exception
     */
    @Test
    public void trc20BalanceOfTest() throws Exception {
        BigDecimal balance = Trc20Helper.balanceOf(
                "TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y",
                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"
        );
        LOGGER.info("balance:" + balance);

    }

    /**
     * 查TRC20小数位
     *
     * @throws Exception
     */
    @Test
    public void trc20DecimalTest() throws Exception {
        Integer decimals = Trc20Helper.decimals(
                "TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y",
                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"
        );
        LOGGER.info("decimals:" + decimals);
    }

    /**
     * 根据区块高度查区块数据
     *
     * @throws Exception
     */
    @Test
    public void getBlockByHeightTest() throws Exception {
        String data = TrxQuery.getBlockByHeight(
                BigInteger.valueOf(23495447)
        );
        LOGGER.info("data:" + data);
    }

    /**
     * 根据区块高度查区块数据
     *
     * @throws Exception
     */
    @Test
    public void getTransactionByIdTest() throws Exception {
        String data = TrxQuery.getTransactionById(
                "8ae0512630afe31226322d518e34a9eab2fe27ffa484dbf2c81cb484abc8f767"
        );
        LOGGER.info("data:" + data);
    }

    /**
     * 冻结TRX获取能量、带宽
     *
     * @throws Exception
     */
    @Test
    public void freezeBalanceTest() throws Exception {
        String result = TrxHelper.freezeBalance(senderPrivateKey, BigDecimal.ONE,Common.ResourceCode.ENERGY);
        LOGGER.info(result);

        result = TrxHelper.freezeBalance(
                senderPrivateKey, BigDecimal.ONE,"TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y",
                Common.ResourceCode.ENERGY);
        LOGGER.info(result);
    }

}