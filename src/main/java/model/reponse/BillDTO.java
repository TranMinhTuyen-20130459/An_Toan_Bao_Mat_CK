package model.reponse;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class BillDTO {

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
    private String status_bill;
    private String security_status;

    /*
     * Trạng thái bảo mật của đơn hàng có 2 giá trị
     * 1."Hợp lệ"
     * 2."Đã bị chỉnh sửa"
     * */
    public static String getStatusBill(int id_status_bill) {
        switch (id_status_bill) {
            case 1:
                return "Chờ xử lý";
            case 2:
                return "Đang vận chuyển";
            case 3:
                return "Đã giao";
            case 4:
                return "Đã hủy";
            default:
                return "";
        }
    }
}
