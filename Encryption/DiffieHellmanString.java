package encryption;

import javax.crypto.*;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.math.BigInteger;
import java.util.Base64;

public class DiffieHellmanString {

    public static void main(String[] args) {
        try {
            // Prime and generator values (you can choose different values)
            String primeStr = "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
                    "29024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
                    "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
                    "E485B576625E7EC6F44C42E9A63A3620FFFFFFFFFFFFFFFF";
            String generatorStr = "2";

            // Alice and Bob generate their private and public keys
            KeyPair aliceKeyPair = generateKeyPair(primeStr, generatorStr);
            KeyPair bobKeyPair = generateKeyPair(primeStr, generatorStr);

            // Alice sends her public key to Bob
            PublicKey alicePublicKey = aliceKeyPair.getPublic();
            String alicePublicKeyStr = keyToString(alicePublicKey);
            System.out.println("Alice's Public Key: " + alicePublicKeyStr);

            // Bob receives Alice's public key and generates shared secret
            PublicKey receivedAlicePublicKey = stringToPublicKey(alicePublicKeyStr);
            KeyAgreement bobKeyAgreement = generateKeyAgreement(bobKeyPair.getPrivate(), receivedAlicePublicKey);

            // Bob sends his public key to Alice
            PublicKey bobPublicKey = bobKeyPair.getPublic();
            String bobPublicKeyStr = keyToString(bobPublicKey);
            System.out.println("Bob's Public Key: " + bobPublicKeyStr);

            // Alice receives Bob's public key and generates shared secret
            PublicKey receivedBobPublicKey = stringToPublicKey(bobPublicKeyStr);
            KeyAgreement aliceKeyAgreement = generateKeyAgreement(aliceKeyPair.getPrivate(), receivedBobPublicKey);

            // Alice and Bob generate shared secrets
            byte[] aliceSharedSecret = generateSharedSecret(aliceKeyAgreement);
            byte[] bobSharedSecret = generateSharedSecret(bobKeyAgreement);

            // Both shared secrets should be the same
            System.out.println("Shared Secret (Alice): " + Base64.getEncoder().encodeToString(aliceSharedSecret));
            System.out.println("Shared Secret (Bob): " + Base64.getEncoder().encodeToString(bobSharedSecret));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyPair generateKeyPair(String primeStr, String generatorStr) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        BigInteger prime = new BigInteger(primeStr, 16);
        BigInteger generator = new BigInteger(generatorStr);

        DHParameterSpec dhParamSpec = new DHParameterSpec(prime, generator);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(dhParamSpec);

        return keyPairGenerator.generateKeyPair();
    }

    private static KeyAgreement generateKeyAgreement(PrivateKey privateKey, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement;
    }

    private static byte[] generateSharedSecret(KeyAgreement keyAgreement) {
        return keyAgreement.generateSecret();
    }

    private static String keyToString(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static PublicKey stringToPublicKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(keySpec);
    }
}

