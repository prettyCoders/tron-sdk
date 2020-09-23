package com.sunlight.tronsdk.trx;

import com.google.protobuf.ByteString;
import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.constant.CoinConstant;
import com.sunlight.tronsdk.transaction.TransactionDataBuilder;
import com.sunlight.tronsdk.transaction.TxCreator;
import com.sunlight.tronsdk.utils.TokenConverter;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.protos.contract.Common;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/7 16:43
 */
public class TrxFreezeBalanceBuilder implements TransactionDataBuilder {
    /**
     * 目前只支持3天
     */
    private static final Long DEFAULT_FROZEN_DURATION = 3L;

    private BigDecimal frozenBalance;
    private Long frozenDuration;
    private String receiverAddress;
    private Common.ResourceCode resourceCode;
    private String ownerAddress;

    private TrxFreezeBalanceBuilder() {
    }

    public TrxFreezeBalanceBuilder(BigDecimal frozenBalance, Common.ResourceCode resourceCode, String ownerAddress) {
        this.frozenBalance = frozenBalance;
        this.resourceCode = resourceCode;
        this.ownerAddress = ownerAddress;
    }

    public TrxFreezeBalanceBuilder(BigDecimal frozenBalance, Long frozenDuration, Common.ResourceCode resourceCode, String ownerAddress) {
        this.frozenBalance = frozenBalance;
        this.frozenDuration = frozenDuration;
        this.resourceCode = resourceCode;
        this.ownerAddress = ownerAddress;
    }

    public TrxFreezeBalanceBuilder(BigDecimal frozenBalance, String receiverAddress, Common.ResourceCode resourceCode, String ownerAddress) {
        this.frozenBalance = frozenBalance;
        this.receiverAddress = receiverAddress;
        this.resourceCode = resourceCode;
        this.ownerAddress = ownerAddress;
    }

    public TrxFreezeBalanceBuilder(BigDecimal frozenBalance, Long frozenDuration, String receiverAddress, Common.ResourceCode resourceCode, String ownerAddress) {
        this.frozenBalance = frozenBalance;
        this.frozenDuration = frozenDuration;
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
        Protocol.Transaction transaction = createFreezeBalanceContractTransaction();
        return transaction.toByteArray();
    }

    /**
     * 创建普通转账交易（转账TRX）
     *
     * @return Protocol.Transaction
     */
    private Protocol.Transaction createFreezeBalanceContractTransaction() throws Exception {
        BalanceContract.FreezeBalanceContract.Builder transferContractBuilder =
                createFreezeBalanceContractBuilder();
        return TxCreator.createTransaction(
                transferContractBuilder,
                Protocol.Transaction.Contract.ContractType.FreezeBalanceContract,
                null
        );
    }


    /**
     * 创建普通转账交易的builder
     *
     * @return Builder
     */
    private BalanceContract.FreezeBalanceContract.Builder createFreezeBalanceContractBuilder() throws Exception {
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        builder.setFrozenBalance(
                TokenConverter.tokenBigDecimalToBigInteger(
                        frozenBalance, CoinConstant.TRX_DECIMAL).longValue()
        );
        builder.setFrozenDuration(frozenDuration == null ? DEFAULT_FROZEN_DURATION : frozenDuration);
        if (receiverAddress != null) {
            builder.setReceiverAddress(AddressHelper.toByteString(receiverAddress));
        }
        builder.setResource(resourceCode);
        builder.setOwnerAddress(ByteString.copyFrom(AddressHelper.decodeFromBase58Check(ownerAddress)));
        return builder;
    }


}
