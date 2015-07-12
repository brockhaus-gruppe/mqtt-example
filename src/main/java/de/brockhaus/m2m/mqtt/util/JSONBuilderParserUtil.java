/**
 * 
 */
package de.brockhaus.m2m.mqtt.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;


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
	private static boolean formatted;

	private JSONBuilderParserUtil() {
		
		// using ISO format
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	// you can get it in a nice format ...
	public static JSONBuilderParserUtil getInstance(boolean formatted) {
		JSONBuilderParserUtil.formatted = formatted;
		return THIS;
	}

	// or just the standard
	public static JSONBuilderParserUtil getInstance() {
		return THIS;
	}

	public String toJSON(Object o) {

		String ret = null;

		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			if(formatted) {
				ret = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
			} else {
				ret = objectMapper.writeValueAsString(o);
			}
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
