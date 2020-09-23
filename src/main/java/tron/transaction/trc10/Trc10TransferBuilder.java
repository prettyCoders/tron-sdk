package tron.transaction.trc10;

import com.google.protobuf.ByteString;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.protos.contract.AssetIssueContractOuterClass;
import tron.address.AddressGenerator;
import tron.transaction.TransferDataBuilder;
import tron.transaction.TxCreater;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/7 16:43
 */
public class Trc10TransferBuilder implements TransferDataBuilder {
    private String senderAddress;
    private String receiverAddress;
    private BigDecimal value;
    private String assetId;
    private Integer coinDecimal;

    private Trc10TransferBuilder() {
    }

    public Trc10TransferBuilder(String senderAddress, String receiverAddress, BigDecimal value, String assetId, Integer coinDecimal) {
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.value = value;
        this.assetId = assetId;
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
        byte[] from = AddressGenerator.decodeFromBase58Check(senderAddress);
        byte[] to = AddressGenerator.decodeFromBase58Check(receiverAddress);
        long amount = value.multiply(BigDecimal.valueOf(coinDecimal)).longValue();
        Protocol.Transaction transaction = createTransferAssetContractTransaction(from, to, amount, assetId);
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
        return TxCreater.createTransaction(transferAssetContractBuilder, Protocol.Transaction.Contract.ContractType.TransferAssetContract);
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
