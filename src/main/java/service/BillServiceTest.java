package service;

import database.dao.*;
import model.Bill;
import utils.BillUtil;
import utils.TimerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.stream.IntStream;

public class BillServiceTest {
    public static final int SUCCESS = 1;
    public static final int ERROR = -1;

    /**
     * Thêm một số hóa đơn ngẫu nhiên vào cơ sở dữ liệu.
     *
     * @param quantity Số lượng hóa đơn cần thêm.
     * @return Kết quả thực hiện: SUCCESS nếu thành công, ERROR nếu có lỗi.
     * @throws SQLException Nếu có lỗi kết nối hoặc thao tác trên cơ sở dữ liệu.
     */
    public static int addBillRandom(int quantity) throws SQLException {

        BillDAO dao = new BillDAO();
        Connection connect = null;

        try {
            connect = dao.connectDB.getConn();
            connect.setAutoCommit(false); // Bắt đầu giao tác

            // Sử dụng Java 8 Stream API để lấy danh sách các ID
            var list_id_users = new CustomerDAOTest().getAllIdUser();
            var list_id_status_bill = new StatusBillDAOTest().getAllIdStatus();
            var list_id_city = new CityDAOTest().getAllIdCity();
            var list_id_product = new ProductDAOTest().getAllIdProduct();

            Random random = new Random();

            // Sử dụng IntStream để tạo số lượng hóa đơn
            IntStream.range(0, quantity).forEachOrdered(i -> {
                try {
                    Bill bill = BillUtil.generateBill(list_id_users, list_id_status_bill, list_id_city, BillUtil.arr_customers, BillUtil.arr_address);
                    int id_bill = dao.addBill(bill);

                    // Sử dụng Java 8 Stream API để thêm chi tiết hóa đơn
                    BillUtil.generateRandomBillDetail(id_bill, list_id_product, random.nextInt(10) + 1)
                            .forEach(item -> {
                                try {
                                    dao.addBillDetail(item);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            connect.commit(); // Kết thúc giao tác
            return SUCCESS;

        } catch (Exception e) {
            // System.out.println(e.getMessage());
            if (connect != null) connect.rollback();
            return ERROR;

        } finally {
            if (dao.connectDB != null) dao.connectDB.close();
        }
    }


    public static void main(String[] args) throws SQLException {
        // Sử dụng TimerUtil để thực hiện và đo thời gian của addBillRandom
        TimerUtil.timeExecution(() -> {
            try {
                System.out.println(addBillRandom(8000));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 1);
    }

}
