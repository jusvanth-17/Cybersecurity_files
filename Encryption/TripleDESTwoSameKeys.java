package encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class TripleDESTwoSameKeys {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plain text: ");
        String plainText = scanner.nextLine();

        try {
            // Generate two 168-bit keys for Triple DES
            SecretKey key1 = generateKey();
            SecretKey key2 = generateKey();

            byte[] cipherText = tripleDesEncrypt(plainText.getBytes(StandardCharsets.UTF_8), key1, key2);

            System.out.println("Plain Text: " + plainText);
            System.out.println("Key 1: " + keyToBase64(key1));
            System.out.println("Key 2: " + keyToBase64(key2));
            System.out.println("Cipher Text: " + byteArrayToBase64(cipherText));

            byte[] decryptedText = tripleDesDecrypt(cipherText, key1, key2);

            System.out.println("Decrypted Text: " + new String(decryptedText, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static SecretKey generateKey() throws Exception {
        // Generate a 168-bit key as a byte array
        byte[] keyBytes = new byte[24]; // 168 bits (24 bytes)
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) i; // Set to a constant value for demonstration purposes
        }

        // Create a SecretKey from the byte array
        return new SecretKeySpec(keyBytes, "DESede");
    }

    private static byte[] tripleDesEncrypt(byte[] data, SecretKey key1, SecretKey key2) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key1);
        byte[] intermediateText = cipher.doFinal(data);

        cipher.init(Cipher.DECRYPT_MODE, key2);
        intermediateText = cipher.doFinal(intermediateText);

        cipher.init(Cipher.ENCRYPT_MODE, key1);
        return cipher.doFinal(intermediateText);
    }

    private static byte[] tripleDesDecrypt(byte[] data, SecretKey key1, SecretKey key2) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key1);
        byte[] intermediateText = cipher.doFinal(data);

        cipher.init(Cipher.ENCRYPT_MODE, key2);
        intermediateText = cipher.doFinal(intermediateText);

        cipher.init(Cipher.DECRYPT_MODE, key1);
        return cipher.doFinal(intermediateText);
    }

    private static String keyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static String byteArrayToBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }
}
