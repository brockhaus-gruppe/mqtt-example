package de.brockhaus.m2m.mqtt.subscriber;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * The one doing the work upon receiving a message ... 
 * Looks a little like the well-known MessageListener interface in JMS
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group
 * www.brockhaus-gruppe.de
 * @author mbohnen, Jun 23, 2015
 *
 */
public class MQTTSubscriptionCallBack implements MqttCallback {
	
	private static final Logger LOG = Logger.getLogger(MQTTSubscriptionCallBack.class);

	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		
		LOG.debug("Message arrived! Topic: " + topic + " Message: "	+ message.toString());
		
		// we can convert to a Java object, ... save the data ... whatever comes to our mind
//		JSONBuilderParserUtil.getInstance().fromJSON(ProductionOrderMessage.class, message.toString());

		// check out for the last will
		if ("home/LWT".equals(topic)) {
			System.err.println("Sensor gone!");
		}
	}
	
	public void connectionLost(Throwable cause) {
		LOG.warn("Connection lost");
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		LOG.trace("Message delivered");
	}
}
