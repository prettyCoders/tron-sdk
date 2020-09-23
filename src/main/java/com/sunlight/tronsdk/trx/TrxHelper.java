package com.sunlight.tronsdk.trx;

import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.transaction.Transaction;
import com.sunlight.tronsdk.transaction.TransferResult;
import org.tron.protos.contract.Common;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/22 14:11
 */
public class TrxHelper {

    public static TransferResult transfer(String senderPrivateKey, String receiveAddress, BigDecimal value) throws Exception {
        String result= Transaction.sendTransaction(
                senderPrivateKey,
                new TrxTransferBuilder(
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey),
                        receiveAddress,
                        value)
        );
       return TransferResult.parse(result);
    }

    public static String freezeBalance(String senderPrivateKey, BigDecimal frozenBalance, String receiverAddress,Common.ResourceCode resourceCode) throws Exception {
        return Transaction.sendTransaction(
                senderPrivateKey,
                new TrxFreezeBalanceBuilder(
                        frozenBalance,
                        receiverAddress,
                        resourceCode,
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey)
                )
        );
    }

    public static String freezeBalance(String senderPrivateKey, BigDecimal frozenBalance,Common.ResourceCode resourceCode) throws Exception {
        return Transaction.sendTransaction(
                senderPrivateKey,
                new TrxFreezeBalanceBuilder(
                        frozenBalance,
                        resourceCode,
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey)
                )
        );
    }
}
