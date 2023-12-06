package service;

import database.dao.BillDAO;
import database.dao.PublicKeyDAO;
import model.reponse.InfoBillResponse;
import utils.AsymmetricEncrypt;
import utils.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BillService {

    public static List<InfoBillResponse> getAllInfoBill() {

        var billDAO = new BillDAO();
        var publicKeyDAO = new PublicKeyDAO();
        var rsa = new AsymmetricEncrypt(AsymmetricEncrypt.RSA);

        try {
            var list_bill = billDAO.getAllBill();

            // Sử dụng stream API trong Java 8 để map các thuộc tính trong Bill sang InfoBillResponse
            return list_bill.stream()
                    .map(bill -> {

                        var security_status = "Hợp lệ";
                        var hash_bill_encrypted = bill.getHash_bill_encrypted();

                        try {

                            // cần set về null bởi vì trước khi Đặt Hàng thì hash_bill_encrypted bằng null
                            bill.setHash_bill_encrypted(null);
                            var hash_bill = HashUtil.hashText(bill.toString(), HashUtil.SHA_1);

                            // Lấy ra publicKey tương ứng để giải mã hash_bill_encrypted
                            var pk = publicKeyDAO.getPublicKeyByInfoBill(bill);

                            rsa.importPublicKey(pk.getPublic_key());

                            var hash_bill_decrypted = rsa.decryptFromBase64(hash_bill_encrypted, AsymmetricEncrypt.transformation_RSA);

                            if (!hash_bill.equals(hash_bill_decrypted)) security_status = "Đã bị chỉnh sửa";

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        return InfoBillResponse.builder()
                                .id_bill(bill.getId_bill())
                                .id_user(bill.getId_user())
                                .id_status_bill(bill.getId_status_bill())
                                .id_city(bill.getId_city())
                                .name_customer(bill.getName_customer())
                                .phone_customer(bill.getPhone_customer())
                                .email_customer(bill.getEmail_customer())
                                .address_customer(bill.getAddress_customer())
                                .bill_price(bill.getBill_price())
                                .total_price(bill.getTotal_price())
                                .time_order(bill.getTime_order())
                                .security_status(security_status)
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (billDAO.connectDB != null) billDAO.connectDB.close();
        }
    }

    public static void main(String[] args) {

        getAllInfoBill().forEach(item -> {
            System.out.println(item.toString());
        });

    }
}
