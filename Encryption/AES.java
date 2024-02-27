/**
 	* Possible key size values are 128, 192 and 256
 * Possible T_len values are 128, 120, 112, 104 and 96
 * Initialization vector (IV) A vector used in defining the starting point of a cryptographic process
 */

package encryption;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AES {

	private SecretKey key;
	private int KEY_SIZE = 128;
	private int T_LEN=128;
	private Cipher encryptionCipher;
	
	public void init() throws Exception{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(KEY_SIZE);
		key = generator.generateKey();
	}
	
	public String encrypt(String message) throws Exception{
		byte[] messageInBytes = message.getBytes();
		encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
		byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
		return encode(encryptedBytes);
	}
	
	public String decrypt(String encryptedMessage) throws Exception{
		byte[] messageInBytes  = decode(encryptedMessage);
		Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN,encryptionCipher.getIV());
		decryptionCipher.init(Cipher.DECRYPT_MODE,key,spec);
		byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
		return new String(decryptedBytes);
	}
	private String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
		
		public static void main(String args[]) {
			try {
				AES aes = new AES();
				aes.init();
				String encryptedMessage = aes.encrypt("Welcome to AES Encryption");
				String decryptedMessage= aes.decrypt(encryptedMessage);
				System.err.println("Encrypted message ="+encryptedMessage);
				System.err.println("Decrypted message ="+decryptedMessage);
			}catch(Exception ignored) {}
		
	}
}
