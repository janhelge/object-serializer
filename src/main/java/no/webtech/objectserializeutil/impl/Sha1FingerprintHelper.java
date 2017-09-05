package no.webtech.objectserializeutil.impl;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Sha1FingerprintHelper is a helper class to create Sha1 based 
 *  implementations easily.
 * 
 * @author jhs
 * 
 */
public class Sha1FingerprintHelper {
	
	public static String toSHA1(byte[] convertme) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"SHA-1 dows not exist as algorithm -- very strange and unlikely to happen");
		}
		byte[] b = md.digest(convertme);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
	
	public static String toSHA1(String convertme) {
		if (convertme == null) throw new NullPointerException("Unlogical to request SHA1 of input=<null>, please check input");
		else
			return toSHA1(convertme.trim().replaceAll("[\r\n ]", " ").getBytes());
	}
}
