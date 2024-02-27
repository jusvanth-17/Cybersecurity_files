package encryption;

import java.security.*;

import java.util.Base64;
import java.util.Scanner;

public class DSASample {

    public static void main(String[] args) {
        try {
            // Generate DSA key pair
            KeyPair keyPair = generateDSAKeyPair();

            // Get input message from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the message to sign: ");
            String message = scanner.nextLine();

            // Sign the message
            String signature = signMessage(message, keyPair.getPrivate());

            // Verify the signature
            boolean isVerified = verifySignature(message, signature, keyPair.getPublic());

            // Display results
            System.out.println("\nOriginal Message: " + message);
            System.out.println("Signature: " + signature);
            System.out.println("Signature Verified: " + isVerified);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyPair generateDSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(1024); // You can adjust the key size
        return keyPairGenerator.generateKeyPair();
    }

    private static String signMessage(String message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    private static boolean verifySignature(String message, String signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withDSA");
        verifier.initVerify(publicKey);
        verifier.update(message.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return verifier.verify(signatureBytes);
    }
}
