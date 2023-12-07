package utils;

import model.Bill;
import model.BillDetail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Tạo danh sách chi tiết hóa đơn cho một hóa đơn cụ thể với số lượng và sản phẩm ngẫu nhiên.
     *
     * @param id_bill         ID của hóa đơn cho danh sách chi tiết.
     * @param list_id_product Danh sách ID sản phẩm để chọn ngẫu nhiên.
     * @param number          Số lượng chi tiết hóa đơn cần tạo.
     * @return Danh sách chi tiết hóa đơn được tạo.
     */
    public static <T> List<BillDetail> generateBillDetailsById(int id_bill, List<T> list_id_product, int number) {
        var bill_details = new ArrayList<BillDetail>();
        var shuffled_list = new ArrayList<>(list_id_product);
        var random = new Random();

        // Trộn ngẫu nhiên danh sách id_product
        Collections.shuffle(shuffled_list);

        for (int i = 0; i < number; i++) {
            T idProduct = shuffled_list.get(i);

            // Giả sử quantity, listed_price, và current_price được tạo ra ngẫu nhiên
            int quantity = random.nextInt(10) + 1; // Số lượng từ 1 đến 10
            BigDecimal listedPrice = roundToInt(new BigDecimal(Math.pow(10, 6) * random.nextDouble()));
            BigDecimal currentPrice = roundToInt(new BigDecimal(Math.pow(10, 6) * random.nextDouble()));

            // Tạo đối tượng BillDetail và thêm vào danh sách
            BillDetail billDetail = BillDetail.builder()
                    .id_bill(id_bill)
                    .id_product((Integer) idProduct)
                    .quantity(quantity)
                    .listed_price(listedPrice)
                    .current_price(currentPrice)
                    .build();

            bill_details.add(billDetail);
        }

        return bill_details;
    }

    /**
     * Làm tròn giá trị BigDecimal đến số nguyên.
     *
     * @param value Giá trị BigDecimal cần làm tròn.
     * @return Giá trị được làm tròn đến số nguyên.
     */
    private static BigDecimal roundToInt(BigDecimal value) {
        return value.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Tạo đối tượng hóa đơn mới với thông tin ngẫu nhiên.
     *
     * @param list_id_users       Danh sách ID người dùng để chọn ngẫu nhiên.
     * @param list_id_status_bill Danh sách ID trạng thái hóa đơn để chọn ngẫu nhiên.
     * @param list_id_city        Danh sách ID thành phố để chọn ngẫu nhiên.
     * @param arr_customers       Mảng chứa tên khách hàng để chọn ngẫu nhiên.
     * @param arr_address         Mảng chứa địa chỉ để chọn ngẫu nhiên.
     * @return Đối tượng hóa đơn mới được tạo.
     */
    public static <T> Bill generateBill(List<T> list_id_users,
                                        List<T> list_id_status_bill,
                                        List<T> list_id_city,
                                        String[] arr_customers,
                                        String[] arr_address) {

        // Tạo mảng giá trị ngẫu nhiên cho các thành phần giá của hóa đơn
        double[] prices = BillUtil.generateRandomPrices();

        // Làm tròn thời điểm đặt hàng đến giây gần nhất
        Instant roundedTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        // Tạo đối tượng hóa đơn mới
        Bill bill = Bill.builder()
                .id_user((int) BillUtil.getRandomElementInList(list_id_users))
                .id_status_bill((int) BillUtil.getRandomElementInList(list_id_status_bill))
                .id_city((int) BillUtil.getRandomElementInList(list_id_city))
                .name_customer(BillUtil.getRandomElementInArray(arr_customers))
                .phone_customer(BillUtil.generateRandomPhoneNumber())
                .email_customer(BillUtil.generateRandomEmail())
                .address_customer(BillUtil.getRandomElementInArray(arr_address))
                .bill_price(prices[0])
                .total_price(prices[1])
                .time_order(Timestamp.from(roundedTime)).build();

        return bill;
    }
}