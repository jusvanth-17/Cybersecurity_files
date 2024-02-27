package encryption;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import java.util.Base64;

public class RSAKeyExchange {

    public static void main(String[] args) {
        try {
            // Alice and Bob generate their key pairs
            KeyPair aliceKeyPair = generateKeyPair();
            KeyPair bobKeyPair = generateKeyPair();

            // Alice sends her public key to Bob
            PublicKey alicePublicKey = aliceKeyPair.getPublic();
            String alicePublicKeyStr = keyToString(alicePublicKey);
            System.out.println("Alice's Public Key: " + alicePublicKeyStr);

            // Bob receives Alice's public key and encrypts a shared secret with it
            PublicKey receivedAlicePublicKey = stringToPublicKey(alicePublicKeyStr);
            String sharedSecret = generateRandomSharedSecret();

            byte[] encryptedSharedSecret = encrypt(sharedSecret, receivedAlicePublicKey);
            String encryptedSharedSecretStr = Base64.getEncoder().encodeToString(encryptedSharedSecret);
            System.out.println("Encrypted Shared Secret: " + encryptedSharedSecretStr);

            // Bob sends the encrypted shared secret to Alice

            // Alice decrypts the shared secret with her private key
            PrivateKey alicePrivateKey = aliceKeyPair.getPrivate();
            byte[] decryptedSharedSecret = decrypt(Base64.getDecoder().decode(encryptedSharedSecretStr), alicePrivateKey);
            String decryptedSharedSecretStr = new String(decryptedSharedSecret);

            // Now both Alice and Bob have the shared secret
            System.out.println("Decrypted Shared Secret: " + decryptedSharedSecretStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate RSA Key Pair
    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // Encrypt or Decrypt Data using RSA Key
    private static byte[] encrypt(String data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    private static byte[] decrypt(byte[] encryptedData, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    // Convert Key to String
    private static String keyToString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // Convert String to Key
    private static PublicKey stringToPublicKey(String keyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    // Generate a random shared secret
    private static String generateRandomSharedSecret() {
        return Base64.getEncoder().encodeToString(generateRandomBytes(16)); // Adjust the length as needed
    }

    // Generate random bytes
    private static byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }
}
