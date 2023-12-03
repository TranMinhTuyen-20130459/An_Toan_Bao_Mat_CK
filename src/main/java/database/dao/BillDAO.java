package database.dao;

import database.DbConnection;
import model.Bill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public final String ADD_BILL = "INSERT INTO bills(id_user,id_status_bill,id_city,fullname_customer,phone_customer,email_customer,address_customer,bill_price,total_price) VALUES(?,?,?,?,?,?,?,?,?)";
    public final String ADD_BILL_DETAIL = "";
    public final String QUERY_GET_ALL_BILL = "";

    public DbConnection connectDB;
    public PreparedStatement preState;
    public ResultSet resultSet;

    public BillDAO() {
        connectDB = DbConnection.getInstance();
    }

    public void addBill(Bill bill) throws Exception {
        if (connectDB == null) throw new Exception();

        preState = connectDB.getPreparedStatement(ADD_BILL);

        preState.setInt(1, bill.getId_user());
        preState.setInt(2, bill.getId_status_bill());
        preState.setInt(3, bill.getId_city());
        preState.setString(4, bill.getName_customer());
        preState.setString(5, bill.getPhone_customer());
        preState.setString(6, bill.getEmail_customer());
        preState.setString(7, bill.getAddress_customer());
        preState.setDouble(8, bill.getBill_price());
        preState.setDouble(9, bill.getTotal_price());

        preState.executeUpdate();
    }

    public List<Bill> getAllBill() throws Exception {
        if (connectDB == null) throw new Exception();
        var result = new ArrayList<Bill>();
        return result;
    }

}
