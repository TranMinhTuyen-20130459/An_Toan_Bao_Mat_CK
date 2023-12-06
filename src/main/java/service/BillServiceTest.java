package service;

import database.dao.*;
import helper.ResultFunction;
import model.PublicKey;
import utils.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.stream.IntStream;

public class BillServiceTest {

    /**
     * Thêm một số hóa đơn ngẫu nhiên(bao gồm cả chi tiết hóa đơn) vào cơ sở dữ liệu.
     * đồng thời thêm public_key để giải mã hóa đơn tương ứng
     *
     * @param quantity Số lượng hóa đơn cần thêm.
     * @return Kết quả thực hiện: SUCCESS nếu thành công, ERROR nếu có lỗi.
     * @throws SQLException Nếu có lỗi kết nối hoặc thao tác trên cơ sở dữ liệu.
     */
    public static int addDataToTableBills(int quantity) throws SQLException {

        var billDAO = new BillDAO();
        var publicKeyDAO = new PublicKeyDAO();

        // Khởi tạo object dùng để mã hóa đơn hàng
        var rsa = new AsymmetricEncrypt(AsymmetricEncrypt.RSA);

        Connection connect = null;

        try {
            connect = billDAO.connectDB.getConn();
            connect.setAutoCommit(false); // Bắt đầu giao tác

            var list_id_users = new CustomerDAOTest().getAllIdUser();
            var list_id_status_bill = new StatusBillDAOTest().getAllIdStatus();
            var list_id_city = new CityDAOTest().getAllIdCity();
            var list_id_product = new ProductDAOTest().getAllIdProduct();

            Random random = new Random();

            // Sử dụng IntStream để tạo số lượng hóa đơn
            IntStream.range(0, quantity).forEachOrdered(i -> {
                try {

                    // tạo đơn hàng ngẫu nhiên
                    var bill = BillUtil.generateBill(list_id_users, list_id_status_bill, list_id_city, BillUtil.arr_customers, BillUtil.arr_address);
                    int id_bill = billDAO.addBill(bill);

                    // tạo danh sách chi tiết đơn hàng ngẫu nhiên
                    var list_bill_details = BillUtil.generateBillDetailsById(id_bill, list_id_product, random.nextInt(10) + 1);

                    list_bill_details.forEach(item -> {
                        try {
                            billDAO.addBillDetail(item);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    bill.setBill_details(list_bill_details);

                    // Tạo private_key và public_key để mã hóa và giải mã đơn hàng
                    rsa.generateKey(512);

                    // Cập nhật id_bill trước khi hash
                    bill.setId_bill(id_bill);

                    // Hash và mã hóa đơn hàng bằng thuật toán SHA_1 && RSA
                    var hash_bill = HashUtil.hashText(bill.toString(), HashUtil.SHA_1);
                    var hash_bill_encrypted = rsa.encryptToBase64(hash_bill, AsymmetricEncrypt.transformation_RSA);

                    // Cập nhật chuỗi hash_bill_encrypted
                    bill.setHash_bill_encrypted(hash_bill_encrypted);

                    // Cập nhật chuỗi hash_bill_encrypted vào table bills
                    billDAO.updateBill(bill);

                    // Thêm public_key để giải mã đơn hàng vào table public_keys
                    var pk = PublicKeyUtil.generateObjectPublicKey(bill.getId_user(), rsa.exportPublicKey(), bill.getTime_order());
                    publicKeyDAO.addPublicKey(pk);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            connect.commit(); // Kết thúc giao tác
            return ResultFunction.SUCCESS;

        } catch (Exception e) {
            // System.out.println(e.getMessage());
            if (connect != null) connect.rollback();
            return ResultFunction.ERROR;

        } finally {
            if (billDAO.connectDB != null) billDAO.connectDB.close();
        }
    }


    public static void main(String[] args) throws SQLException {
        // Sử dụng TimerUtil để thực hiện và đo thời gian của addBillRandom
        TimerUtil.timeExecution(() -> {
            try {
                System.out.println(addDataToTableBills(5000));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 1);
    }

}
