package database.dao;

import database.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOTest {
    public final String QUERY_ID_PRODUCT = "SELECT id_product FROM products";

    public DbConnection connectDB;
    public PreparedStatement preState;
    public ResultSet resultSet;

    public List<Integer> getAllIdProduct() throws Exception {

        if (connectDB == null) connectDB = DbConnection.getInstance();

        var result = new ArrayList<Integer>();

        preState = connectDB.getPreparedStatement(QUERY_ID_PRODUCT);
        resultSet = preState.executeQuery();

        while (resultSet.next()) {
            var id_product = resultSet.getInt("id_product");
            result.add(id_product);
        }

        return result;
    }

}
