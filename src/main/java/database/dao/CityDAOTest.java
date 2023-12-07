package database.dao;

import database.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CityDAOTest {
    public final String QUERY_ID_CITY = "SELECT id_city FROM city";

    public DbConnection connectDB;

    public List<Integer> getAllIdCity() throws Exception {
        if (connectDB == null) connectDB = DbConnection.getInstance();
        var result = new ArrayList<Integer>();

        var preState = connectDB.getPreparedStatement(QUERY_ID_CITY);
        var resultSet = preState.executeQuery();
        while (resultSet.next()) {
            var id_city = resultSet.getInt("id_city");
            result.add(id_city);
        }

        return result;
    }
}
