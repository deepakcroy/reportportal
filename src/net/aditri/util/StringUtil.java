package net.aditri.util;

import java.util.List;

public final class StringUtil {
	private StringUtil(){}
	
	public static String escapeString(String s)
	{
		return s.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"");
	}
	public static boolean containsIgnoreCase(List<String> list, String searchStr) {
	    for (String current : list) {
	        if (current.equalsIgnoreCase(searchStr)) {
	            return true;
	        }
	    }
	    return false;
	}
}
