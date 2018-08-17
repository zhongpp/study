package com.zpp.resttemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author pingpingZhong
 *         on 2017/5/8.
 */
@Service
public class RestTemplateService implements CommandLineRunner {

    @Value("${short.url.sina_short_url}")
    private String SINA_SHORT_URL;

    @Value("${short.url.sina_source}")
    private String SINA_SOURCE;

    @Value("${short.url.weixin_get_access_token}")
    private String WEIXIN_GET_ACCESS_TOKEN;

    @Value("${short.url.weixin_get_short_url}")
    private String WEIXIN_GET_SHORT_URL;

    @Value("${short.url.baidu_short_url}")
    private String BAIDU_SHORT_URL;

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    //post请求


    private String getShortUrlFromBaidu(String longUrl) throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("url", longUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(BAIDU_SHORT_URL, HttpMethod.POST, requestEntity, String.class);
        String body = response.getBody().toString();
        JsonNode jsonNode = objectMapper.readTree(body);
        return jsonNode.path("tinyurl").asText();
    }


    private String getShortUrlFromSina(String longUrl) throws Exception {
        System.out.println(SINA_SHORT_URL);
        ResponseEntity<String> response = restTemplate.exchange(SINA_SHORT_URL, HttpMethod.GET, null, String.class, SINA_SOURCE, longUrl);
        int statusCode = response.getStatusCodeValue();
        if (statusCode >= 300 || response.getBody() == null) {
            throw new Exception("accept fail!");
        }
        String body = response.getBody().toString();
        JsonNode jsonNode = objectMapper.readTree(body.substring(1, body.length() - 1));
        return jsonNode.path("url_short").asText();
    }

    private String getShortUrlFromWeixin(String longUrl) throws Exception {
        String accessToken = "";

        ResponseEntity<String> response = restTemplate.exchange(WEIXIN_GET_ACCESS_TOKEN, HttpMethod.GET, null, String.class);
        int statusCode = response.getStatusCodeValue();
        if (statusCode >= 300 || response.getBody() == null) {
            throw new Exception("accept fail!");
        }
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        accessToken = jsonNode.path("access_token").asText();

        Map<String, String> map = ImmutableMap.of(
                "action", "long2short",
                "long_url", longUrl
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(WEIXIN_GET_SHORT_URL, HttpMethod.POST, requestEntity, String.class, accessToken);
        int statusCode2 = response2.getStatusCodeValue();
        if (statusCode2 >= 300 || response2.getBody() == null) {
            throw new Exception("accept fail!");
        }
        JsonNode jsonNode2 = objectMapper.readTree(response2.getBody());
        return jsonNode2.path("short_url").asText();
    }

    public void run(String... args) throws Exception {
        String baidu = "http://www.baidu.com";

        System.out.println(" =========新浪==========");
        String retSinaStr = getShortUrlFromSina(baidu);
        System.out.println(retSinaStr);

        System.out.println(" =========百度==========");
        String retBaiStr = getShortUrlFromBaidu(baidu);
        System.out.println(retBaiStr);

        System.out.println(" =========微信==========");
        String retWeiStr = getShortUrlFromWeixin(baidu);
        System.out.println(retWeiStr);



    }
}