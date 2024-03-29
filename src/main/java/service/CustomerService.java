package service;

import database.DbConnection;
import database.dao.BillDAO;
import database.dao.PublicKeyDAO;
import model.Bill;
import model.CartItem;
import model.Customer;
import model.Order;
import utils.HashUtil;
import utils.RSACipher;
import utils.SortedUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CustomerService {

    public static Customer checkLogin(String email, String password) {
        List<Customer> customers = new ArrayList<>();
        DbConnection connectDB = DbConnection.getInstance();
        String sql = "SELECT id_user_customer, username, pass, id_status_acc, id_city, fullname, phone_customer, address, sex, email_customer " +
                "from account_customer where username = ?";
        PreparedStatement preState = connectDB.getPreparedStatement(sql);
        try {
            preState.setString(1, email);
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                int id_customer = rs.getInt("id_user_customer");
                String email_customer = rs.getString("username");
                String password_customer = rs.getString("pass");
                int id_status_acc_customer = rs.getInt("id_status_acc");
                int id_city_customer = rs.getInt("id_city");
                String fullname_customer = rs.getString("fullname");
                if (fullname_customer == null) {
                    fullname_customer = email_customer;
                }
                String phone = rs.getString("phone_customer");
                String address = rs.getString("address");
                String sex = rs.getString("sex");
                String email_cus = rs.getString("email_customer");
                Customer customer = new Customer(id_customer, email_customer, password_customer, id_status_acc_customer,
                        id_city_customer, fullname_customer, phone, address);
                customer.setSex(sex);
                customer.setEmail_customer(email_cus);
                customers.add(customer);
            }
            if (customers.size() != 1) {
                return null;
            } else {
                try {
                    var hash_pass_input = HashUtil.hashText(password, HashUtil.SHA_256);
                    Customer unique = customers.get(0);
                    if (unique.getPassword().equals(hash_pass_input)) {
                        return unique;
                    }
                } catch (Exception e) {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectDB.close();
        }
        return null;
    }

    public static boolean changePass(String newPass, String email) {
        DbConnection connectDb = DbConnection.getInstance();
        String sql = "UPDATE account_customer " +
                "SET pass = ?, time_change_pass = current_timestamp()" +
                " WHERE username = ?";
        PreparedStatement preState = connectDb.getPreparedStatement(sql);
        try {
            preState.setString(1, newPass);
            preState.setString(2, email);
            int update = preState.executeUpdate();
            if (update > 0) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connectDb.close();
        }
        return false;
    }

    public static int getIdOfCity(String city_name) {
        DbConnection connnectDb = DbConnection.getInstance();
        String sql = "select id_city from city where name_city = ?";
        PreparedStatement pre = connnectDb.getPreparedStatement(sql);
        try {
            pre.setString(1, city_name);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_city");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean profile(String email, String fullname, int city, String sex,
                                  String email_customer, String phone, String address) {
        DbConnection connnectDb = DbConnection.getInstance();
        String sql = "update account_customer " +
                "set fullname = ?, id_city = ?, sex = ?, email_customer = ?, phone_customer = ?, address = ? " +
                "where username = ?";
        PreparedStatement pre = connnectDb.getPreparedStatement(sql);
        try {
            pre.setString(1, fullname);
            pre.setInt(2, city);
            pre.setString(3, sex);
            pre.setString(4, email_customer);
            pre.setString(5, phone);
            pre.setString(6, address);
            pre.setString(7, email);
            int rs = pre.executeUpdate();
            return rs > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkExist(String email) {
        DbConnection connectDb = DbConnection.getInstance();
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id_user_customer, username, pass, id_status_acc, id_city, fullname, phone_customer, address " +
                "from account_customer where username = ?";
        PreparedStatement preState = connectDb.getPreparedStatement(sql);
        try {
            preState.setString(1, email);
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                int id_customer = rs.getInt("id_user_customer");
                String email_customer = rs.getString("username");
                String password_customer = rs.getString("pass");
                int id_status_acc_customer = rs.getInt("id_status_acc");
                int id_city_customer = rs.getInt("id_city");
                String fullname_customer = rs.getString("fullname");
                String phone = rs.getString("phone_customer");
                String address = rs.getString("address");
                Customer customer = new Customer(id_customer, email_customer, password_customer,
                        id_status_acc_customer, id_city_customer, fullname_customer, phone, address);
                customers.add(customer);
            }
            return customers.size() != 0;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            connectDb.close();
        }
    }

    public static void signUp(String email, String password) {
        DbConnection connectDb = DbConnection.getInstance();
        String sql = "INSERT INTO account_customer(username, pass, id_status_acc, id_city) " +
                "VALUES(?, ?, 1, 1)";
        PreparedStatement preState = connectDb.getPreparedStatement(sql);
        try {
            var hash_password = HashUtil.hashText(password, HashUtil.SHA_256);
            preState.setString(1, email);
            preState.setString(2, hash_password);
            preState.executeUpdate();
            System.out.println("success");
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            connectDb.close();
        }
    }

    public static int getIdCustomer(String email){
        DbConnection connnectDb = DbConnection.getInstance();
        String sql = "select id_user_customer from account_customer where username = ?";
        PreparedStatement pre = connnectDb.getPreparedStatement(sql);
        try {
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_user_customer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void savePuKey(int id_customer, String pu_key){
        DbConnection connectDb = DbConnection.getInstance();
        String sql = "INSERT INTO public_keys(id_user, public_key, start_time, is_valid) " +
                "VALUES(?, ?, ?, 1)";
        PreparedStatement preState = connectDb.getPreparedStatement(sql);
        try {
            Timestamp start_time = new Timestamp(System.currentTimeMillis());
            preState.setInt(1, id_customer);
            preState.setString(2, pu_key);
            preState.setTimestamp(3, start_time);
            preState.executeUpdate();
            System.out.println("success");
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            connectDb.close();
        }
    }

    public static void updateExpiredKey(int id_customer){
        DbConnection connectDb = DbConnection.getInstance();
        String sql = "UPDATE public_keys SET expired_time = ?, is_valid = 0 WHERE id_user = ? " +
                "ORDER BY id_public_key DESC LIMIT 1";
        PreparedStatement preState = connectDb.getPreparedStatement(sql);
        try {
            Timestamp expired_time = new Timestamp(System.currentTimeMillis());
            preState.setTimestamp(1, expired_time);
            preState.setInt(2, id_customer);
            preState.executeUpdate();
            int rowsAffected = preState.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            connectDb.close();
        }
    }

    // return customers created within the last ? days
    public static List<Customer> getRecentCustomers(int day) {
        List<Customer> customers = new ArrayList<>();
        try (var ps = DbConnection.getInstance().getPreparedStatement("SELECT id_user_customer, fullname, " +
                "sex, phone_customer, address, time_created, s.name_status_acc " +
                "FROM account_customer a JOIN status_acc s ON a.id_status_acc = s.id_status_acc " +
                "WHERE DATE(time_created) > (NOW() - INTERVAL ? DAY)")) {
            ps.setInt(1, day);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id_user_customer"));
                c.setSex(rs.getString("sex"));
                c.setPhone(rs.getString("phone_customer"));
                c.setAddress(rs.getString("address"));
                c.setFullname(rs.getString("fullname"));
                c.setTimeCreated(rs.getDate("time_created"));
                c.setStatus(rs.getString("name_status_acc"));
                customers.add(c);
            }
            return customers;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public static double getTransportFee(int cityId) {
        try (var ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT transport FROM city WHERE id_city = ?")) {
            ps.setInt(1, cityId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("transport");
        } catch (SQLException e) {
            return -1;
        }
    }

    public static Map<Integer, String> getCities() {
        Map<Integer, String> map = new HashMap<>();
        try (var ps = DbConnection.getInstance().getPreparedStatement("SELECT id_city, name_city FROM city")) {
            var rs = ps.executeQuery();
            while (rs.next()) map.put(rs.getInt("id_city"), rs.getString("name_city"));
            return map;
        } catch (SQLException e) {
            return new HashMap<>();
        }
    }

    public static int getQuantityByBillId(int billId) {
        try (var ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT SUM(quantity) FROM bills b JOIN bill_detail bd ON b.id_bill = bd.id_bill " +
                        "WHERE b.id_bill = ? GROUP BY b.id_bill"
        )) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        return bills;
    }

    public static List<Order> getOrderByUser(int userId) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT DISTINCT b.id_bill, s.name_status_bill, time_order, total_price " +
                        "FROM bills b JOIN bill_detail bd ON b.id_bill = bd.id_bill " +
                        "JOIN status_bill s ON s.id_status_bill = b.id_status_bill " +
                        "WHERE id_user = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int billId = rs.getInt("id_bill");
                List<CartItem> items = getCartItemsByBillId(billId);
                Order order = new Order(billId, items, rs.getTimestamp("time_order"),
                        rs.getDouble("total_price"),
                        rs.getString("name_status_bill"), isOrderVerified(billId));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    private static boolean isOrderVerified(int orderId) {
        try {
            final var billDao = new BillDAO();
            var bill = billDao.getAllBill().stream().filter(b -> b.getId_bill() == orderId).collect(Collectors.toList()).get(0);
            SortedUtil.sortByProductId(bill.getBill_details());
            String hashedBill = HashUtil.hashText(bill.toString(), HashUtil.SHA_1);

            final var publicKeyDao = new PublicKeyDAO();
            final var publicKey = publicKeyDao.getPublicKeyByInfoBill(bill);
            if (publicKey.getExpired_time() == null) {
                publicKey.setExpired_time(new Timestamp(System.currentTimeMillis()));
            }

            if (!(bill.getTime_order().after(publicKey.getStart_time()) && bill.getTime_order().before(publicKey.getExpired_time()))) {
                return false;
            }

            String decryptedHash = new RSACipher().decrypt(bill.getHash_bill_encrypted(), publicKey.getPublic_key());
            return decryptedHash.equals(hashedBill);
        } catch (Exception e) {
            return false;
        }
    }

    public static List<CartItem> getCartItemsByBillId(int billId) {
        List<CartItem> result = new ArrayList<>();
        try (var ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT id_product, quantity FROM bill_detail WHERE id_bill = ?")) {
            ps.setInt(1, billId);
            var rs = ps.executeQuery();
            while (rs.next()) {
                var cartItem = new CartItem(ProductService.getProductById(rs.getInt("id_product")),
                        rs.getInt("quantity"));
                result.add(cartItem);
            }
            return result;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public static String getEmailFromIdBill(int idBill){
        DbConnection connnectDb = DbConnection.getInstance();
        String sql = "SELECT DISTINCT account_customer.username " +
                "FROM bills " +
                "INNER JOIN account_customer ON bills.id_user = account_customer.id_user_customer " +
                "WHERE bills.id_bill = ?;";
        PreparedStatement pre = connnectDb.getPreparedStatement(sql);
        try {
            pre.setInt(1, idBill);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertUUID(String uuid, String email){
        DbConnection connectDb = DbConnection.getInstance();
        String sql = "UPDATE account_customer SET uuid = ? WHERE username = ?";
        PreparedStatement preState = connectDb.getPreparedStatement(sql);
        try {
            preState.setString(1, uuid);
            preState.setString(2, email);
            preState.executeUpdate();
            int rowsAffected = preState.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            connectDb.close();
        }
    }
    public static String getUUID(String email){
        DbConnection connnectDb = DbConnection.getInstance();
        String sql = "select uuid from account_customer where username = ?";
        PreparedStatement pre = connnectDb.getPreparedStatement(sql);
        try {
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getString("uuid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(getEmailFromIdBill(5));
//        insertUUID("hello", "20130390@st.hcmuaf.edu.vn");
    }
}
