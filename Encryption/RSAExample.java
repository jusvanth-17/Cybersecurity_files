package encryption;

import java.security.*;

import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;

public class RSAExample {

    public static void main(String[] args) {
        try {
            // Generate Key Pair
            KeyPair keyPair = generateKeyPair();

            // Get public and private keys
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Input Text
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the plaintext: ");
            String originalText = scanner.nextLine();

            // Encrypt using public key
            byte[] encryptedBytes = encrypt(originalText, publicKey);
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            System.out.println("Encrypted Text: " + encryptedText);

            // Decrypt using private key
            byte[] decryptedBytes = decrypt(Base64.getDecoder().decode(encryptedText), privateKey);
            String decryptedText = new String(decryptedBytes);

            System.out.println("Decrypted Text: " + decryptedText);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate RSA Key Pair
    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // Encrypt or Decrypt Data using RSA Key
    private static byte[] encrypt(String data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    private static byte[] decrypt(byte[] encryptedData, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }
}
