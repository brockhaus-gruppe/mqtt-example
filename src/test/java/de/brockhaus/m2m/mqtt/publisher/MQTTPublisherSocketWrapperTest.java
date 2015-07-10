package de.brockhaus.m2m.mqtt.publisher;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;

/**
 * Simulation reading of ProductionOrder by manual entry via command line
 * 
 * Not a JUnit test.
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author mbohnen, Jul 9, 2015
 *
 */
public class MQTTPublisherSocketWrapperTest {

	private static final Logger LOG = Logger
			.getLogger(MQTTPublisherPythonWrapperTest.class);

	private static String deviceId = "00AAFF";

	public static void main(String[] args) {
		MQTTPublisherSocketWrapperTest test = new MQTTPublisherSocketWrapperTest();
		test.init();
	}

	private void doSend(ProductionOrderMessage message) {

		try {
			Socket clientSocket = new Socket("127.0.0.1", 4000);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			;
			String msg = JSONBuilderParserUtil.getInstance().toJSON(message);
			LOG.debug("sending: " + msg);
			out.println(msg);
			out.flush();

			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		// create a scanner so we can read the command-line input
		Scanner scanner = new Scanner(System.in);
		String cont = "Y";

		while (cont.equals("Y")) {
			// prompt for the number of a production order
			System.out.print("PO-number: ");

			// get the input as a String
			String poNumber = scanner.next();

			LOG.debug("PO received: " + poNumber);

			ProductionOrderMessage message = new ProductionOrderMessage(
					deviceId, poNumber, new Date(System.currentTimeMillis()));

			this.doSend(message);

			System.out.print("Continue [Y/N]: ");
			cont = scanner.next().toUpperCase();
		}
	}
}
