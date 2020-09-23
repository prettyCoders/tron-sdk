package com.sunlight.tronsdk.trc20.decode;

import com.sunlight.tronsdk.address.AddressHelper;
import org.tron.common.utils.ByteArray;

import java.math.BigInteger;

/**
 * @author: sunlight
 * @date: 2020/9/17 18:26
 */
public class Trc20TransferFromDecoder implements Trc20MessageDecoder {
    private String data;
    private Trc20TransferFromDecoder() {
    }
    public Trc20TransferFromDecoder(String data) {
        this.data = data;
    }

    @Override
    public TransferMessage decode() {
        data=data.substring(32);

        byte[] fromBytes = ByteArray.fromHexString("41"+data.substring(0,40));
        String fromAddress= AddressHelper.addressBytesEncode58Check(fromBytes);

        data=data.substring(52);
        byte[] toBytes = ByteArray.fromHexString("41"+data.substring(0,40));
        String toAddress= AddressHelper.addressBytesEncode58Check(toBytes);

        data=data.substring(52);
        String hexValue=data.replaceFirst("^0*", "");;
        BigInteger value = new BigInteger(hexValue,16);
        return new TransferMessage(toAddress,value);
    }
}
