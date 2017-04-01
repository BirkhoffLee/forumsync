package me.sisko.phpbb;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
	public static String hash(String password) {
		try {
			MessageDigest m=MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0,password.length());
			return new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
