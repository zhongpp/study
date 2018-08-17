package com.zpp.okhttp;

import okhttp3.*;

import java.io.IOException;

/**
 * @author pingpingZhong
 * Date: 2018/1/16
 * Time: 10:24
 */
public class OkhttpTest {
    public static void main(String[] args) {
        //get();
        post();
    }

    private static void get() {
        String url = "https://www.baidu.com/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void post() {
        String url = "https://www.baidu.com/";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("kw", "值")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
