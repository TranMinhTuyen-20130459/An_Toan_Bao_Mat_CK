package model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
/*
 * đây là chuỗi object toString() sẽ dùng để hash => sau đó mã hóa bằng private_key
 * */
@ToString(of = {"id_bill", "id_user", "id_status_bill", "id_city", "name_customer", "phone_customer", "email_customer", "address_customer", "bill_price", "total_price", "time_order", "hash_bill_encrypted", "bill_details"})

public class Bill implements Serializable {

    private int id_bill; //=> id_bill cũng cần được hash
    private int id_user;
    private int id_status_bill;
    private int id_city;
    private String name_customer;
    private String phone_customer;
    private String email_customer;
    private String address_customer;
    private Double bill_price;
    private Double total_price;
    private Timestamp time_order; //=> time_order cũng cần được hash
    private String hash_bill_encrypted;
    @Builder.Default
    private List<BillDetail> bill_details = null;
    public static void main(String[] args) {

        var bill = Bill.builder()
                .id_user(1)
                .id_status_bill(1)
                .id_city(1)
                .name_customer("TRAN TUYEN")
                .phone_customer("0927042108")
                .email_customer("tuyen@gmail.com")
                .address_customer("256")
                .bill_price((double) 100000)
                .total_price((double) 150000)
                .build();

        System.out.println(bill.toString());
    }

}
