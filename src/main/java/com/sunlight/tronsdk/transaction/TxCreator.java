package com.sunlight.tronsdk.transaction;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.sunlight.tronsdk.TrxQuery;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.protos.contract.Common;

/**
 * @author: sunlight
 * @date: 2020/9/7 16:49
 */
public class TxCreator {
    /**
     * 创建交易
     *
     * @param builder      不同交易类型的builder
     * @param contractType 合约类型
     * @return Protocol.Transaction
     */
    public static Protocol.Transaction createTransaction(
            Message.Builder builder,
            Protocol.Transaction.Contract.ContractType contractType,
            Long feeLimit) throws Exception {
        Protocol.Transaction.Builder transactionBuilder = Protocol.Transaction.newBuilder();
        Protocol.Transaction.Contract.Builder contractBuilder = Protocol.Transaction.Contract.newBuilder();
        try {
            Any any = Any.pack(builder.build());
            contractBuilder.setParameter(any);
        } catch (Exception e) {
            return null;
        }
        contractBuilder.setType(contractType);
        //获取最新区块的相关信息
        JSONObject latestBlockData = JSONObject.parseObject(TrxQuery.getLatestBlock());
        JSONObject rawData = latestBlockData.getJSONObject("block_header").getJSONObject("raw_data");
        Long latestBlockTimestamp = rawData.getLong("timestamp");
        long blockHeight = rawData.getLong("number");
        byte[] blockHash = ByteArray.fromHexString(latestBlockData.getString("blockID"));
        transactionBuilder.getRawDataBuilder().addContract(contractBuilder)
                .setTimestamp(System.currentTimeMillis())
                .setExpiration(latestBlockTimestamp + 10 * 60 * 60 * 1000);
        Protocol.Transaction transaction = transactionBuilder.build();
        return setReference(transaction, blockHeight, blockHash,feeLimit);
    }

    /**
     * 设置交易的引用
     *
     * @param transaction 交易
     * @return Protocol.Transaction
     */
    private static Protocol.Transaction setReference(
            Protocol.Transaction transaction,
            long blockHeight,
            byte[] blockHash,
            Long feeLimit
    ) {
        byte[] refBlockNum = ByteArray.fromLong(blockHeight);
        Protocol.Transaction.raw rawData = transaction.getRawData().toBuilder()
                .setRefBlockHash(ByteString.copyFrom(ByteArray.subArray(blockHash, 8, 16)))
                .setRefBlockBytes(ByteString.copyFrom(ByteArray.subArray(refBlockNum, 6, 8)))
                .setFeeLimit(feeLimit == null ? 10000000 : feeLimit)
                .build();
        return transaction.toBuilder().setRawData(rawData).build();
    }
}
