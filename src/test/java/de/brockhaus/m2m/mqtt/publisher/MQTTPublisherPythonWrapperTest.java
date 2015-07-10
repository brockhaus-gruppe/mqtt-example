package de.brockhaus.m2m.mqtt.publisher;

import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;

/**
 * We read some values from the command line, convert them into a
 * ProductionOrderMessage and off we go ...
 * 
 * Not a JUnit test.
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author mbohnen, Jul 1, 2015
 *
 */
public class MQTTPublisherPythonWrapperTest {

	private static final Logger LOG = Logger.getLogger(MQTTPublisherPythonWrapperTest.class);

	private static String deviceId = "00AAFF";

	private static Scanner scanner;

	public static void main(String[] args) {
		MQTTPublisherPythonWrapperTest test = new MQTTPublisherPythonWrapperTest();
		test.proceed();
	}

	private void proceed() {
		// create a scanner so we can read the command-line input
		scanner = new Scanner(System.in);
		String cont = "Y";

		while (cont.equals("Y")) {
			// prompt for the number of a production order
			System.out.print("PO-number: ");

			// get the input as a String
			String poNumber = scanner.next();

			LOG.debug("PO received: " + poNumber);

			ProductionOrderMessage message = new ProductionOrderMessage(
					deviceId, poNumber, new Date(System.currentTimeMillis()));

			MQTTPublisherPythonWrapper.doSend(JSONBuilderParserUtil
					.getInstance().toJSON(message));
			
			System.out.print("Continue [Y/N]: ");
			cont = scanner.next().toUpperCase();
			System.out.println(cont);
		}
	}

}
