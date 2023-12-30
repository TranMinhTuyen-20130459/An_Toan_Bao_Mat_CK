package utils;

import properties.DomainProperties;

public class BodyMailRegister {
    public static String createBodyMailForRegister(String context_path, String id){
        String domain = DomainProperties.getDomain();
        return "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"> <!-- utf-8 works for most cases -->\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div style=\"display: flex; justify-content: center; background-color: #f1f1f1\">\n" +
                "    <div style=\"width: 500px; text-align: center\">\n" +
                "        <div style=\"padding-bottom: 20px\">\n" +
                "            <h1 style=\"color: blue;\"><a>LAB CHEMICALS</a></h1>\n" +
                "            <h2>Chào mừng bạn đến với dịch vụ của chúng tôi</h2>\n" +
                "            <p>Để tạo tài khoản và sử dụng các dịch vụ của chúng tôi hãy " +
                "                        <a href='"+ domain + context_path +"/shop/verify-register?key=" + id + "'>nhấn vào đây!</a></p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <h3 style=\"font-size: 24px;\">Chúng tôi sẽ cung cấp một private key cho bạn với mục đích ký đơn hàng xác thực chính chủ</h3>\n" +
                "            <h3 style=\"font-size: 24px; color: red;\">Đừng để lộ private key này cho bất kỳ ai! Họ có thể mạo danh bạn!</h3>\n" +
                "            <p>Nếu bạn bị lộ hãy thông báo cho chúng tôi trong trang cá nhân của bạn. Chúng tôi sẽ xử lý trường hợp của bạn nhanh chóng</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
    public static String createBodyMailForLeakKey(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"> <!-- utf-8 works for most cases -->\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div style=\"display: flex; justify-content: center; background-color: #f1f1f1\">\n" +
                "    <div style=\"width: 500px; text-align: center\">\n" +
                "        <div>\n" +
                "            <h3 style=\"font-size: 24px;\">Private key mới cho bạn</h3>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
    public static String createBodyMailForUpdateStatusBill(String context_path, String id, String status, int id_status, String toEmail){
        String domain = DomainProperties.getDomain();
        return "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"> <!-- utf-8 works for most cases -->\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div style=\"display: flex; justify-content: center; background-color: #f1f1f1\">\n" +
                "    <div style=\"width: 500px; text-align: center\">\n" +
                "        <div style=\"padding-bottom: 20px\">\n" +
                "            <h1 style=\"color: blue;\"><a>LAB CHEMICALS</a></h1>\n" +
                "            <h2>Chúng tôi cần bạn xác thực đơn hàng " + status +"</h2>\n" +
                "            <p>Để xác thực đơn hàng hãy " +
                "                        <a href='"+ domain + context_path +"/shop/verify-status-bill?key=" + id + "&idStatus="+ id_status + "&email="+ toEmail +"'>nhấn vào đây!</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
