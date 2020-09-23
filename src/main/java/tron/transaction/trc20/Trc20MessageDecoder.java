package tron.transaction.trc20;


/**
 * @author: sunlight
 * @date: 2020/9/17 18:20
 */
public interface Trc20MessageDecoder {
    /**
     * 解码交易数据
     * @return 解码后的交易数据
     */
    TransferMessage decode();
}
