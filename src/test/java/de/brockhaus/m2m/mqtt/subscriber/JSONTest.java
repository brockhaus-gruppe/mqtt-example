package de.brockhaus.m2m.mqtt.subscriber;

import java.util.Date;

import de.brockhaus.m2m.mqtt.util.JSONBuilderParserUtil;
import de.brockhaus.m2m.mqtt.util.ProductionOrderMessage;

public class JSONTest {
	
	public static void main(String[] args) {
		ProductionOrderMessage msg = new ProductionOrderMessage();
		msg.setDate(new Date(System.currentTimeMillis()));
		msg.setProductionOrderId("aaabbb123");
		msg.setSenderId("000FFF999");
		
		System.out.println(JSONBuilderParserUtil.getInstance().toJSON(msg));
	}

}
