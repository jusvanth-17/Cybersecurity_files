package encryption;

import java.security.*;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;

public class RSASignature {

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

            System.out.println("Original Text: " + originalText);
            System.out.println("Encrypted Text: " + encryptedText);

            // Decrypt using private key
            byte[] decryptedBytes = decrypt(Base64.getDecoder().decode(encryptedText), privateKey);
            String decryptedText = new String(decryptedBytes);

            System.out.println("Decrypted Text: " + decryptedText);

            // Sign using private key
            byte[] signature = sign(originalText, privateKey);
            String signatureText = Base64.getEncoder().encodeToString(signature);

            System.out.println("Digital Signature: " + signatureText);

            // Verify signature using public key
            boolean isVerified = verify(originalText, signature, publicKey);
            System.out.println("Signature Verification: " + isVerified);

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

    // Sign or Verify using RSA Key
    private static byte[] sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    private static boolean verify(String data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifySignature = Signature.getInstance("SHA256withRSA");
        verifySignature.initVerify(publicKey);
        verifySignature.update(data.getBytes());
        return verifySignature.verify(signature);
    }
}
