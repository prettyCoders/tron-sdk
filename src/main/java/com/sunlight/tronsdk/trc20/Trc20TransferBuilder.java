package com.sunlight.tronsdk.trc20;

import com.github.ki5fpl.tronj.abi.TypeReference;
import com.github.ki5fpl.tronj.abi.datatypes.Address;
import com.github.ki5fpl.tronj.abi.datatypes.Bool;
import com.github.ki5fpl.tronj.abi.datatypes.Function;
import com.github.ki5fpl.tronj.abi.datatypes.generated.Uint256;
import com.sunlight.tronsdk.transaction.TransactionDataBuilder;
import com.sunlight.tronsdk.transaction.TxCreator;
import com.sunlight.tronsdk.utils.TokenConverter;
import org.tron.protos.Protocol;
import org.tron.protos.contract.SmartContractOuterClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author: sunlight
 * @date: 2020/9/7 17:45
 */
public class Trc20TransferBuilder implements TransactionDataBuilder {
    private String ownerAddress;
    private String receiverAddress;
    private BigDecimal value;
    private  String contractAddress;
    private Long feeLimit;

    private Trc20TransferBuilder() {
    }

    public Trc20TransferBuilder(String ownerAddress, String receiverAddress, BigDecimal value,
                                String contractAddress, Long feeLimit) {
        this.ownerAddress = ownerAddress;
        this.receiverAddress = receiverAddress;
        this.value = value;
        this.contractAddress=contractAddress;
        this.feeLimit=feeLimit;
    }

    @Override
    public byte[] build() throws Exception {
        Protocol.Transaction transaction = createTransferTrc20Transaction(feeLimit);
        return transaction.toByteArray();
    }


    private Protocol.Transaction createTransferTrc20Transaction(Long feeLimit) throws Exception {
        return TxCreator.createTransaction(
                createTransferTrc20Builder(),
                Protocol.Transaction.Contract.ContractType.TriggerSmartContract,
                feeLimit
        );
    }

    private SmartContractOuterClass.TriggerSmartContract.Builder createTransferTrc20Builder() throws Exception {
        Function trc20Transfer = new Function("transfer",
                Arrays.asList(new Address(receiverAddress),
                        new Uint256(TokenConverter.tokenBigDecimalToBigInteger(value,Trc20Helper.decimals(ownerAddress,contractAddress)))),
                Arrays.asList(new TypeReference<Bool>() {
                }));
        return Trc20ParamBuilder.build(trc20Transfer, ownerAddress,contractAddress);
    }

}
