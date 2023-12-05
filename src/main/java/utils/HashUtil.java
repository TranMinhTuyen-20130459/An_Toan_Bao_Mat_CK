package utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtil {

    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";

    public String hashText(String input_text, String algorithm) throws Exception {
        // Tạo một đối tượng MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);

        // Băm đoạn văn bản đầu vào thành một mảng byte
        byte[] message_digest = md.digest(input_text.getBytes());

        // Chuyển mảng byte thành số nguyên BigInteger
        BigInteger number = new BigInteger(1, message_digest);

        // Chuyển đổi số nguyên sang chuỗi theo hệ cơ số 16 (hex)
        return number.toString(16);
    }

    public static void main(String[] args) throws Exception {

        var hash = new HashUtil();
        System.out.println(hash.hashText("TRAN MINH TUYEN", HashUtil.SHA_1));

    }

}
