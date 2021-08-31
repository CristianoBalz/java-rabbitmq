package com.rabbitmq.rabbitmq_queues.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class JsonUtils {
	private JsonUtils() {}
	
	public static String toJson(Object obj) {
		return gson().toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return gson().fromJson(json, clazz);
	}
	
	public static <T> T fromJson(JsonElement jsonElement, Class<T> clazz) {
		return gson().fromJson(jsonElement, clazz);
	}
	
	private static Gson gson() {
		GsonBuilder builder = new GsonBuilder(); 
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    return builder.create(); 
	}
}