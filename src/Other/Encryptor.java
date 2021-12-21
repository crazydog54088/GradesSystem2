package Other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
	
	private String result;
	
	public Encryptor(String info) {
		try {
			this.result = printResult(encrypt(info));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String getResult() {
		return this.result;
	}
	
	private String printResult(byte[] array) {
		String result = "";
		if (array == null) {
			System.out.println("null");
			return null;
		}
		for(byte Element: array) {
			result += Element + " ";
		}
		return result;
	}
	
	private byte[] encrypt(String info) throws NoSuchAlgorithmException {   
	    MessageDigest md5 = MessageDigest.getInstance("MD5");  
	    byte[] srcBytes = info.getBytes();  
	    md5.update(srcBytes);    
	    byte[] resultBytes = md5.digest();  
	    return resultBytes;  
	}

}
