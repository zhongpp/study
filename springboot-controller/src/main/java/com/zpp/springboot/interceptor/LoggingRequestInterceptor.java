package com.zpp.springboot.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author pingpingZhong
 *         Date: 2018/6/7
 *         Time: 16:48
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.info("--->> {} {}", request.getMethod(), request.getURI());
        ClientHttpResponse response = execution.execute(request, body);
        logger.info("<<--- {} {}", response.getRawStatusCode(), request.getURI());
        return response;
    }
}
