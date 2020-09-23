package com.sunlight.tronsdk.trc20;

import com.github.ki5fpl.tronj.abi.FunctionEncoder;
import com.github.ki5fpl.tronj.abi.datatypes.Function;
import com.github.ki5fpl.tronj.client.TronClient;
import org.tron.protos.contract.SmartContractOuterClass;

/**
 * @author: sunlight
 * @date: 2020/9/22 14:47
 */
public class Trc20ParamBuilder {
    public static SmartContractOuterClass.TriggerSmartContract.Builder build(
            Function function,String ownerAddress,String contractAddress){
        String encodedFunction = FunctionEncoder.encode(function);
        SmartContractOuterClass.TriggerSmartContract.Builder builder =
                SmartContractOuterClass.TriggerSmartContract.newBuilder();
        builder.setOwnerAddress(TronClient.parseAddress(ownerAddress));
        builder.setContractAddress(TronClient.parseAddress(contractAddress));
        builder.setData(TronClient.parseHex(encodedFunction));
        return builder;
    }
}
