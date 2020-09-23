package com.sunlight.tronsdk.address;


import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * @author: sunlight
 * @date: 2020/7/24 15:59
 */

public class AddressTest {
    private static final Logger LOGGER= LoggerFactory.getLogger(AddressTest.class);

    @Test
    public void testNewAddress() throws Exception {
        Address address = AddressHelper.newAddress();
        LOGGER.info(JSON.toJSONString(address));
        AddressHelper.decodeFromBase58Check("TYCw2XSJuQKWtqNMme4XQJX8MgtZ1R4Xwb");
        assertNotNull(address);
        assertNotNull(address.getAddress());
        assertNotNull(address.getPrivateKey());
    }
}
