package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
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
    private Timestamp time_order;
    private String hash_bill_encrypted;
}
