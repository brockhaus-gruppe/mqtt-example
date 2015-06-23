/**
 * 
 */
package de.brockhaus.m2m.mqtt.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Utility which converts objects into JSON et vice versa
 * 
 * Project: mqtt-example
 *
 * Copyright (c) by Brockhaus Group
 * www.brockhaus-gruppe.de
 * @author mbohnen, Jun 23, 2015
 *
 */
public class JSONBuilderParserUtil {

	private static final JSONBuilderParserUtil THIS = new JSONBuilderParserUtil();
	private ObjectMapper objectMapper = new ObjectMapper();

	private JSONBuilderParserUtil() {
		// lazy
	}

	public static JSONBuilderParserUtil getInstance() {
		return THIS;
	}

	public String toJSON(Object o) {

		String ret = null;

		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			ret = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return ret;
	}

	public <T> T fromJSON(Class<T> clazz, String json) {
		T ret = null;

		try {
			ret = (T) objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
