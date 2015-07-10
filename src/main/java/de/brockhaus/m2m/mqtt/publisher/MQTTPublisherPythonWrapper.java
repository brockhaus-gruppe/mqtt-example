package de.brockhaus.m2m.mqtt.publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;

/**
 * This wrapper is meant to get called by python and delegate the sending ...
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author mbohnen, Jul 1, 2015
 *
 */
public class MQTTPublisherPythonWrapper {

	private static final Logger LOG = Logger
			.getLogger(MQTTPublisherPythonWrapper.class);

	private static MQTTPublisher publisher = new MQTTPublisher();

	// this will be invoked by python using python's subprocess library
	public static void main(String[] args) {
		try {
			LOG.debug(MQTTPublisherPythonWrapper.class.getSimpleName()
					+ " is started ");
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String in = bufferRead.readLine();

			if (null != in || !in.equals("")) {
				LOG.debug(MQTTPublisherPythonWrapper.class.getSimpleName()
						+ " received: " + in);
				MQTTPublisherPythonWrapper.doSend(in);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receiveMessage() {
		try {
			ServerSocket serverSocket = new ServerSocket(4000);
			Socket clientSocket = serverSocket.accept();
			InputStream in = clientSocket.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String received = read.readLine();

			System.out.println("Got: " + received);

			in.close();
			read.close();
			serverSocket.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void doSend(String json) {

		ProductionOrderMessage message = JSONBuilderParserUtil.getInstance()
				.fromJSON(ProductionOrderMessage.class, json);
		publisher.publish(message);
	}

}
