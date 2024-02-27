package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class TripleDESOneKey {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plain text: ");
        String plainText = scanner.nextLine();

        try {
            // Generate a 168-bit key for Triple DES
            SecretKey key = generateKey();

            byte[] cipherText = tripleDesEncrypt(plainText.getBytes(StandardCharsets.UTF_8), key);

            System.out.println("Plain Text: " + plainText);
            System.out.println("Key: " + keyToBase64(key));
            System.out.println("Cipher Text: " + byteArrayToBase64(cipherText));

            byte[] decryptedText = tripleDesDecrypt(cipherText, key);

            System.out.println("Decrypted Text: " + new String(decryptedText, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(168); // 168-bit key for Triple DES
        return keyGen.generateKey();
    }

    private static byte[] tripleDesEncrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static byte[] tripleDesDecrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static String keyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static String byteArrayToBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }
}
