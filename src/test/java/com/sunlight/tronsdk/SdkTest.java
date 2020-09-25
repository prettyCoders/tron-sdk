package com.sunlight.tronsdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunlight.tronsdk.address.AccountResource;
import com.sunlight.tronsdk.transaction.TransactionResult;
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
        TransactionResult transactionResult = TrxHelper.transfer(senderPrivateKey, receiverAddress, BigDecimal.valueOf(10));
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));
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
        TransactionResult transactionResult = Trc10Helper.transfer(senderPrivateKey, receiverAddress, BigDecimal.ONE, assetName, coinDecimal);
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));
    }


    /**
     * 转TRC20
     *
     * @throws Exception
     */
    @Test
    public void sendTrc20TransactionTest() throws Exception {
        TransactionResult transactionResult = Trc20Helper.transfer(
                senderPrivateKey, receiverAddress, BigDecimal.valueOf(0.1),
                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t", null);
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));

    }

    /**
     * 查TRX余额
     *
     * @throws Exception
     */
    @Test
    public void getTrxBalanceTest() throws Exception {
        BigDecimal balance = TrxQuery.getTrxBalance("TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y");
        LOGGER.info("balance:" + balance);
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
     * 查询最新区块区块数据
     *
     * @throws Exception
     */
    @Test
    public void getLatestBlockTest() throws Exception {
        String data = TrxQuery.getLatestBlock();
        LOGGER.info("data:" + data);
    }

    /**
     * 根据区块高度查区块数据
     *
     * @throws Exception
     */
    @Test
    public void getBlockByHeightTest() throws Exception {
        String data = TrxQuery.getBlockByHeight(
                BigInteger.valueOf(23516058)
        );
        LOGGER.info("data:" + data);
    }

    /**
     * 根据交易ID交易数据
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
     * 根据交易ID交易信息（包含所在区块高度，可以用来计算确认数）
     *
     * @throws Exception
     */
    @Test
    public void getTransactionInfoByIdTest() throws Exception {
        String data = TrxQuery.getTransactionInfoById(
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
        //能量
        TransactionResult transactionResult = TrxHelper.freezeBalance(senderPrivateKey, BigDecimal.ONE, Common.ResourceCode.ENERGY);
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));

        transactionResult = TrxHelper.freezeBalance(
                senderPrivateKey, BigDecimal.ONE, "TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y",
                Common.ResourceCode.ENERGY);
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));

        //带宽
        transactionResult = TrxHelper.freezeBalance(
                senderPrivateKey, BigDecimal.ONE, Common.ResourceCode.BANDWIDTH);
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));
    }

    /**
     * 解冻TRX
     *
     * @throws Exception
     */
    @Test
    public void unFreezeBalanceTest() throws Exception {
        TransactionResult transactionResult = TrxHelper.unFreezeBalance(senderPrivateKey, Common.ResourceCode.ENERGY);
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));

        transactionResult = TrxHelper.unFreezeBalance(senderPrivateKey, Common.ResourceCode.ENERGY,
                "TT7bh9H6o8hVXXQ4L3q5Cp17LASmW9ud2y");
        LOGGER.info("transactionResult:" + JSON.toJSONString(transactionResult));
    }

    /**
     * 查询账户资源情况
     *
     * @throws Exception
     */
    @Test
    public void getAccountResourceTest() throws Exception {
        String address="TUaLK2ZX7LzYEgWYrEyXW15P9CBCXDtgaf";
        //查询所有数据
        AccountResource accountResource = TrxQuery.getAccountResource(address);
        LOGGER.info("accountResource:" + JSON.toJSONString(accountResource));

        //查询单个数据
        LOGGER.info("NetBalance:"+TrxQuery.getAddressNetBalance(address));
        LOGGER.info("EnergyBalance:"+TrxQuery.getAddressEnergyBalance(address));
        LOGGER.info("NetRate:"+TrxQuery.getNetRate(address));
        LOGGER.info("EnergyRate:"+TrxQuery.getEnergyRate(address));
    }
}