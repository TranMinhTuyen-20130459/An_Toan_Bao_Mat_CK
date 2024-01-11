package service;

import database.dao.BillDAO;
import database.dao.PublicKeyDAO;
import model.Bill;
import model.reponse.BillDTO;
import utils.AsymmetricEncrypt;
import utils.HashUtil;
import utils.RSACipher;
import utils.SortedUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BillService {

    /**
     * Lấy ra danh sách thông tin về đơn hàng
     * trong đó bao gồm trạng thái bảo mật của đơn hàng ('Hợp lệ' or 'Đã bị chỉnh sửa')
     */
    public static List<BillDTO> getAllInfoBill() {

        var billDAO = new BillDAO();
        var publicKeyDAO = new PublicKeyDAO();
        var rsa = new AsymmetricEncrypt(AsymmetricEncrypt.RSA);

        try {
            var list_bill = billDAO.getAllBill();

            // Sử dụng stream API trong Java 8 để map các thuộc tính trong Bill sang InfoBillResponse
            return list_bill.stream()
                    .map(bill -> {
                        //=> Trạng thái bảo mật của đơn hàng
                        var security_status = "Không hợp lệ";

                        // => Thông tin Bill đã được Hash và mã hóa bằng private_key
                        var hash_bill_encrypted = bill.getHash_bill_encrypted();

                        // => Sắp xếp ds chi tiết đơn hàng theo id_product
                        SortedUtil.sortByProductId(bill.getBill_details());

                        try {
                            var hash_bill = HashUtil.hashText(bill.toString(), HashUtil.SHA_1);

                            // Lấy ra public_key tương ứng để giải mã hash_bill_encrypted
                            var pk = publicKeyDAO.getPublicKeyByInfoBill(bill);

                            if (pk != null) {

                                rsa.importPublicKey(pk.getPublic_key());

                                var hash_bill_decrypted = "";

                                try {
                                    hash_bill_decrypted = rsa.decryptFromBase64(hash_bill_encrypted, AsymmetricEncrypt.transformation_RSA);
                                } catch (Exception e) {
                                    hash_bill_decrypted = "";
                                }

                                // Cập nhật lại trạng thái bảo mật của đơn hàng
                                if (hash_bill.equals(hash_bill_decrypted)) security_status = "Hợp lệ";
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        return BillDTO.builder()
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
                                .status_bill(BillDTO.getStatusBill(bill.getId_status_bill()))
                                .security_status(security_status)
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (billDAO.connectDB != null) billDAO.connectDB.close();
        }
    }

    public static boolean checkVerifyPrivateKey(int idBill, String privateKey){
        var billDAO = new BillDAO();
        String getHashedBill;
        try {
            Bill billRaw = billDAO.getABill(idBill);
            getHashedBill = billRaw.getHash_bill_encrypted();
            String hashedBill = HashUtil.hashText(billRaw.toString(), HashUtil.SHA_1);
            var rsa = new RSACipher();
            // Encrypt the hash.
            var hashedBillEncrypted = rsa.encrypt(hashedBill, privateKey);
            if(getHashedBill.equals(hashedBillEncrypted)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Key không phù hợp");
            return false;
        }
    }

    public static void updateStatusABill(int idBill, int idStatus, String privateKey){
        var billDAO = new BillDAO();
        String getHashedBill;
        try {
            Bill billRaw = billDAO.getABill(idBill);
            billRaw.setId_status_bill(idStatus);

            String hashedBill = HashUtil.hashText(billRaw.toString(), HashUtil.SHA_1);
            var rsa = new RSACipher();
            var hashedBillEncrypted = rsa.encrypt(hashedBill, privateKey);

            billDAO.updateStatusBill(billRaw.getId_bill(), idStatus);
            billDAO.updateEncryptedHash(billRaw.getId_bill(), hashedBillEncrypted);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {

//        BillService.getAllInfoBill().forEach(item -> {
//            System.out.println(item.toString());
//        });
        System.out.println(checkVerifyPrivateKey(63, "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAiurFY/sMQG/0TtI2lMuXRgmwVXdvcHZ2D8sIZsbplsHpbeYpcvL0UfhvgL2RKGMVPfmrsYIAB6eJi+ag9UVlqQIDAQABAkARrzd3teLmZRot2pb2NEDKursG9lBqebon4onsQ5BDD7XS68g83S8d1+ic8n1UGmFRkUc1c09B21dXG4BzblSNAiEA3nWxuDGA08SOruQba6ux2xFj0oHEqCOm7visCHimFhcCIQCf3JH+iZpDUSXtBEBgoODOShXe3xXI5W0ZCt9K23l6PwIgY+Tr4i9wjyejfst1uLrHyfycG6okGfCwhHzsG3257w8CIDm8umYKYNLrXh1Kqc28jTU0gM2sGPwkENE4ogkxV6CVAiBRJ0CXM7yE0Tf1ZAluLO3j2v5X68OyKS+q6nM9DJMlJg=="));
    }
}
