package database.dao;

import database.DbConnection;
import model.Bill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public final String ADD_BILL = "";
    public final String ADD_BILL_DETAIL = "";
    public final String QUERY_GET_ALL_BILL = "";
    public DbConnection connectDB;
    public PreparedStatement preState;
    public ResultSet resultSet;
    public void addBill(Bill bill) throws Exception {

    }
    public List<Bill> getAllBill() throws Exception {
        if (connectDB == null) throw new Exception();
        var result = new ArrayList<Bill>();
        return result;
    }

}
