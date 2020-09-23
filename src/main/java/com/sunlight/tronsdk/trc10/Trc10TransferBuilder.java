package com.sunlight.tronsdk.trc10;

import com.google.protobuf.ByteString;
import com.sunlight.tronsdk.address.AddressHelper;
import com.sunlight.tronsdk.transaction.TransactionDataBuilder;
import com.sunlight.tronsdk.transaction.TxCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.protos.contract.AssetIssueContractOuterClass;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/7 16:43
 */
public class Trc10TransferBuilder implements TransactionDataBuilder {
    private String senderAddress;
    private String receiverAddress;
    private BigDecimal value;
    private String assetName;
    private Integer coinDecimal;

    private Trc10TransferBuilder() {
    }

    public Trc10TransferBuilder(String senderAddress, String receiverAddress, BigDecimal value, String assetName, Integer coinDecimal) {
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.value = value;
        this.assetName = assetName;
        this.coinDecimal = coinDecimal;
    }

    /**
     * 构建TRC10交易数据
     *
     * @return 交易数据
     * @throws Exception 异常
     */
    @Override
    public byte[] build() throws Exception {
        byte[] from = AddressHelper.decodeFromBase58Check(senderAddress);
        byte[] to = AddressHelper.decodeFromBase58Check(receiverAddress);
        long amount = value.multiply(BigDecimal.valueOf(coinDecimal)).longValue();
        Protocol.Transaction transaction = createTransferAssetContractTransaction(from, to, amount, assetName);
        return transaction.toByteArray();
    }


    /**
     * 创建资产转账交易
     *
     * @param from    发送方
     * @param to      接收方
     * @param amount  金额
     * @param assetId 资产ID
     * @return Protocol.Transaction
     */
    private Protocol.Transaction createTransferAssetContractTransaction(
            byte[] from, byte[] to, long amount, String assetId) throws Exception {
        AssetIssueContractOuterClass.TransferAssetContract.Builder transferAssetContractBuilder =
                createTransferAssetContractBuilder(from, to, amount, assetId);
        return TxCreator.createTransaction(transferAssetContractBuilder, Protocol.Transaction.Contract.ContractType.TransferAssetContract,null);
    }

    /**
     * 创建资产转账交易的builder
     *
     * @param from    发送方
     * @param to      接收方
     * @param amount  金额
     * @param assetId 资产ID
     * @return Builder
     */
    private AssetIssueContractOuterClass.TransferAssetContract.Builder createTransferAssetContractBuilder(
            byte[] from, byte[] to, long amount, String assetId) {
        AssetIssueContractOuterClass.TransferAssetContract.Builder transferAssetContractBuilder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
        transferAssetContractBuilder.setAmount(amount);
        ByteString bsTo = ByteString.copyFrom(to);
        ByteString bsOwner = ByteString.copyFrom(from);
        transferAssetContractBuilder.setToAddress(bsTo);
        transferAssetContractBuilder.setOwnerAddress(bsOwner);
        transferAssetContractBuilder.setAssetName(ByteString.copyFrom(ByteArray.fromHexString(assetId)));
        return transferAssetContractBuilder;
    }
}
