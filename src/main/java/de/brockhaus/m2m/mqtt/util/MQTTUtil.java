package de.brockhaus.m2m.mqtt.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Some useful thing to get a MQTTClient. Later on maybe everything will be configured
 * by Spring DI, then it will become a Singleton automatically.
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author mbohnen, Jun 23, 2015
 *
 */
public class MQTTUtil {

	// public broker: "tcp://broker.mqttdashboard.com:1883", "tcp://iot.eclipse.org:1883"
	// the local broker: "tcp://localhost:1883"
	public String brokerURL = "tcp://broker.mqttdashboard.com:1883";

	// the type of client we want to connect
	public enum ClientType {
		TYPE_PUBLISHER, TYPE_SUBSCRIBER
	};
	
	// the type of memory we want to use
	public enum PersistenceType {
		TYPE_MEMORY, TYPE_FILE
	};

	private MqttClient client;

	public MQTTUtil() {
		// lazy
	}

	// you can only connect once under this id, 
	// if another client will connect under this id, the first one will be disconnected
	public MqttClient getClient(ClientType clientType, PersistenceType persistenceType) throws MqttException {
		String clientId = "";

		switch (clientType) {
			case TYPE_PUBLISHER:
				clientId = this.getMacAddress() + "-pub";
				break;
			case TYPE_SUBSCRIBER:
				clientId = this.getMacAddress() + "-sub";
				break;
		}

		if(persistenceType == PersistenceType.TYPE_MEMORY) 
		{
			client = new MqttClient(brokerURL, clientId, new MemoryPersistence());
		} else {
			client = new MqttClient(brokerURL, clientId);
		}

		return client;
	}

	// getting the unique MAC address
	private String getMacAddress() {

		StringBuilder sb = new StringBuilder();
		String result = "";

		try {
			for (NetworkInterface ni : Collections.list(NetworkInterface
					.getNetworkInterfaces())) {
				byte[] hardwareAddress = ni.getHardwareAddress();

				if (hardwareAddress != null) {
					for (int i = 0; i < hardwareAddress.length; i++)
						result += String.format((i == 0 ? "" : "-") + "%02X",
								hardwareAddress[i]);

					return result;
				}
			}

		} catch (SocketException e) {
			System.out
					.println("Could not find out MAC Adress. Exiting Application ");
			System.exit(1);
		}
		return result;
	}

	public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}
}
