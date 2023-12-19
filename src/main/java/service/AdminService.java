package service;

import database.DbConnection;
import model.Account;
import model.Admin;
import model.Bill;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminService {

    public static int getTotalCancelOrders() {
        try (PreparedStatement ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT COUNT(*) FROM bills WHERE id_status_bill = ?")) {
            ps.setInt(1, 3);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static double getTotalRevenue() {
        try (PreparedStatement ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT SUM(total_price) FROM bills")) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static List<Bill> getBillsOrderedIn(int month) {
        List<Bill> bills = new ArrayList<>();
        return bills;
    }

    public static int getCustomersCreatedIn(int month) {
        try (var ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT DISTINCT COUNT(*) FROM account_customer " +
                        "WHERE MONTH(time_created) = ? AND YEAR(time_created) = YEAR(CURRENT_DATE)")) {
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static double getRevenueFor(int month) {
        try (var ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT SUM(total_price) FROM bills " +
                        "WHERE MONTH(time_order) = ? AND YEAR(time_order) = YEAR(CURRENT_DATE) " +
                        "GROUP BY MONTH(time_order)")) {
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static List<Bill> getRecentOrderedBills(int day) {
        List<Bill> bills = new ArrayList<>();
        return bills;
    }

    public static int getBillCounts() {
        return getNumberOf("bills");
    }

    public static int getCustomerCounts() {
        return getNumberOf("account_customer");
    }

    public static int getProductCounts() {
        return getNumberOf("products");
    }

    private static int getNumberOf(String table) {
        try (Statement st = DbConnection.getInstance().getStatement()) {
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (var ps = DbConnection.getInstance().getPreparedStatement(
                "SELECT id_user_customer, username, pass, name_status_acc, email_customer, time_created\n" +
                        "FROM account_customer a JOIN status_acc s ON a.id_status_acc = s.id_status_acc"
        )) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_user_customer");
                String username = rs.getString("username");
                String password = rs.getString("pass");
                String status = rs.getString("name_status_acc");
                String email = rs.getString("email_customer");
                Date time = rs.getDate("time_created");
                accounts.add(new Account(id, username, password, status, email, time));
            }
            return accounts;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
