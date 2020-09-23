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
public class TrxUnFreezeBalanceBuilder implements TransactionDataBuilder {
    /**
     * 目前只支持3天
     */
    private static final Long DEFAULT_FROZEN_DURATION = 3L;

    private String receiverAddress;
    private Common.ResourceCode resourceCode;
    private String ownerAddress;

    private TrxUnFreezeBalanceBuilder() {
    }

    public TrxUnFreezeBalanceBuilder(Common.ResourceCode resourceCode, String ownerAddress) {
        this.resourceCode = resourceCode;
        this.ownerAddress = ownerAddress;
    }

    public TrxUnFreezeBalanceBuilder(String receiverAddress, Common.ResourceCode resourceCode, String ownerAddress) {
        this.receiverAddress = receiverAddress;
        this.resourceCode = resourceCode;
        this.ownerAddress = ownerAddress;
    }

    /**
     * 构建TRX交易数据
     *
     * @return 交易数据
     */
    @Override
    public byte[] build() throws Exception {
        Protocol.Transaction transaction = createUnFreezeBalanceContractTransaction();
        return transaction.toByteArray();
    }

    /**
     * 创建普通转账交易（转账TRX）
     *
     * @return Protocol.Transaction
     */
    private Protocol.Transaction createUnFreezeBalanceContractTransaction() throws Exception {
        BalanceContract.UnfreezeBalanceContract.Builder transferContractBuilder =
                createUnFreezeBalanceContractBuilder();
        return TxCreator.createTransaction(
                transferContractBuilder,
                Protocol.Transaction.Contract.ContractType.UnfreezeBalanceContract,
                null
        );
    }


    /**
     * 创建普通转账交易的builder
     *
     * @return Builder
     */
    private BalanceContract.UnfreezeBalanceContract.Builder createUnFreezeBalanceContractBuilder() throws Exception {
        BalanceContract.UnfreezeBalanceContract.Builder builder = BalanceContract.UnfreezeBalanceContract.newBuilder();
        if (receiverAddress != null) {
            builder.setReceiverAddress(AddressHelper.toByteString(receiverAddress));
        }
        builder.setResource(resourceCode);
        builder.setOwnerAddress(ByteString.copyFrom(AddressHelper.decodeFromBase58Check(ownerAddress)));
        return builder;
    }


}
