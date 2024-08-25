package net.aditri.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptorSHA {
	public enum SHAType
	{
		SHA1(1),
		SHA256(256),
		SHA384(384),
		SHA512(512);

		private final int value;    

		private SHAType(int value) {
			this.value = value;
		}
	
		public int getValue() {
			return value;
		}
	}
	public String getSHAHash(String passwordToHash,SHAType shaType) throws NoSuchAlgorithmException
	{
		String generatedPassword = null;
		String salt = getSalt();
		switch(shaType)
		{
			case SHA1:
				generatedPassword = get_SHA_1_SecurePassword(passwordToHash, salt);
			break;
			case SHA256:
				generatedPassword = get_SHA_256_SecurePassword(passwordToHash, salt);
			break;
			case SHA384:
				generatedPassword = get_SHA_384_SecurePassword(passwordToHash, salt);
			break;
			case SHA512:
				generatedPassword = get_SHA_512_SecurePassword(passwordToHash, salt);
			default:
				generatedPassword = get_SHA_1_SecurePassword(passwordToHash, salt);
			break;
		}
		return generatedPassword;
	}
	private static String get_SHA_1_SecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try 
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
	private static String get_SHA_256_SecurePassword(String passwordToHash, String salt)
	{
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	private static String get_SHA_384_SecurePassword(String passwordToHash, String salt)
	{
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-384");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	private static String get_SHA_512_SecurePassword(String passwordToHash, String salt)
	{
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
     
    //Add salt
    private static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
}
