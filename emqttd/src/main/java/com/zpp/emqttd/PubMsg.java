package com.zpp.emqttd;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PubMsg {
    private static int qos = 2; //只有一次
    private static String broker = "tcp://192.168.6.137:1883";
    private static String userName = "tuyou";
    private static String passWord = "tuyou";


    private static MqttClient connect(String clientId, String userName,
                                      String password) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(userName);
        connOpts.setPassword(password.toCharArray());
        connOpts.setConnectionTimeout(10);
        connOpts.setKeepAliveInterval(20);
//		String[] uris = {"tcp://10.100.124.206:1883","tcp://10.100.124.207:1883"};
//		connOpts.setServerURIs(uris);  //起到负载均衡和高可用的作用
        MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
        mqttClient.setCallback(new PushCallback("test"));
        mqttClient.connect(connOpts);
        return mqttClient;
    }

    private static void pub(MqttClient sampleClient, String msg, String topic)
            throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage("ertwersdfas".getBytes());
        message.setQos(qos);
        message.setRetained(false);
        sampleClient.publish(topic, message);
    }

    private static void publish(String str, String clientId, String topic) throws MqttException {
        MqttClient mqttClient = connect(clientId, userName, passWord);

        if (mqttClient != null) {
            pub(mqttClient, str, topic);
            System.out.println("pub-->" + str);
        }

        if (mqttClient != null) {
            mqttClient.disconnect();
        }
    }

    public static void main(String[] args) throws MqttException {
        publish("message content", "client-id-0", "$share/edge/server/public/a");
    }
}

class PushCallback implements MqttCallback {
    private String threadId;

    public PushCallback(String threadId) {
        this.threadId = threadId;
    }

    public void connectionLost(Throwable cause) {

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
//       System.out.println("deliveryComplete---------" + token.isComplete());
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String msg = new String(message.getPayload());
        System.out.println(threadId + " " + msg);
    }
}
