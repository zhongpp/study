package com.zpp.emqttd;

import javafx.util.Callback;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.Marshaller;
import java.nio.Buffer;

@Component
public class EmqttdRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*MQTT mqtt = new MQTT();
        mqtt.setHost("localhost", 1883);
        mqtt.setClientId("mengsu");
        CallbackConnection connection = mqtt.callbackConnection();

        connection.listener(new Marshaller.Listener() {
            @Override
            public void onConnected() {
            }

            @Override
            public void onDisconnected() {
            }

            @Override
            public void onPublish(UTF8Buffer utf8Buffer, Buffer buffer, Runnable ack) {
                //当有设备向服务已订阅的主题发送消息时,该方法会消费
                ack.run();
                System.out.println(UTF8Buffer.encode(buffer.toString()));
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //连接成功后会默认订阅主题($client/mengsu)
                System.out.println("连接成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
        //新建一个device主题
        Topic[] topic = new Topic[]{new Topic("device", QoS.AT_LEAST_ONCE)};
        connection.subscribe(topic, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                System.out.println("订阅成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });*/
    }
}
