package de.brockhaus.m2m.mqtt.publisher;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.MQTTUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;
import de.brockhaus.m2m.mqtt.util.MQTTUtil.ClientType;

/**
 * Test-driver to push data into the system
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group
 * www.brockhaus-gruppe.de
 * @author mbohnen, Jun 23, 2015
 *
 */
public class MQTTPublisher {
	
	private static final Logger LOG = Logger.getLogger(MQTTPublisher.class);
	
	private MqttClient client;
	private MqttTopic topic;
	
	// you can hierarchically structure further on, i.e. for every nfc reader
	private String topicName = "nfc/ABC999";
	
	// we don't make use of JUnit in this project, so ...
	public static void main(String[] args) {
		MQTTPublisher client = new MQTTPublisher();
		client.init();
		client.publish();
		
//		System.exit(0);
	}
	
	
	private void publish() {
		
		// creating just a message
		ProductionOrderMessage poMsg = new ProductionOrderMessage("Station-ABC999", "PO-123456789-ABCD", new Date(System.currentTimeMillis()));

		// converting the message to JSON
		String json = JSONBuilderParserUtil.getInstance().toJSON(poMsg);
		
		MqttMessage msg = new MqttMessage();
		// quality of service, see: https://www.eclipse.org/paho/files/mqttdoc/Cclient/qos.html
		msg.setQos(1);
		
		// here's the beef ... we turn it into bytes
		msg.setPayload(json.getBytes());
		
		// will be stored regardless what happens
		msg.setRetained(true);
		
		try {
			// off you go ...
			topic.publish(msg);
			LOG.debug("\n Message send: \n" + json);
			
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		try {
			client = new MQTTUtil().getClient(ClientType.TYPE_PUBLISHER);
			
			// setting the options
			MqttConnectOptions options = new MqttConnectOptions();
			options.setWill(this.topicName, ("I'm gone: " + this.client.getClientId().toString()).getBytes("UTF-8"), 1, true);
			
			// you might put the options here
			client.connect(options);
			
			topic = client.getTopic(topicName);
			
		} catch (MqttException e) {
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
