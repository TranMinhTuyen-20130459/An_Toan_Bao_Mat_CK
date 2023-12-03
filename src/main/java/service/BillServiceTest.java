package service;

import database.dao.BillDAO;
import database.dao.CityDAOTest;
import database.dao.CustomerDAOTest;
import database.dao.StatusBillDAOTest;
import model.Bill;
import utils.BillUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class BillServiceTest {
    public static final int SUCCESS = 1;
    public static final int ERROR = -1;

    public static int addBillRandom(int quantity) throws SQLException {

        var dao = new BillDAO();
        Connection connect = null;

        try {

            connect = dao.connectDB.getConn();
            connect.setAutoCommit(false); // Bắt đầu giao tác

            var list_id_users = new CustomerDAOTest().getAllIdUser();
            var list_id_status_bill = new StatusBillDAOTest().getAllIdStatus();
            var list_id_city = new CityDAOTest().getAllIdCity();

            for (int i = 0; i < quantity; i++) {

                double[] prices = BillUtil.generateRandomPrices();

                Bill bill = Bill.builder()
                        .id_user(BillUtil.getRandomElementInList(list_id_users))
                        .id_status_bill(BillUtil.getRandomElementInList(list_id_status_bill))
                        .id_city(BillUtil.getRandomElementInList(list_id_city))
                        .name_customer(BillUtil.getRandomElementInArray(BillUtil.arr_customers))
                        .phone_customer(BillUtil.generateRandomPhoneNumber())
                        .email_customer(BillUtil.generateRandomEmail())
                        .address_customer(BillUtil.getRandomElementInArray(BillUtil.arr_address))
                        .bill_price(prices[0])
                        .total_price(prices[1])
                        .time_order(Timestamp.from(Instant.now())).build();

                dao.addBill(bill);
            }

            connect.commit(); // Kết thúc giao tác
            return SUCCESS;

        } catch (Exception e) {

            if (connect != null) connect.rollback();
            return ERROR;

        } finally {
            if (dao.connectDB != null) dao.connectDB.close();
        }

    }

    public static void main(String[] args) throws SQLException {

        System.out.println(addBillRandom(1000));

    }
}
