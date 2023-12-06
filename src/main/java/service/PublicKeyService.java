package service;

import database.dao.CustomerDAOTest;
import database.dao.PublicKeyDAO;
import helper.ResultFunction;
import model.PublicKey;
import utils.AsymmetricEncrypt;
import utils.TimerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.stream.IntStream;

public class PublicKeyService {

    /**
     * Thêm dữ liệu vào bảng chứa các khóa công khai (public keys).
     *
     * @return Kết quả thực hiện thêm dữ liệu: SUCCESS nếu thành công, ERROR nếu có lỗi.
     * @throws SQLException Nếu có lỗi xảy ra trong quá trình thao tác với cơ sở dữ liệu.
     */
    public static int addDataToTablePublicKeys() throws SQLException {
        // Tạo đối tượng DAO để tương tác với cơ sở dữ liệu public keys.
        var dao = new PublicKeyDAO();
        Connection connect = null;

        try {
            // Kết nối đến cơ sở dữ liệu và bắt đầu giao dịch.
            connect = dao.connectDB.getConn();
            connect.setAutoCommit(false);

            // Tạo đối tượng Random để sinh giá trị ngẫu nhiên và đối tượng RSA để tạo khóa.
            var random = new Random();
            var rsa = new AsymmetricEncrypt(AsymmetricEncrypt.RSA);

            // Lấy danh sách ID người dùng từ cơ sở dữ liệu.
            var list_id_user = new CustomerDAOTest().getAllIdUser();

            // Duyệt qua danh sách ID người dùng và thêm dữ liệu vào bảng public keys.
            list_id_user.forEach(id_user -> {
                // Duyệt qua một số lượng ngẫu nhiên (từ 1 đến 5) để thêm nhiều public keys cho mỗi người dùng.
                IntStream.range(0, random.nextInt(5) + 1).forEachOrdered(item -> {
                    // Sinh bộ thời gian bắt đầu và hết hạn sử dụng khóa.
                    var arr_timestamp = TimerUtil.generateStartAndExpiredTimes();

                    // Tạo khóa công khai và xây dựng đối tượng PublicKey.
                    try {
                        rsa.generateKey(512);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    var public_key = PublicKey.builder()
                            .id_user(id_user)
                            .public_key(rsa.exportPublicKey())
                            .start_time(arr_timestamp[0])
                            .expired_time(arr_timestamp[1])
                            .is_valid((byte) (random.nextInt(2)))
                            .build();

                    // Thêm public key vào cơ sở dữ liệu.
                    try {
                        dao.addPublicKey(public_key);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            });

            // Commit giao dịch nếu mọi thứ diễn ra suôn sẻ.
            connect.commit();
            return ResultFunction.SUCCESS;

        } catch (Exception e) {
            // Rollback giao dịch nếu có lỗi xảy ra.
            if (connect != null) connect.rollback();
            return ResultFunction.ERROR;

        } finally {
            // Đóng kết nối đến cơ sở dữ liệu sau khi thực hiện xong.
            if (dao.connectDB != null) dao.connectDB.close();
        }
    }

    public static void main(String[] args) throws Exception {

        TimerUtil.timeExecution(() -> {
            try {
                System.out.println(addDataToTablePublicKeys());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 10);

    }

}
