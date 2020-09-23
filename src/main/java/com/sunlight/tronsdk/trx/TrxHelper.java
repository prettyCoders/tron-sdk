package com.sunlight.tronsdk.trx;

import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.transaction.TransactionSender;
import com.sunlight.tronsdk.transaction.TransactionResult;
import org.tron.protos.contract.Common;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/22 14:11
 */
public class TrxHelper {

    public static TransactionResult transfer(String senderPrivateKey, String receiveAddress, BigDecimal value) throws Exception {
        String result= TransactionSender.sendTransaction(
                senderPrivateKey,
                new TrxTransferBuilder(
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey),
                        receiveAddress,
                        value)
        );
       return TransactionResult.parse(result);
    }

    public static TransactionResult freezeBalance(String senderPrivateKey, BigDecimal frozenBalance, String receiverAddress,Common.ResourceCode resourceCode) throws Exception {
        String result= TransactionSender.sendTransaction(
                senderPrivateKey,
                new TrxFreezeBalanceBuilder(
                        frozenBalance,
                        receiverAddress,
                        resourceCode,
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey)
                )
        );
        return TransactionResult.parse(result);
    }

    public static TransactionResult freezeBalance(String senderPrivateKey, BigDecimal frozenBalance,Common.ResourceCode resourceCode) throws Exception {
        String result= TransactionSender.sendTransaction(
                senderPrivateKey,
                new TrxFreezeBalanceBuilder(
                        frozenBalance,
                        resourceCode,
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey)
                )
        );
        return TransactionResult.parse(result);
    }

    public static TransactionResult unFreezeBalance(String senderPrivateKey,Common.ResourceCode resourceCode) throws Exception {
        String result= TransactionSender.sendTransaction(
                senderPrivateKey,
                new TrxUnFreezeBalanceBuilder(
                        resourceCode,
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey)
                )
        );
        return TransactionResult.parse(result);
    }

    public static TransactionResult unFreezeBalance(String senderPrivateKey,Common.ResourceCode resourceCode,String receiverAddress) throws Exception {
        String result= TransactionSender.sendTransaction(
                senderPrivateKey,
                new TrxUnFreezeBalanceBuilder(
                        receiverAddress,
                        resourceCode,
                        AddressHelper.privateKeyToBase58Address(senderPrivateKey)
                )
        );
        return TransactionResult.parse(result);
    }
}
