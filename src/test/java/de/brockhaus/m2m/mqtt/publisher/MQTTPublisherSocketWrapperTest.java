package de.brockhaus.m2m.mqtt.publisher;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;

/**
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group
 * www.brockhaus-gruppe.de
 * @author mbohnen, Jul 9, 2015
 *
 */
public class MQTTPublisherSocketWrapperTest {

	private static final Logger LOG = Logger.getLogger(MQTTPublisherPythonWrapperTest.class);

	private ArrayList<ProductionOrderMessage> messages;

	public static void main(String[] args) {
		MQTTPublisherSocketWrapperTest test = new MQTTPublisherSocketWrapperTest();
		test.initData();
		test.doSend();
	}


	private void doSend() {

		try {
			Socket clientSocket = new Socket("127.0.0.1",4000);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			
			for (ProductionOrderMessage productionOrderMessage : messages) {
				LOG.debug("Socket connected: " + clientSocket.isConnected());
				String msg = JSONBuilderParserUtil.getInstance().toJSON(productionOrderMessage);
				LOG.debug("sending: " + msg);
				out.println(msg);
				out.flush();
			}
			
			//out.println("exit");
			
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initData() {
		messages = new ArrayList<ProductionOrderMessage>();
		ProductionOrderMessage p1 = new ProductionOrderMessage();
		p1.setDate(new Date(System.currentTimeMillis()));
		p1.setProductionOrderId("12345-ABC");
		p1.setSenderId("0001AXHF");
		messages.add(p1);
		
		ProductionOrderMessage p2 = new ProductionOrderMessage();
		p2.setDate(new Date(System.currentTimeMillis()));
		p2.setProductionOrderId("ABC-124");
		p2.setSenderId("0001AXHF");
//		messages.add(p2);
		
	}
}
