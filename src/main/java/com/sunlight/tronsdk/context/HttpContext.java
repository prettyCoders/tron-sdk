package com.sunlight.tronsdk.context;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @author: sunlight
 * @date: 2020/7/28 10:02
 */
public class HttpContext {
    public static RestTemplate restTemplate;
    public static HttpHeaders standardHeaders;

    static {
        restTemplate = new RestTemplate();
        standardHeaders = new HttpHeaders();
        standardHeaders.setContentType(MediaType.APPLICATION_JSON);
    }
}
