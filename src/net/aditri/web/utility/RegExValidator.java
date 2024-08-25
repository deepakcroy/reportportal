package net.aditri.web.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegExValidator {
	
	private static Pattern pattern;
	private static Matcher matcher;
	
	public static final String USERNAME_PATTERN = "^[a-z0-9_-]{6,15}$";
	public static final String USERNAME_ERRMSG = 
			  "<ul>"
			+ "<li>Match characters and symbols in the list, a-z, 0-9, underscore, hyphen</li>"
			+ "<li>Length at least 6 characters and maximum length of 15</li>"
			+ "</ul>";
	
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String PASSWORD_ERRMSG = 
			  "<ul>"
			+ "<li>must contains one digit from 0-9</li>"
			+ "<li>must contains one lowercase characters</li>"
			+ "<li>must contains one uppercase characters</li>"
			+ "<li>must contains one special symbols in the list <b>@#$%</b></li>"
			+ "<li>match anything with previous condition checking</li>"
			+ "<li>length at least 6 characters and maximum of 20</li>"
			+ "</ul>";
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String EMAIL_ERRMSG = 
			  "<ul>"
			+ "<li>e.g.: username@domain.com</li>"
			+ "</ul>";
	//private static final String PHONE_PATTERN = "";
	
	
	public static boolean validate(final String validateType, final String validateStr)
	{
		switch(validateType)
		{
			case USERNAME_PATTERN:
				pattern = Pattern.compile(USERNAME_PATTERN);
			break;
			case PASSWORD_PATTERN:
				pattern = Pattern.compile(PASSWORD_PATTERN);
			break;
			case EMAIL_PATTERN:
				pattern = Pattern.compile(EMAIL_PATTERN);
			break;
			/*case PHONE_PATTERN:
				pattern = Pattern.compile(PHONE_PATTERN);
			break;*/
		}
		matcher = pattern.matcher(validateStr);
		return matcher.matches();
	}
	
	/*public RegExValidator(final String validateType)
	{
		switch(validateType)
		{
			case USERNAME_PATTERN:
				pattern = Pattern.compile(USERNAME_PATTERN);
			break;
			case PASSWORD_PATTERN:
				pattern = Pattern.compile(PASSWORD_PATTERN);
			break;
			
		}
	}*/
}
