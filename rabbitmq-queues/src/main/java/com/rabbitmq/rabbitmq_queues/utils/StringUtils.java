package com.rabbitmq.rabbitmq_queues.utils;

public class StringUtils {
	private StringUtils() {}
	
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
}
