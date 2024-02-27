package encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;

public class DoubleDESEncryptionDecryption {

    public static void main(String[] args) throws Exception {
        // 64-bit plain text
        String plainText = "0123456789ABCDEF";

        // Two 64-bit keys
        String key1 = "0123456789ABCDEF";
        String key2 = "FEDCBA9876543210";

        // Convert keys to byte arrays
        byte[] key1Bytes = hexStringToByteArray(key1);
        byte[] key2Bytes = hexStringToByteArray(key2);

        // Convert plain text to byte array
        byte[] plainTextBytes = hexStringToByteArray(plainText);

        // First DES encryption
        byte[] middleText = desEncrypt(plainTextBytes, key1Bytes);

        // Second DES encryption
        byte[] cipherText = desEncrypt(middleText, key2Bytes);

        // Printing intermediate and final results
        System.out.println("Plain Text: " + plainText);
        System.out.println("Key 1: " + key1);
        System.out.println("Key 2: " + key2);
        System.out.println("Intermediate Text: " + byteArrayToHexString(middleText));
        System.out.println("Cipher Text: " + byteArrayToHexString(cipherText));

        // Decrypting
        byte[] decryptedMiddleText = desDecrypt(cipherText, key2Bytes);
        byte[] decryptedPlainText = desDecrypt(decryptedMiddleText, key1Bytes);

        // Printing decrypted results
        System.out.println("Decrypted Intermediate Text: " + byteArrayToHexString(decryptedMiddleText));
        System.out.println("Decrypted Plain Text: " + byteArrayToHexString(decryptedPlainText));
    }

    private static byte[] desEncrypt(byte[] data, byte[] key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    private static byte[] desDecrypt(byte[] data, byte[] key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    private static String byteArrayToHexString(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }
}

