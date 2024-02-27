package encryption;

import java.math.BigInteger;
import java.util.Scanner;

public class DiffieHellmanExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get public numbers P and G from the user
        System.out.print("Enter the value of P: ");
        BigInteger p = scanner.nextBigInteger();

        System.out.print("Enter the value of G: ");
        BigInteger g = scanner.nextBigInteger();

        // Get private keys for Alice and Bob from the user
        System.out.print("Enter the private key a for Alice: ");
        BigInteger a = scanner.nextBigInteger();

        System.out.print("Enter the private key b for Bob: ");
        BigInteger b = scanner.nextBigInteger();

        // Display public numbers and private keys
        System.out.println("The value of P: " + p);
        System.out.println("The value of G: " + g);
        System.out.println("The private key a for Alice: " + a);
        System.out.println("The private key b for Bob: " + b);

        // Step 3: Compute public values
        BigInteger x = g.modPow(a, p);
        BigInteger y = g.modPow(b, p);

        // Step 4: Exchange public numbers

        // Step 5: Receive public keys
        BigInteger receivedY = y; // In a real scenario, this would be received from the other party
        BigInteger receivedX = x; // In a real scenario, this would be received from the other party

        // Step 6: Compute symmetric keys
        BigInteger ka = receivedY.modPow(a, p);
        BigInteger kb = receivedX.modPow(b, p);

        // Display the symmetric keys
        System.out.println("Secret key for Alice is: " + ka.mod(p));
        System.out.println("Secret key for Bob is: " + kb.mod(p));

        scanner.close();
    }
}
