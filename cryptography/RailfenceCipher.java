package cryptography;

import java.util.Scanner;

public class RailfenceCipher {

    public static String encrypt(String plaintext, int rails) {
        char[][] matrix = generateMatrix(plaintext, rails);
        return readCipherText(matrix, rails);
    }

    private static char[][] generateMatrix(String plaintext, int rails) {
        char[][] matrix = new char[rails][plaintext.length()];
        int row = 0;
        boolean down = true;

        for (int i = 0; i < plaintext.length(); i++) {
            matrix[row][i] = plaintext.charAt(i);

            if (down) {
                row++;
                if (row == rails - 1) {
                    down = false;
                }
            } else {
                row--;
                if (row == 0) {
                    down = true;
                }
            }
        }

        return matrix;
    }

    private static String readCipherText(char[][] matrix, int rails) {
        StringBuilder ciphertext = new StringBuilder();

        for (int row = 0; row < rails; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != '\u0000') {
                    ciphertext.append(matrix[row][col]);
                }
            }
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine().toUpperCase();

        System.out.print("Enter the number of rails: ");
        int rails = scanner.nextInt();

        String ciphertext = encrypt(plaintext, rails);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
