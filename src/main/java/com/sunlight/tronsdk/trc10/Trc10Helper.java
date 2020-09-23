package com.sunlight.tronsdk.trc10;

import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.transaction.Transaction;
import com.sunlight.tronsdk.transaction.TransferResult;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/22 14:25
 */
public class Trc10Helper {
    public static TransferResult transfer(
            String senderPrivateKey,
            String receiverAddress,
            BigDecimal value,
            String assetName,
            Integer coinDecimal
    ) throws Exception {
        String result = Transaction.sendTransaction(
                senderPrivateKey,
                new Trc10TransferBuilder(
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey),
                        receiverAddress,
                        value,
                        assetName,
                        coinDecimal
                )
        );
        return TransferResult.parse(result);
    }
}
