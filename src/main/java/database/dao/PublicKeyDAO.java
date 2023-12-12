package database.dao;

import database.DbConnection;
import model.Bill;
import model.PublicKey;
import utils.TimerUtil;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;

public class PublicKeyDAO {

    public DbConnection connectDB;
    public final String ADD_PUBLIC_KEY = "INSERT INTO public_keys(id_user, public_key, start_time, expired_time, is_valid) VALUES(?,?,?,?,?)";
    public final String QUERY_PUBLIC_KEY = "SELECT public_key, start_time, expired_time, is_valid FROM public_keys WHERE id_user = ? AND ? BETWEEN start_time AND IFNULL(expired_time, NOW()) LIMIT 1";

    public PublicKeyDAO() {
        connectDB = DbConnection.getInstance();
    }

    /**
     * Adds a public key to the database and returns the generated id_public_key as a BigInteger.
     *
     * @param pk The PublicKey object containing the information to be added.
     * @return A BigInteger representing the generated id_public_key.
     * @throws Exception If an error occurs during the database operation.
     */
    public BigInteger addPublicKey(PublicKey pk) throws Exception {
        // Kiểm tra và khởi tạo kết nối đến cơ sở dữ liệu nếu cần
        if (connectDB == null) connectDB = DbConnection.getInstance();

        // Chuẩn bị truy vấn SQL với khả năng trả về khóa chính được sinh ra
        var preState = connectDB.getConn().prepareStatement(ADD_PUBLIC_KEY, Statement.RETURN_GENERATED_KEYS);

        // Đặt các tham số của truy vấn SQL từ đối tượng PublicKey
        preState.setInt(1, pk.getId_user());
        preState.setString(2, pk.getPublic_key());
        preState.setTimestamp(3, pk.getStart_time());
        preState.setTimestamp(4, pk.getExpired_time());
        preState.setInt(5, pk.getIs_valid());

        // Thực hiện truy vấn cập nhật dữ liệu
        preState.executeUpdate();

        // Lấy ResultSet chứa các khóa chính được sinh ra
        var resultSet = preState.getGeneratedKeys();

        // Kiểm tra xem ResultSet có dữ liệu không
        if (resultSet.next()) {
            // Trả về giá trị id_public_key được sinh ra dưới dạng BigInteger
            return BigInteger.valueOf(resultSet.getInt(1));
        } else {
            // Nếu không có dữ liệu trong ResultSet, thông báo lỗi
            throw new Exception("Add public_key into database failed. No generated keys available.");
        }
    }

    public PublicKey getPublicKeyByInfoBill(Bill bill) throws SQLException {

        // Kiểm tra và khởi tạo kết nối đến cơ sở dữ liệu nếu cần
        if (connectDB == null) connectDB = DbConnection.getInstance();

        var preState = connectDB.getPreparedStatement(QUERY_PUBLIC_KEY);

        preState.setInt(1, bill.getId_user());
        preState.setTimestamp(2, bill.getTime_order());

        preState.executeQuery();

        var resultSet = preState.getResultSet();

        if (resultSet.next()) {
            var pk = PublicKey.builder()
                    .id_user(bill.getId_user())
                    .public_key(resultSet.getString("public_key"))
                    .start_time(resultSet.getTimestamp("start_time"))
                    .expired_time(resultSet.getTimestamp("expired_time"))
                    .is_valid(resultSet.getByte("is_valid"))
                    .build();

            return pk;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        var arr_timestamp = TimerUtil.generateStartAndExpiredTimes();
        var public_key = PublicKey.builder()
                .id_user(1)
                .public_key("qwertyuiopasdfghjklzxcvbnm")
                .start_time(arr_timestamp[0])
                .expired_time(arr_timestamp[1])
                .is_valid((byte) 1).build();

        var dao = new PublicKeyDAO();

        System.out.println(dao.addPublicKey(public_key));
    }

}
