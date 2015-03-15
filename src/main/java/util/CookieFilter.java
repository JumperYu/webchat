package util;

import java.util.Locale;
import java.util.StringTokenizer;

public class CookieFilter {

	private static final String OBFUSCATED = "[obfuscated]";

	private CookieFilter() {
		// Hide default constructor
	}

	public static String filter(String input, String sessionId) {

		StringBuilder sb = new StringBuilder(input.length());

		// Cookie name value pairs are ';' separated.
		// Session IDs don't use ; in the value so don't worry about quoted
		// values that contain ;
		StringTokenizer st = new StringTokenizer(input, ";");

		boolean first = true;
		while (st.hasMoreTokens()) {
			if (first) {
				first = false;
			} else {
				sb.append(';');
			}
			sb.append(filterNameValuePair(st.nextToken(), sessionId));
		}

		return sb.toString();
	}

	private static String filterNameValuePair(String input, String sessionId) {
		int i = input.indexOf('=');
		if (i == -1) {
			return input;
		}
		String name = input.substring(0, i);
		String value = input.substring(i + 1, input.length());

		if (name.toLowerCase(Locale.ENGLISH).contains("jsessionid")
				&& (sessionId == null || !value.contains(sessionId))) {
			value = OBFUSCATED;
		}

		return name + "=" + value;
	}
}
