package com.sunlight.tronsdk.trx;

import com.google.protobuf.ByteString;
import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.constant.CoinConstant;
import com.sunlight.tronsdk.transaction.TransactionDataBuilder;
import com.sunlight.tronsdk.transaction.TxCreator;
import com.sunlight.tronsdk.utils.TokenConverter;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.protos.contract.Common;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/7 16:43
 */
public class TrxTransferBuilder implements TransactionDataBuilder {
    private String senderAddress;
    private String receiverAddress;
    private BigDecimal value;

    private TrxTransferBuilder() {
    }

    public TrxTransferBuilder(String senderAddress, String receiverAddress, BigDecimal value) {
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.value = value;
    }

    /**
     * 构建TRX交易数据
     * @return 交易数据
     */
    @Override
    public byte[] build() throws Exception {
        byte[] from = AddressHelper.decodeFromBase58Check(senderAddress);
        byte[] to = AddressHelper.decodeFromBase58Check(receiverAddress);
        long amount = TokenConverter.tokenBigDecimalToBigInteger(value,CoinConstant.TRX_DECIMAL).longValue();
        Protocol.Transaction transaction = createTransferContractTransaction(from, to, amount);
        return transaction.toByteArray();
    }

    /**
     * 创建普通转账交易（转账TRX）
     *
     * @param from   发送方
     * @param to     接收方
     * @param amount 金额
     * @return Protocol.Transaction
     */
    private Protocol.Transaction createTransferContractTransaction(
            byte[] from, byte[] to, long amount) throws Exception {
        BalanceContract.TransferContract.Builder transferContractBuilder =
                createTransferContractBuilder(from, to, amount);
        return TxCreator.createTransaction(
                transferContractBuilder,
                Protocol.Transaction.Contract.ContractType.TransferContract,
                null
        );
    }


    /**
     * 创建普通转账交易的builder
     *
     * @param from   发送方
     * @param to     接收方
     * @param amount 金额
     * @return Builder
     */
    private BalanceContract.TransferContract.Builder createTransferContractBuilder(byte[] from, byte[] to, long amount) {
        BalanceContract.TransferContract.Builder transferContractBuilder = BalanceContract.TransferContract.newBuilder();
        transferContractBuilder.setAmount(amount);
        ByteString bsTo = ByteString.copyFrom(to);
        ByteString bsOwner = ByteString.copyFrom(from);
        transferContractBuilder.setToAddress(bsTo);
        transferContractBuilder.setOwnerAddress(bsOwner);
        return transferContractBuilder;
    }
}
