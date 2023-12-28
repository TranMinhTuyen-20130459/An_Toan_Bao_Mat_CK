package mail;

import properties.sendMailProperties;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;

public class SendMail {
    public static void sendMail(String toEmail, Email email){
        Properties pro = new Properties();
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");

        Session session_send = Session.getInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getUsername(), email.getPassword());
            }
        });
        try {
            MimeMessage message = new MimeMessage(session_send);
            message.setFrom(new InternetAddress(email.getUsername()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(email.getSub(), "utf-8");
            message.setContent(email.getBody(), "text/html; charset=utf-8");
            Transport.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public static void sendEmailWithAttachment(String toEmail, Email email, String nameFile, String attachmentContent){
        Properties pro = sendMailProperties.loadProperties();
//        pro.put("mail.smtp.host", "smtp.gmail.com");
//        pro.put("mail.smtp.port", "587");
//        pro.put("mail.smtp.auth", "true");
//        pro.put("mail.smtp.starttls.enable", "true");

        Session session_send = Session.getInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getUsername(), email.getPassword());
            }
        });

        try {
            MimeMessage message = new MimeMessage(session_send);
            message.setFrom(new InternetAddress(email.getUsername()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(email.getSub(), "utf-8");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(email.getBody(), "text/html; charset=utf-8");

            // Thiết lập tập tin đính kèm
            ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentContent.getBytes(), "application/octet-stream");
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
            attachmentBodyPart.setFileName(nameFile);

            // Thêm nội dung và tập tin đính kèm vào email
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Thiết lập nội dung email và gửi email
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("success!");
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Email em = new Email("nguyenphutai840@gmail.com", "nlrtjmzdmlihnlrz",
                "text gửi mail mà hơi khó á",
                "Để tạo tài khoản và sử dụng các dịch vụ của chúng tôi hãy <a href='#'>nhấn vào đây!</a>");
        sendEmailWithAttachment("20130390@st.hcmuaf.edu.vn", em, "hello.txt", "xin chào!");
    }
}
