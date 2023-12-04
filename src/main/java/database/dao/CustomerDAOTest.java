package database.dao;

import database.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOTest {
    public final String QUERY_ID_CUSTOMER = "SELECT id_user_customer FROM account_customer";

    public DbConnection connectDB;


    public List<Integer> getAllIdUser() throws Exception {
        if (connectDB == null) connectDB = DbConnection.getInstance();
        var result = new ArrayList<Integer>();

        var preState = connectDB.getPreparedStatement(QUERY_ID_CUSTOMER);
        var resultSet = preState.executeQuery();
        while (resultSet.next()) {
            var id_user = resultSet.getInt("id_user_customer");
            result.add(id_user);
        }

        return result;
    }
}
