package database.dao;

import database.DbConnection;
import model.Bill;
import model.BillDetail;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public final String ADD_BILL = "INSERT INTO bills(id_user,id_status_bill,id_city,fullname_customer,phone_customer,email_customer,address_customer,bill_price,total_price) VALUES(?,?,?,?,?,?,?,?,?)";
    public final String ADD_BILL_DETAIL = "INSERT INTO bill_detail(id_bill,id_product,quantity,listed_price,current_price) VALUES(?,?,?,?,?)";
    public final String QUERY_GET_ALL_BILL = "SELECT id_bill,id_user,id_status_bill,id_city,fullname_customer,phone_customer,email_customer,address_customer,bill_price,total_price,time_order,hash_bill_encrypted FROM bills";
    public final String QUERY_GET_BILL_DETAIL = "SELECT id_bill,id_product,quantity,listed_price,current_price FROM bill_detail WHERE id_bill=?";

    public DbConnection connectDB;

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
        var preState = connectDB.getConn().prepareStatement(ADD_BILL, Statement.RETURN_GENERATED_KEYS);

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
        var resultSet = preState.getGeneratedKeys();

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
        var preState = connectDB.getPreparedStatement(ADD_BILL_DETAIL);

        // Thiết lập các giá trị tham số cho truy vấn
        preState.setInt(1, bill_detail.getId_bill());
        preState.setInt(2, bill_detail.getId_product());
        preState.setInt(3, bill_detail.getQuantity());
        preState.setBigDecimal(4, bill_detail.getListed_price());
        preState.setBigDecimal(5, bill_detail.getCurrent_price());

        // Thực hiện truy vấn SQL để thêm dữ liệu vào cơ sở dữ liệu
        preState.executeUpdate();
    }

    /**
     * Trả về danh sách tất cả các hóa đơn từ cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng Bill chứa thông tin hóa đơn.
     * @throws Exception Nếu không thể thiết lập kết nối đến cơ sở dữ liệu.
     */
    public List<Bill> getAllBill() throws Exception {
        // Kiểm tra xem kết nối đến cơ sở dữ liệu đã được thiết lập chưa.
        if (connectDB == null) {
            throw new Exception("Database connection not established.");
        }

        // Danh sách kết quả chứa thông tin hóa đơn.
        var result = new ArrayList<Bill>();

        // Tạo prepared statement và thực hiện truy vấn để lấy dữ liệu hóa đơn từ cơ sở dữ liệu.
        var preState = connectDB.getPreparedStatement(QUERY_GET_ALL_BILL);
        var resultSet = preState.executeQuery();

        // Duyệt qua tất cả các dòng kết quả từ truy vấn.
        while (resultSet.next()) {

            // Lấy thông tin cơ bản của hóa đơn từ ResultSet.
            var id_bill = resultSet.getInt("id_bill");

            // Lấy thông tin chi tiết hóa đơn dựa trên ID hóa đơn.
            var list_bill_detail = getBillDetailsById(id_bill);

            // Xây dựng đối tượng Bill từ dữ liệu ResultSet và danh sách chi tiết hóa đơn.
            var bill = Bill.builder()
                    .id_bill(id_bill)
                    .id_user(resultSet.getInt("id_user"))
                    .id_status_bill(resultSet.getInt("id_status_bill"))
                    .id_city(resultSet.getInt("id_city"))
                    .name_customer(resultSet.getString("fullname_customer"))
                    .phone_customer(resultSet.getString("phone_customer"))
                    .email_customer(resultSet.getString("email_customer"))
                    .address_customer(resultSet.getString("address_customer"))
                    .bill_price(resultSet.getDouble("bill_price"))
                    .total_price(resultSet.getDouble("total_price"))
                    .time_order(resultSet.getTimestamp("time_order"))
                    .hash_bill_encrypted(resultSet.getString("hash_bill_encrypted"))
                    .bill_details(list_bill_detail)
                    .build();

            // Thêm đối tượng Bill vào danh sách kết quả.
            result.add(bill);
        }

        // Trả về danh sách các hóa đơn.
        return result;
    }

    /**
     * Trả về danh sách chi tiết hóa đơn dựa trên ID hóa đơn.
     *
     * @param id_bill ID của hóa đơn.
     * @return Danh sách các đối tượng BillDetail chứa thông tin chi tiết hóa đơn.
     * @throws Exception Nếu không thể thiết lập kết nối đến cơ sở dữ liệu.
     */
    public List<BillDetail> getBillDetailsById(int id_bill) throws Exception {
        // Kiểm tra xem kết nối đến cơ sở dữ liệu đã được thiết lập chưa.
        if (connectDB == null) {
            throw new Exception("Database connection not established.");
        }

        // Danh sách kết quả chứa thông tin chi tiết hóa đơn.
        var result = new ArrayList<BillDetail>();

        // Tạo prepared statement và thiết lập tham số ID hóa đơn.
        var preState = connectDB.getPreparedStatement(QUERY_GET_BILL_DETAIL);
        preState.setInt(1, id_bill);

        // Thực hiện truy vấn để lấy dữ liệu chi tiết hóa đơn từ cơ sở dữ liệu.
        var resultSet = preState.executeQuery();

        // Duyệt qua tất cả các dòng kết quả từ truy vấn.
        while (resultSet.next()) {
            // Xây dựng đối tượng BillDetail từ dữ liệu ResultSet.
            var bill_detail = BillDetail.builder()
                    .id_bill(id_bill)
                    .id_product(resultSet.getInt("id_product"))
                    .quantity(resultSet.getInt("quantity"))
                    .listed_price(resultSet.getBigDecimal("listed_price"))
                    .current_price(resultSet.getBigDecimal("current_price"))
                    .build();

            // Thêm đối tượng BillDetail vào danh sách kết quả.
            result.add(bill_detail);
        }

        // Trả về danh sách chi tiết hóa đơn.
        return result;
    }


    public static void main(String[] args) throws Exception {

        BillDAO dao = new BillDAO();
        dao.getAllBill().forEach(item -> {
            System.out.println(item.toString());
        });
    }

}
