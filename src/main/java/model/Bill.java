package model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class Bill implements Serializable {

    private int id_user;
    private int id_status_bill;
    private int id_city;
    private String name_customer;
    private String phone_customer;
    private String email_customer;
    private String address_customer;
    private Double bill_price;
    private Double total_price;
    private Timestamp time_order; //=> bắt buộc phải mã hóa cả thời gian đặt hàng
    private String hash_bill_encrypted;

    @Builder.Default
    private List<BillDetail> bill_details = null;

    @Builder.Default
    private byte isValid = 1; //=> đơn hàng có hợp lệ hay không (1: Hợp lệ, 2: Đã bị chỉnh sửa)

}
