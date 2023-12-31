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
                        var security_status = "Đã bị chỉnh sửa";

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
        System.out.println(checkVerifyPrivateKey(7, "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkvtY1Onx/t6Lr1mlidgcDYSeYf2MYhHEtvyFBSq0KfOUth2MUykQxbmm9zMdc5QXnFjZvAfwklbTUuBIXM2IDQIDAQABAkAGtKzy2dGOnTymPWLODSwKedh4BAdZmhHtf0vaVn5tw3sRoNte/3E4xwDRCg4ikmMI/Xu03J5GdQ8TtJS2A5KBAiEAnDf7XWG2o5uKbkBWtnhqVSD/qidLjjuSPbjnn3th79ECIQDw3QFkNd9QjKBxu5S5dsB7eLxFz/TLjtHPPjejsUc/fQIgArP9R3pfXKleVdYLKhEfVeXCRsFzTzy8pRDEAUBzS2ECIQC651RYFbHHSIiMGLpMvIiah1LZfYiAmL/YaHg2bq9R5QIgXFF+xRMklK618d/+xrDUEG23Q9u7X1R9mIpMBkSOCnA="));
    }
}
