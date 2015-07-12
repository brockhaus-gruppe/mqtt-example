package de.brockhaus.m2m.mqtt.publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.apache.log4j.Logger;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;

/**
 * This wrapper is meant to be invoked by socket-based communication 
 * and delegates the publishing to MQTT Broker...
 * 
 * Be aware of the pom.xml, you might need to change the <mainClass> entry
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author mbohnen, Jul 1, 2015
 *
 */
public class MQTTPublisherSocketWrapper {

	private static final Logger LOG = Logger
			.getLogger(MQTTPublisherSocketWrapper.class);

	private static MQTTPublisher publisher = new MQTTPublisher();

	public static void main(String[] args) {
		MQTTPublisherSocketWrapper wrapper = new MQTTPublisherSocketWrapper();
	}
	
	public MQTTPublisherSocketWrapper() {
		this.init();
	}

	private void init() {

		try {
			ServerSocket serverSocket = new ServerSocket(4000);
			LOG.debug("Socket receiver up'n'running");

			String received = null;
			while (true) {

				Socket clientSocket = serverSocket.accept();
				
				// receiving from outer space
				InputStream in = clientSocket.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in));
				
				received = read.readLine();
				if (null != received) {
					LOG.debug("Got: " + received);

					if (received.equals("exit")) {
						LOG.debug("Signal 2 end received ");
						in.close();
						read.close();
						serverSocket.close();
						clientSocket.close();
						
						System.exit(-1);
					} else {
						this.doSend(received);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void doSend(String json) {

		ProductionOrderMessage message = JSONBuilderParserUtil.getInstance().fromJSON(ProductionOrderMessage.class, json);

		// setting the date, might be done within the python script as well
		message.setDate(new Date(System.currentTimeMillis()));
		publisher.publish(message);
	}

}
