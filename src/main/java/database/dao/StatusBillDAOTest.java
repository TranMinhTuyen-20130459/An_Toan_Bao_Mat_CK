package database.dao;

import database.DbConnection;
import java.util.ArrayList;
import java.util.List;

public class StatusBillDAOTest {
    public final String QUERY_ID_STATUS = "SELECT id_status_bill FROM status_bill";

    public DbConnection connectDB;

    public List<Integer> getAllIdStatus() throws Exception {

        if (connectDB == null) connectDB = DbConnection.getInstance();
        var result = new ArrayList<Integer>();

        var preState = connectDB.getPreparedStatement(QUERY_ID_STATUS);
        var resultSet = preState.executeQuery();
        while (resultSet.next()) {
            var id_status = resultSet.getInt("id_status_bill");
            result.add(id_status);
        }

        return result;
    }

}
