package com.ateam.paw_pals.shared.util;


public class CommonUtils {
	
	public static final String EMAIL_REGEXP = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
	public static final String EMAIL_REGEXP_MESSAGE = "Invalid email.";
	
	public static <E extends Throwable> void falseThenThrow(boolean b, E e) throws E {
		if (!b) {
			throw e;
		}
	}
	
	public static <T, E extends Throwable> T nullThenThrow(T t, E e) throws E {
		if (t != null) {
			return t;
		}
		throw e;
	}
}
