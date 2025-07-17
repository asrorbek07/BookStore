package uz.kruz.util;

public class StringUtil {
	public static boolean isEmpty(String str) {
		return str != null && str.isEmpty()&& str.trim().equals(str);
	}
}
