package tron.transaction;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tron.sdk.TrxSdk;
import tron.sdk.SdkConfig;
import tron.transaction.trc10.Trc10TransferBuilder;
import tron.transaction.trc20.Trc20TransferBuilder;
import tron.transaction.trx.TrxTransferBuilder;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/7/29 10:43
 */
public class SendTransactionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendTransactionTest.class);
    private String senderAddress = "";
    private String senderPrivateKey = "";
    private String receiverAddress = "";

    private TrxSdk trxSdk = new TrxSdk();

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
        TransactionSendResult resultBo = trxSdk.sendTransaction(
                senderPrivateKey,
                new TrxTransferBuilder(senderAddress, receiverAddress, BigDecimal.valueOf(30)));
        LOGGER.info("resultBo:" + JSON.toJSONString(resultBo));

    }

    /**
     * 转TRC10
     *
     * @throws Exception
     */
    @Test
    public void testSendTrc10Transaction() throws Exception {
        String assetId = "31303032303030";
        Integer coinDecimal = 1000000;
        TransactionSendResult resultBo = trxSdk.sendTransaction(
                senderPrivateKey,
                new Trc10TransferBuilder(senderAddress, receiverAddress, BigDecimal.ONE, assetId, coinDecimal)
        );
        LOGGER.info("resultBo:" + JSON.toJSONString(resultBo));
    }


    /**
     * 转TRC20
     *
     * @throws Exception
     */
    @Test
    public void sendTrc20TransactionTest() throws Exception {
        String trc20Usdt = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        TransactionSendResult resultBo = trxSdk.sendTransaction(
                senderPrivateKey,
                new Trc20TransferBuilder(
                        senderAddress,
                        receiverAddress,
                        BigDecimal.ONE,
                        trc20Usdt,
                        6
                )
        );
        LOGGER.info("resultBo:" + JSON.toJSONString(resultBo));
    }
}