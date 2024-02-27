package encryption;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

import java.math.BigInteger;
import java.security.*;
import java.util.Base64;

public class DiffieHellmanKeyExchange {

    public static void main(String[] args) {
        try {
            // Create parameters for Diffie-Hellman key exchange
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman");
            DHParameterSpec dhParamSpec = new DHParameterSpec(getP(), getG());
            keyPairGenerator.initialize(dhParamSpec);

            // Alice generates her key pair
            KeyPair aliceKeyPair = keyPairGenerator.generateKeyPair();

            // Bob generates his key pair using the same parameters
            KeyPair bobKeyPair = keyPairGenerator.generateKeyPair();

            // Alice and Bob exchange their public keys
            KeyAgreement aliceKeyAgreement = KeyAgreement.getInstance("DiffieHellman");
            aliceKeyAgreement.init(aliceKeyPair.getPrivate());
            aliceKeyAgreement.doPhase(bobKeyPair.getPublic(), true);

            KeyAgreement bobKeyAgreement = KeyAgreement.getInstance("DiffieHellman");
            bobKeyAgreement.init(bobKeyPair.getPrivate());
            bobKeyAgreement.doPhase(aliceKeyPair.getPublic(), true);

            // Alice and Bob generate and print their shared secrets
            byte[] aliceSharedSecret = aliceKeyAgreement.generateSecret();
            byte[] bobSharedSecret = bobKeyAgreement.generateSecret();

            System.out.println("Shared Secret (Alice): " + Base64.getEncoder().encodeToString(aliceSharedSecret));
            System.out.println("Shared Secret (Bob): " + Base64.getEncoder().encodeToString(bobSharedSecret));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // These prime (p) and generator (g) values are standard for Diffie-Hellman
    private static BigInteger getP() {
        return new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
                "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A89" +
                "9CAC3844B81B3D1306C11B9D9EE1F4F5B2B9AEAE075D8E50B849D6A91547F11C40740D491696865FD367501110180DF099860469" +
                "9A63A7E9DC2A6D733B6CC7EF48A03BBF1883B00F9110471B14A91ABD9D6B24B8B701122B641D4F1A8A49D265B9BC5D0AAB5A9995" +
                "9E27A8485795E7E3896EFD13F47CA887C4B5B546BCF7585CDE32D8A1B710BDEF8D8E79B18569F3B555132F0C8E27A96C7D44BFD5F8" +
                "343020FFFFFFFFFFFFFFFF", 16);
    }

    private static BigInteger getG() {
        return new BigInteger("2");
    }
}

