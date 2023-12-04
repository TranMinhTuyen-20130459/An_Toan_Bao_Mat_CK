package database.dao;

import database.DbConnection;
import model.Bill;
import model.BillDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public final String ADD_BILL = "INSERT INTO bills(id_user,id_status_bill,id_city,fullname_customer,phone_customer,email_customer,address_customer,bill_price,total_price) VALUES(?,?,?,?,?,?,?,?,?)";
    public final String ADD_BILL_DETAIL = "INSERT INTO bill_detail(id_bill,id_product,quantity,listed_price,current_price) VALUES(?,?,?,?,?)";
    public final String QUERY_GET_ALL_BILL = "";

    public DbConnection connectDB;
    public PreparedStatement preState;
    public ResultSet resultSet;

    public BillDAO() {
        connectDB = DbConnection.getInstance();
    }

    /**
     * Thêm một đối tượng Bill vào cơ sở dữ liệu và trả về giá trị id_bill được sinh ra.
     *
     * @param bill Đối tượng Bill chứa thông tin cần thêm vào cơ sở dữ liệu.
     * @return Giá trị id_bill được sinh ra sau khi thêm vào cơ sở dữ liệu, hoặc ném ra lỗi nếu không thành công.
     * @throws Exception Nếu không thể kết nối đến cơ sở dữ liệu.
     */
    public int addBill(Bill bill) throws Exception {
        // Kiểm tra xem kết nối đến cơ sở dữ liệu đã được thiết lập hay chưa
        if (connectDB == null) throw new Exception("Database connection not established.");

        // Tạo một PreparedStatement để thực hiện truy vấn SQL thêm mới
        preState = connectDB.getConn().prepareStatement(ADD_BILL, Statement.RETURN_GENERATED_KEYS);

        // Thiết lập các tham số của truy vấn SQL từ thông tin trong đối tượng Bill
        preState.setInt(1, bill.getId_user());
        preState.setInt(2, bill.getId_status_bill());
        preState.setInt(3, bill.getId_city());
        preState.setString(4, bill.getName_customer());
        preState.setString(5, bill.getPhone_customer());
        preState.setString(6, bill.getEmail_customer());
        preState.setString(7, bill.getAddress_customer());
        preState.setDouble(8, bill.getBill_price());
        preState.setDouble(9, bill.getTotal_price());

        // Thực thi truy vấn SQL thêm mới
        preState.executeUpdate();

        // Lấy ResultSet chứa giá trị id_bill được sinh ra (nếu có)
        resultSet = preState.getGeneratedKeys();

        // Kiểm tra xem ResultSet có dữ liệu không
        if (resultSet.next()) {
            // Trả về giá trị id_bill được sinh ra
            return resultSet.getInt(1);
        } else {
            throw new Exception("add bill into database failed");
        }
    }

    /**
     * Thêm thông tin chi tiết hóa đơn vào cơ sở dữ liệu.
     *
     * @param bill_detail Đối tượng {@code BillDetail} chứa thông tin chi tiết hóa đơn.
     * @throws Exception Nếu có lỗi xảy ra trong quá trình thực hiện thao tác với cơ sở dữ liệu.
     */
    public void addBillDetail(BillDetail bill_detail) throws Exception {

        // Kiểm tra xem kết nối đến cơ sở dữ liệu đã được thiết lập hay chưa
        if (connectDB == null) throw new Exception("Database connection not established.");

        // Chuẩn bị truy vấn SQL để thêm thông tin chi tiết hóa đơn
        preState = connectDB.getPreparedStatement(ADD_BILL_DETAIL);

        // Thiết lập các giá trị tham số cho truy vấn
        preState.setInt(1, bill_detail.getId_bill());
        preState.setInt(2, bill_detail.getId_product());
        preState.setInt(3, bill_detail.getQuantity());
        preState.setBigDecimal(4, bill_detail.getListed_price());
        preState.setBigDecimal(5, bill_detail.getCurrent_price());

        // Thực hiện truy vấn SQL để thêm dữ liệu vào cơ sở dữ liệu
        preState.executeUpdate();
    }


    public List<Bill> getAllBill() throws Exception {
        if (connectDB == null) throw new Exception();
        var result = new ArrayList<Bill>();
        return result;
    }

}
