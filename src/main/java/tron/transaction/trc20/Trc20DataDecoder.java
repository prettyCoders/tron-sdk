package tron.transaction.trc20;

import org.tron.common.crypto.Hash;
import org.tron.common.utils.ByteArray;
import tron.constant.Trc20Method;

/**
 * @author: sunlight
 * @date: 2020/9/17 17:36
 */
public class Trc20DataDecoder {

    public static TransferMessage decode(String data) throws Exception {
        Trc20MessageDecoder decoder= matchTrc20MessageDecoder(data);
        return decoder.decode();
    }

    private static Trc20MessageDecoder matchTrc20MessageDecoder(String data) throws Exception {
        String methodId=data.substring(0,8);
        if (methodId.equals(encodeMethod(Trc20Method.TRANSFER))){
            return new Trc20TransferDecoder(data);
        }else if(methodId.equals(encodeMethod(Trc20Method.TRANSFER_FROM))){
            return new Trc20TransferFromDecoder(data);
        }else {
            throw new Exception("unknown trc20 method");
        }
    }

    private static String encodeMethod(Trc20Method trc20Method){
        return ByteArray.toHexString(Hash.sha3(trc20Method.getMethod().getBytes())).substring(0,8);
    }
}
