package com.hydromart.util;

public class SlugUtil {
	public static String toSlug(String text) {
		return text.toLowerCase()
				.trim()
				.replaceAll("\\s+", "-")
				.replaceAll("[^a-z0-9-]", "")
				.replaceAll("-+", "-")
				.replaceAll("^-|-$", "");
	}
}
