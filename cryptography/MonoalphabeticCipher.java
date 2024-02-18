package cryptography;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MonoalphabeticCipher {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String KEY = "YBEFWXDIZUMSHGQJRTLCNOVKPA";

    public static String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase();
        StringBuilder ciphertext = new StringBuilder();

        Map<Character, Character> encryptionMap = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            encryptionMap.put(ALPHABET.charAt(i), KEY.charAt(i));
        }

        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                ciphertext.append(encryptionMap.get(c));
            } else {
                ciphertext.append(c);
            }
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        String ciphertext = encrypt(plaintext);
        System.out.println("Ciphertext: " + ciphertext);
    }
}