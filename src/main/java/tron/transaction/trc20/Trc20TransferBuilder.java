package tron.transaction.trc20;

import com.github.ki5fpl.tronj.abi.FunctionEncoder;
import com.github.ki5fpl.tronj.abi.TypeReference;
import com.github.ki5fpl.tronj.abi.datatypes.Address;
import com.github.ki5fpl.tronj.abi.datatypes.Bool;
import com.github.ki5fpl.tronj.abi.datatypes.Function;
import com.github.ki5fpl.tronj.abi.datatypes.generated.Uint256;
import com.github.ki5fpl.tronj.client.TronClient;
import org.tron.protos.Protocol;
import org.tron.protos.contract.SmartContractOuterClass;
import tron.transaction.TransferDataBuilder;
import tron.transaction.TxCreater;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author: sunlight
 * @date: 2020/9/7 17:45
 */
public class Trc20TransferBuilder implements TransferDataBuilder {
    private String ownerAddress;
    private String receiverAddress;
    private BigDecimal value;
    private String contractAddress;
    private Integer coinDecimal;

    private Trc20TransferBuilder() {
    }
    public Trc20TransferBuilder(String ownerAddress, String receiverAddress, BigDecimal value, String contractAddress, Integer coinDecimal) {
        this.ownerAddress = ownerAddress;
        this.receiverAddress = receiverAddress;
        this.value = value;
        this.contractAddress = contractAddress;
        this.coinDecimal = coinDecimal;
    }

    @Override
    public byte[] build() throws Exception {
        Protocol.Transaction transaction = createTransferTrc20Transaction();
        return transaction.toByteArray();
    }


    private Protocol.Transaction createTransferTrc20Transaction() throws Exception {
        return TxCreater.createTransaction(
                createTransferTrc20Builder(),
                Protocol.Transaction.Contract.ContractType.TriggerSmartContract
        );
    }

    private SmartContractOuterClass.TriggerSmartContract.Builder createTransferTrc20Builder() {
        Function trc20Transfer = new Function("transfer",
                Arrays.asList(new Address(receiverAddress),
                        new Uint256(value.toBigInteger().multiply(BigInteger.valueOf(10).pow(coinDecimal)))),
                Arrays.asList(new TypeReference<Bool>() {}));
        String encodedFunction = FunctionEncoder.encode(trc20Transfer);
        SmartContractOuterClass.TriggerSmartContract.Builder builder = SmartContractOuterClass.TriggerSmartContract.newBuilder();
        builder.setOwnerAddress(TronClient.parseAddress(ownerAddress));
        builder.setContractAddress(TronClient.parseAddress(contractAddress));
        builder.setData(TronClient.parseHex(encodedFunction));
        return builder;
    }

}
