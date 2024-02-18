package cryptography;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
import java.security.SecureRandom;

public class SymmetricCryptoExample {

    public static String encrypt(String plaintext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(ciphertext);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try {
            // Generate a secret key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // You can use 128, 192, or 256 bits
            SecretKey secretKey = keyGenerator.generateKey();

            // User input for plaintext
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter plaintext: ");
            String originalMessage = scanner.nextLine();

            System.out.println("Original message: " + originalMessage);
            System.out.println("Secret Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

            // Encrypt
            String encryptedMessage = encrypt(originalMessage, secretKey);
            System.out.println("Encrypted message: " + encryptedMessage);

            // Decrypt
            String decryptedMessage = decrypt(encryptedMessage, secretKey);
            System.out.println("Decrypted message: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
