package cryptography;

import java.security.SecureRandom;
import java.util.Scanner;

public class OneTimePadCipher {

    public static String generateRandomKey(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            key.append((char) (random.nextInt(26) + 'A'));
        }

        return key.toString();
    }

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i);

            int encryptedChar = (plainChar + keyChar) % 26 + 'A';
            ciphertext.append((char) encryptedChar);
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine().toUpperCase();

        if (!plaintext.matches("[A-Z]+")) {
            System.out.println("Invalid input. Please enter only uppercase letters.");
            return;
        }

        String key = generateRandomKey(plaintext.length());
        System.out.println("Generated Key: " + key);

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
