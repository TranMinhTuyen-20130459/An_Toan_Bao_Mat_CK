package model.reponse;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
@Data
@Builder
public class InfoBillResponse {

    /*
     * khởi tạo các thông tin về đơn hàng hiển thị lên View
     * */

    private int id_bill;
    private int id_user;
    private int id_status_bill;
    private int id_city;
    private String name_customer;
    private String phone_customer;
    private String email_customer;
    private String address_customer;
    private Double bill_price;
    private Double total_price;
    private Timestamp time_order;
    private String security_status;

    /*
     * Trạng thái bảo mật của đơn hàng có 2 giá trị
     * 1."Hợp lệ"
     * 2."Đã bị chỉnh sửa"
     * */
}
