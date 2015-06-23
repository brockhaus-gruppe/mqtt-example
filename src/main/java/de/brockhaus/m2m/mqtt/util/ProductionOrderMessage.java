package de.brockhaus.m2m.mqtt.util;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Something which might come from a NFC Reader ...
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group
 * www.brockhaus-gruppe.de
 * @author mbohnen, Jun 23, 2015
 *
 */
@XmlRootElement
public class ProductionOrderMessage implements Serializable {
	
	private String senderId;
	private String productionOrderId;
	
	private Date date;
	
	public ProductionOrderMessage() {
		super();
	}

	public ProductionOrderMessage(String senderId, String productionOrderId,
			Date date) {
		super();
		this.senderId = senderId;
		this.productionOrderId = productionOrderId;
		this.date = date;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(String productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
