package cryptography;

import java.util.Scanner;

public class PolyalphabeticCipher {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());

            if (Character.isLetter(plainChar)) {
                int rowIndex = ALPHABET.indexOf(plainChar);
                int colIndex = ALPHABET.indexOf(keyChar);

                char encryptedChar = getVigenereTableChar(rowIndex, colIndex);
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(plainChar);
            }
        }

        return ciphertext.toString();
    }

    private static char getVigenereTableChar(int row, int col) {
        return ALPHABET.charAt((row + col) % 26);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
