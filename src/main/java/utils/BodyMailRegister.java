package utils;

public class BodyMailRegister {
    public static String createBodyMailForRegister(String context_path, String id, String private_key){
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
                "                        <a href='http://localhost:8080" + context_path +"/shop/verify-register?key=" + id + "'>nhấn vào đây!</a></p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <h3 style=\"font-size: 24px;\">Chúng tôi sẽ cung cấp một private key cho bạn</h3>\n" +
                "            <p style=\"text-align: center; word-wrap: break-word; overflow-wrap: break-word;\">\n" +
                                private_key +
                "            </p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
