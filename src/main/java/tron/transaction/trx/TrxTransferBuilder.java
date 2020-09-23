package tron.transaction.trx;

import com.google.protobuf.ByteString;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import tron.address.AddressGenerator;
import tron.constant.CoinConstant;
import tron.transaction.TransferDataBuilder;
import tron.transaction.TxCreater;

import java.math.BigDecimal;

/**
 * @author: sunlight
 * @date: 2020/9/7 16:43
 */
public class TrxTransferBuilder implements TransferDataBuilder {
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

    @Override
    public byte[] build() throws Exception {
        byte[] from = AddressGenerator.decodeFromBase58Check(senderAddress);
        byte[] to = AddressGenerator.decodeFromBase58Check(receiverAddress);
        long amount = value.multiply(BigDecimal.valueOf(CoinConstant.TRX_DECIMAL)).longValue();
        Protocol.Transaction transaction = createTransferContractTransaction(from, to, amount);
        return transaction.toByteArray();
    }

    /**
     * 构建TRX交易数据
     *
     * @param senderPrivateKey 发送方的私钥，十六进制
     * @param receiverAddress  接收方地址，Base58
     * @param value            转账数量
     * @return 交易数据
     */


    /**
     * 创建普通转账交易（转账TRX）
     *
     * @param from   发送方
     * @param to     接收方
     * @param amount 金额
     * @return Protocol.Transaction
     */
    private Protocol.Transaction createTransferContractTransaction(byte[] from, byte[] to, long amount) throws Exception {
        BalanceContract.TransferContract.Builder transferContractBuilder = createTransferContractBuilder(from, to, amount);
        return TxCreater.createTransaction(transferContractBuilder, Protocol.Transaction.Contract.ContractType.TransferContract);
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
