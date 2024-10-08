package net.aditri.web.utility;


import java.util.List;

public class StringHelper {

	public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	public static boolean inArrayIgnoreCase(List<String> list, String searchFor) {
	    for (String current : list) {
	        if (current.equalsIgnoreCase(searchFor)) {
	            return true;
	        }
	    }
	    return false;
	}
}
