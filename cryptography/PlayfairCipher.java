package cryptography;

import java.util.Scanner;

public class PlayfairCipher {

    private static final int MATRIX_SIZE = 5;
    private static char[][] playfairMatrix;

    public static String encrypt(String plaintext, String key) {
        generatePlayfairMatrix(key);

        StringBuilder ciphertext = new StringBuilder();
        plaintext = preprocessText(plaintext);

        for (int i = 0; i < plaintext.length(); i += 2) {
            char char1 = plaintext.charAt(i);
            char char2 = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

            int[] pos1 = findPosition(char1);
            int[] pos2 = findPosition(char2);

            int row1 = pos1[0], col1 = pos1[1];
            int row2 = pos2[0], col2 = pos2[1];

            if (row1 == row2) {
                col1 = (col1 + 1) % MATRIX_SIZE;
                col2 = (col2 + 1) % MATRIX_SIZE;
            } else if (col1 == col2) {
                row1 = (row1 + 1) % MATRIX_SIZE;
                row2 = (row2 + 1) % MATRIX_SIZE;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            ciphertext.append(playfairMatrix[row1][col1]);
            ciphertext.append(playfairMatrix[row2][col2]);
        }

        return ciphertext.toString();
    }

    private static void generatePlayfairMatrix(String key) {
        playfairMatrix = new char[MATRIX_SIZE][MATRIX_SIZE];
        key = preprocessText(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");

        int index = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                playfairMatrix[i][j] = key.charAt(index++);
            }
        }
    }

    private static String preprocessText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        return text.replace("J", "I");
    }

    private static int[] findPosition(char c) {
        int[] pos = new int[2];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (playfairMatrix[i][j] == c) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
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
