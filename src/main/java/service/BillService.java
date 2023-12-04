package service;

import database.dao.BillDAO;
import model.reponse.InfoBillResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BillService {

    public static List<InfoBillResponse> getAllInfoBill() {
        BillDAO dao = new BillDAO();
        try {
            var list_bill = dao.getAllBill();

            // Sử dụng stream API trong Java 8 để map các thuộc tính trong Bill sang InfoBillResponse
            return list_bill.stream()
                    .map(item -> {

                        var security_status = "Hợp lệ";

                        return InfoBillResponse.builder()
                                .id_bill(item.getId_bill())
                                .id_user(item.getId_user())
                                .id_status_bill(item.getId_status_bill())
                                .id_city(item.getId_city())
                                .name_customer(item.getName_customer())
                                .phone_customer(item.getPhone_customer())
                                .email_customer(item.getEmail_customer())
                                .address_customer(item.getAddress_customer())
                                .bill_price(item.getBill_price())
                                .total_price(item.getTotal_price())
                                .time_order(item.getTime_order())
                                .security_status(security_status)
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (dao.connectDB != null) dao.connectDB.close();
        }
    }

    public static void main(String[] args) {

        getAllInfoBill().forEach(item -> {
            System.out.println(item.toString());
        });

    }
}
