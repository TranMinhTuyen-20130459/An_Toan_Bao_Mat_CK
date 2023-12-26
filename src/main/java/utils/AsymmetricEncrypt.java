package utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricEncrypt {
    public static final String RSA = "RSA";
    public static final String transformation_RSA = "RSA";
    private KeyPair key_pair;
    private PublicKey public_key;
    private PrivateKey private_key;
    private String algorithm;

    public AsymmetricEncrypt(String name_algorithm) {
        this.algorithm = name_algorithm;
    }

    public void generateKey(int key_size) throws Exception {
        KeyPairGenerator keyGenerator = null;
        keyGenerator = KeyPairGenerator.getInstance(algorithm);
        keyGenerator.initialize(key_size);
        key_pair = keyGenerator.generateKeyPair();
        public_key = key_pair.getPublic();
        private_key = key_pair.getPrivate();
    }

    public String encryptToBase64(String text, String transformation) throws Exception {

        if (private_key == null) throw new Exception("private_key is null");
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, private_key);
        var byte_text = text.getBytes(StandardCharsets.UTF_8);
        var byte_encrypted = cipher.doFinal(byte_text);

        return Base64.getEncoder().encodeToString(byte_encrypted);
    }

    public String decryptFromBase64(String encrypted, String transformation) throws Exception {

        if (public_key == null) throw new Exception("public_key is null");
        var byte_encrypted = Base64.getDecoder().decode(encrypted.getBytes());

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, public_key);

        var byte_decrypted = cipher.doFinal(byte_encrypted);
        return new String(byte_decrypted, StandardCharsets.UTF_8);
    }

    public String exportPublicKey() {
        if (public_key == null) return null;
        var byte_public_key = public_key.getEncoded();
        return Base64.getEncoder().encodeToString(byte_public_key);
    }

    public String exportPrivateKey() {
        if (private_key == null) return null;
        var byte_private_key = private_key.getEncoded();
        return Base64.getEncoder().encodeToString(byte_private_key);
    }

    public PublicKey importPublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (publicKey == null || publicKey.isEmpty()) return null;

        var byte_public_key = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byte_public_key);
        public_key = keyFactory.generatePublic(keySpec);
        return public_key;
    }

    public PrivateKey importPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (privateKey == null || privateKey.isEmpty()) return null;

        byte[] byte_private_key = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byte_private_key);
        private_key = keyFactory.generatePrivate(keySpec);
        return private_key;
    }

    public static void main(String[] args) throws Exception {

        var rsa = new AsymmetricEncrypt(AsymmetricEncrypt.RSA);
        rsa.generateKey(512);

        System.out.println("PUBLIC_KEY=" + rsa.exportPublicKey() + "\nPRIVATE_KEY=" + rsa.exportPrivateKey());

        var transformation = "RSA";
        var text = "TRẦN MINH TUYÊN";
        var encrypted_text = rsa.encryptToBase64(text, transformation);
        var decrypted_text = rsa.decryptFromBase64(encrypted_text, transformation);

        System.out.println(encrypted_text);
        System.out.println(decrypted_text);
    }


}
