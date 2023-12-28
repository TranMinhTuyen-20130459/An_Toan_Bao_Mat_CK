package controller.shop.customer;

import mail.Email;
import mail.SendMail;
import model.Customer;
import service.CustomerService;
import utils.BodyMailRegister;
import utils.RSACipher;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LeakKey", value = "/shop/leak_key")
public class LeakKey extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shop/profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        session.getAttribute("auth_session_customer");
        Customer cus = (Customer) session.getAttribute("auth_customer");
        String email = cus.getEmail();
        String password = request.getParameter("password");
        Customer checkAgain = CustomerService.checkLogin(email, password);
        if (checkAgain != null && checkAgain.getId_status_acc() == 1) {
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

            String body = BodyMailRegister.createBodyMailForLeakKey();
            Email sendEmailForVerify = new Email("nguyenphutai840@gmail.com", "nlrtjmzdmlihnlrz",
                    "Cấp lại key cho dịch vụ ký đơn hàng của LAB CHEMICALS", body);
            SendMail.sendEmailWithAttachment(email, sendEmailForVerify,"Private_Key.txt", pr_key);

            int id_customer = CustomerService.getIdCustomer(email);
            CustomerService.updateExpiredKey(id_customer);
            CustomerService.savePuKey(id_customer, pu_key);

            out.write("success");
        } else {
            out.write("error");
        }
    }
}
