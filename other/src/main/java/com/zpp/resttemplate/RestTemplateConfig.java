package com.zpp.resttemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author pingpingZhong
 *         Date: 2017/6/5
 *         Time: 14:19
 */
@Configuration
public class RestTemplateConfig {

    @Value("${connection.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value("${connection.connect-timeout}")
    private int connectTimeout;

    @Value("${connection.read-timeout}")
    private int readTimeout;

    @Bean
    @ConfigurationProperties(prefix = "connection")
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        httpComponentsClientHttpRequestFactory.setConnectTimeout(connectTimeout);
        httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeout);
        return httpComponentsClientHttpRequestFactory;
    }

    @Bean
    RestTemplate restTemplate () {
        RestTemplate restTemplate = new RestTemplate(customHttpRequestFactory());
        return restTemplate;
    }

}
