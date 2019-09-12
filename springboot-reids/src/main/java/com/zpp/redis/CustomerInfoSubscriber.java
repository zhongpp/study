package com.zpp.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class CustomerInfoSubscriber implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("Received >> " + message +  ", " + Thread.currentThread().getName() );
	}
}
