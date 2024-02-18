package cryptography;

import java.util.Scanner;

public class HillCipherEncryptDecrypt {

    private static final int MATRIX_SIZE = 3; // Size of the matrix (you can choose other sizes as well)

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

    public static String decrypt(String ciphertext, int[][] keyMatrix) {
        int[][] inverseKeyMatrix = calculateInverseMatrix(keyMatrix);
        ciphertext = preprocessText(ciphertext);
        int[] vector = new int[MATRIX_SIZE];
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += MATRIX_SIZE) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                vector[j] = ciphertext.charAt(i + j) - 'A';
            }

            int[] resultVector = multiplyMatrix(inverseKeyMatrix, vector);

            for (int j = 0; j < MATRIX_SIZE; j++) {
                decryptedText.append((char) (resultVector[j] + 'A'));
            }
        }

        return decryptedText.toString();
    }

    private static int[] multiplyMatrix(int[][] matrix, int[] vector) {
        int[] resultVector = new int[MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                resultVector[i] += matrix[i][j] * vector[j];
            }
            resultVector[i] = (resultVector[i] % 26 + 26) % 26; // Modulo 26 to wrap around the alphabet
        }

        return resultVector;
    }

    private static int[][] calculateInverseMatrix(int[][] keyMatrix) {
        int det = keyMatrix[0][0] * (keyMatrix[1][1] * keyMatrix[2][2] - keyMatrix[1][2] * keyMatrix[2][1])
                - keyMatrix[0][1] * (keyMatrix[1][0] * keyMatrix[2][2] - keyMatrix[1][2] * keyMatrix[2][0])
                + keyMatrix[0][2] * (keyMatrix[1][0] * keyMatrix[2][1] - keyMatrix[1][1] * keyMatrix[2][0]);

        int detInverse = modInverse(det, 26);

        int[][] adjugateMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];

        adjugateMatrix[0][0] = keyMatrix[1][1] * keyMatrix[2][2] - keyMatrix[1][2] * keyMatrix[2][1];
        adjugateMatrix[0][1] = keyMatrix[0][2] * keyMatrix[2][1] - keyMatrix[0][1] * keyMatrix[2][2];
        adjugateMatrix[0][2] = keyMatrix[0][1] * keyMatrix[1][2] - keyMatrix[0][2] * keyMatrix[1][1];
        adjugateMatrix[1][0] = keyMatrix[1][2] * keyMatrix[2][0] - keyMatrix[1][0] * keyMatrix[2][2];
        adjugateMatrix[1][1] = keyMatrix[0][0] * keyMatrix[2][2] - keyMatrix[0][2] * keyMatrix[2][0];
        adjugateMatrix[1][2] = keyMatrix[0][2] * keyMatrix[1][0] - keyMatrix[0][0] * keyMatrix[1][2];
        adjugateMatrix[2][0] = keyMatrix[1][0] * keyMatrix[2][1] - keyMatrix[1][1] * keyMatrix[2][0];
        adjugateMatrix[2][1] = keyMatrix[0][1] * keyMatrix[2][0] - keyMatrix[0][0] * keyMatrix[2][1];
        adjugateMatrix[2][2] = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];

        int[][] inverseMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                inverseMatrix[i][j] = (adjugateMatrix[j][i] * detInverse) % 26;
                if (inverseMatrix[i][j] < 0) {
                    inverseMatrix[i][j] += 26;
                }
            }
        }

        return inverseMatrix;
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
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

        String decryptedText = decrypt(ciphertext, keyMatrix);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}

