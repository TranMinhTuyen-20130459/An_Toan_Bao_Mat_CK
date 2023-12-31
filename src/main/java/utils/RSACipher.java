package utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSACipher {

    public String encrypt(String text, String key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String text, String key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return new String(cipher.doFinal(Base64.getDecoder().decode(text)), StandardCharsets.UTF_8);
    }

    public String[] generateKeyPair(int size) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(size);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        return new String[] {
                Base64.getEncoder().encodeToString(publicKey.getEncoded()),
                Base64.getEncoder().encodeToString(privateKey.getEncoded())
        };
    }

    public static void main(String[] args) throws Exception {
        RSACipher rsaCipher = new RSACipher();
        String[] keyPair = rsaCipher.generateKeyPair(512);
        System.out.println("PUBLIC_KEY=" + keyPair[0] + "\nPRIVATE_KEY=" + keyPair[1]);
        System.out.println("-----------------------------------------------------------------");
        keyPair = rsaCipher.generateKeyPair(512);
        System.out.println("PUBLIC_KEY=" + keyPair[0] + "\nPRIVATE_KEY=" + keyPair[1]);


//        String cipherText = rsaCipher.encrypt("addf120b430021c36c232c99ef8d926aea2acd6b", keyPair[1]);
//        String plainText = rsaCipher.decrypt(cipherText, keyPair[0]);
//
//        System.out.println(cipherText);
//        System.out.println(plainText);
    }
}
