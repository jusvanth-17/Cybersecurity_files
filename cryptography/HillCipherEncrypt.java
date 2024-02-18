package cryptography;

import java.util.Scanner;

public class HillCipherEncrypt {

    private static final int MATRIX_SIZE = 3; // Size of the matrix (for simplicity, using a 2x2 matrix)

    public static String encrypt(String plaintext, int[][] keyMatrix) {
        plaintext = preprocessText(plaintext);
        int[] vector = new int[MATRIX_SIZE];
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += MATRIX_SIZE) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                vector[j] = plaintext.charAt(i + j) - 'A';
            }

            int[] resultVector = multiplyMatrix(keyMatrix, vector);

            for (int j = 0; j < MATRIX_SIZE; j++) {
                ciphertext.append((char) (resultVector[j] + 'A'));
            }
        }

        return ciphertext.toString();
    }

    private static int[] multiplyMatrix(int[][] matrix, int[] vector) {
        int[] resultVector = new int[MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                resultVector[i] += matrix[i][j] * vector[j];
            }
            resultVector[i] %= 26; // Modulo 26 to wrap around the alphabet
        }

        return resultVector;
    }

    private static String preprocessText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        // Padding 'X' if the length is not a multiple of MATRIX_SIZE
        while (text.length() % MATRIX_SIZE != 0) {
            text += 'X';
        }
        return text;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.println("Enter the key matrix (space-separated values, row-wise):");
        int[][] keyMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }

        String ciphertext = encrypt(plaintext, keyMatrix);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
