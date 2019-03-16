package inventory.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.collections.buffer.CircularFifoBuffer;




public class HashingPassword {
	static final String SALT ="inventory_management";
	public static String encrypt(String originPassword) {
		String result=null;
		byte[] salt = SALT.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] hashPass = md.digest(originPassword.getBytes(StandardCharsets.US_ASCII));
			result = Base64.getEncoder().encodeToString(hashPass).substring(0, 32);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) {
		String rs = encrypt("1234");
		System.out.println(rs);
	}

}
