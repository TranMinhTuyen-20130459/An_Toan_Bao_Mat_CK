package utils;

import java.util.List;
import java.util.Random;

public class BillUtil {
    public static String[] arr_customers = {"Trần Tuyên", "Phú Tài", "Tấn Đạt", "Dư Lập", "Văn Lênh"};
    public static String[] arr_address = {"Cư Xá A, Nông Lâm, Linh Trung, Thủ Đức, Hồ Chí Minh", "256 Lê Văn Quới, BHHA, Bình Tân, Hồ Chí Minh"};

    public static String getRandomElementInArray(String[] arr) {
        Random random = new Random();
        int randomIndex = random.nextInt(arr.length);
        return arr[randomIndex];
    }

    public static <T> T getRandomElementInList(List<T> list) {

        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static String generateRandomPhoneNumber() {
        Random random = new Random();

        // Số điện thoại bắt đầu bằng 0 và có 10 chữ số
        StringBuilder phoneNumber = new StringBuilder("0");

        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }

        return phoneNumber.toString();
    }

    public static String generateRandomEmail() {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "example.com", "test.com"};
        Random random = new Random();

        // Tên người dùng ngẫu nhiên
        String username = generateRandomString(8);

        // Chọn một domain ngẫu nhiên từ mảng domains
        String domain = domains[random.nextInt(domains.length)];

        // Kết hợp username, "@" và domain để tạo địa chỉ email
        return username + "@" + domain;
    }

    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public static double[] generateRandomPrices() {
        Random random = new Random();
        double[] prices = new double[2];
        prices[0] = random.nextDouble() * 1000;
        prices[1] = prices[0] + random.nextDouble() * 1000;
        return prices;
    }

}
