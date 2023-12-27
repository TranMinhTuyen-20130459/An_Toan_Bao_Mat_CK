package controller.shop.customer;

import mail.Email;
import mail.SendMail;
import model.CustomerSecurity;
import service.CustomerService;
import utils.AsymmetricEncrypt;
import utils.BodyMailRegister;
import utils.RSACipher;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "DoRegisterCustomerServlet", value = "/shop/register")
public class DoRegisterCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/shop/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm_pass = request.getParameter("confirm-pass");
        if(email.equals("") || password.equals("") || confirm_pass.equals("")){
            request.setAttribute("text_register", "Vui lòng hãy nhập vào những trường còn thiếu");
            request.getServletContext().getRequestDispatcher("/shop/register.jsp").forward(request, response);
            return;
        }
        if (CustomerService.checkExist(email)) {
            request.setAttribute("text_register", "Email đã tồn tại, vui lòng chọn Email khác");
            request.getServletContext().getRequestDispatcher("/shop/register.jsp").forward(request, response);
        } else {
            if (password.equals(confirm_pass)) {
                RSACipher rsaCipher = new RSACipher();
                String pu_key = "";
                String pr_key = "";
                try {
                    String[] keyPair = rsaCipher.generateKeyPair(512);
                    pu_key = keyPair[0];
                    pr_key = keyPair[1];
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                UUID uuid = UUID.randomUUID();
                String id = uuid.toString();
                CustomerSecurity customer_register = new CustomerSecurity(id, email, password);
                HttpSession session = request.getSession(true);
                request.setAttribute("session_cus", session);
                session.setAttribute("cus", customer_register);
                session.setAttribute("pu_key", pu_key);
                var context_path = request.getContextPath();
//                String body = "Để tạo tài khoản và sử dụng các dịch vụ của chúng tôi hãy " +
//                        "<a href='http://localhost:8080"+context_path+"/shop/verify-register?key=" + id + "'>nhấn vào đây!</a>";
                String body = BodyMailRegister.createBodyMailForRegister(context_path, id);
                Email sendEmailForVerify = new Email("nguyenphutai840@gmail.com", "nlrtjmzdmlihnlrz",
                        "Chào mừng bạn trở thành một phần của LAB CHEMICALS", body);
                SendMail.sendEmailWithAttachment(email, sendEmailForVerify,"Private_Key.txt", pr_key);
                request.setAttribute("success_register", "Vui lòng kiểm tra lại hộp thư trong email mà bạn đăng ký");
                request.getServletContext().getRequestDispatcher("/shop/register.jsp").forward(request, response);
            } else {
                request.setAttribute("text_register", "Xác thực mật khẩu không hợp lệ, vui lòng thử lại");
                request.getServletContext().getRequestDispatcher("/shop/register.jsp").forward(request, response);
            }
        }
    }
}
