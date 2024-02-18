package cryptography;

import java.util.Scanner;

public class CaesarCipher {

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a message to encrypt:");
		String message =sc.nextLine();
		System.out.print("Enter the shift value for message:");
		int shift = sc.nextInt();
		
		String encryptedMessage = encrypt(message, shift);
		System.out.println("Encrypted message:" +encryptedMessage);
		
		String decryptedMessage = decrypt(encryptedMessage, shift);
		System.out.println("Decrypted message:" +decryptedMessage);
	}
	public static String encrypt(String message, int shift)
	{
		StringBuilder encryptedMessage = new StringBuilder();
		message = message.toLowerCase();
		for (int i=0;i<message.length();i++) 
		{
			char c = message.charAt(i);
			if(Character.isLetter(c))
			{
				c = (char)((c-'a'+shift+26)%26+'a');
			}
			encryptedMessage.append(c);
		}
		return encryptedMessage.toString();
		
	}
	public static String decrypt(String message, int shift)
	{
		StringBuilder decryptedMessage = new StringBuilder();
		
		for (int i=0;i<message.length();i++) 
		{
			char c = message.charAt(i);
			if(Character.isLetter(c))
			{
				c = (char)((c-'a'- shift+26)%26+'a');
			}
			decryptedMessage.append(c);
		}
		return decryptedMessage.toString();
		
	}
}

