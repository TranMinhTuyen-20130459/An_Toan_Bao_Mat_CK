package service;

import database.dao.PublicKeyDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class PublicKeyService {

    public static void addDataToTablePublicKeys() throws SQLException {
        var dao = new PublicKeyDAO();
        Connection connect = null;
        try {
            connect = dao.connectDB.getConn();
            connect.setAutoCommit(false);



            connect.commit();

        } catch (Exception e) {
            if (connect != null) connect.rollback();
        } finally {
            if (dao.connectDB != null) dao.connectDB.close();
        }
    }

}
