package database.dao;

import database.DbConnection;
import model.PublicKey;

import java.math.BigInteger;
import java.sql.Statement;

public class PublicKeyDAO {

    public DbConnection connectDB;
    public final String ADD_PUBLIC_KEY = "INSERT INTO public_keys(id_user, public_key, start_time, expired_time, is_valid) VALUES(?,?,?,?,?)";

    public BigInteger addPublicKey(PublicKey pk) throws Exception {

        if (connectDB == null) connectDB = DbConnection.getInstance();

        var preState = connectDB.getConn().prepareStatement(ADD_PUBLIC_KEY, Statement.RETURN_GENERATED_KEYS);

        preState.setInt(1, pk.getId_user());
        preState.setString(2, pk.getPublic_key());
        preState.setTimestamp(3, pk.getStart_time());
        preState.setTimestamp(4, pk.getExpired_time());
        preState.setInt(5, pk.getIs_valid());

        preState.executeUpdate();

        var resultSet = preState.getResultSet();

        // Kiểm tra xem ResultSet có dữ liệu không
        if (resultSet.next()) {
            // Trả về giá trị id_public_key được sinh ra
            return BigInteger.valueOf(resultSet.getInt(1));
        } else {
            throw new Exception("add public_key into database failed");
        }
    }

    public static void main(String[] args) {

    }

}
