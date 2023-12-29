package controller.admin.bill;

import mail.Email;
import mail.SendMail;
import service.CustomerService;
import utils.BodyMailRegister;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "AjaxBillUpdateStatus", value = "/AjaxBillUpdateStatus")
public class AjaxBillUpdateStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idBill = Integer.parseInt(request.getParameter("idBill"));
        int idStatus = Integer.parseInt(request.getParameter("idStatus"));
        String status = request.getParameter("status");
        String toEmail = CustomerService.getEmailFromIdBill(idBill);

        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        HttpSession session = request.getSession(true);
        request.setAttribute("ses_verify", session);
        session.setAttribute("uuid", uuidStr);
        session.setAttribute("id_status", idStatus);

        var context_path = request.getContextPath();
        String body = BodyMailRegister.createBodyMailForUpdateStatusBill(context_path, uuidStr, status);
        Email sendEmailForVerify = new Email("nguyenphutai840@gmail.com", "nlrtjmzdmlihnlrz",
                "Xác thực tình trạng đơn hàng của LAB CHEMICALS", body);
        SendMail.sendMail(toEmail, sendEmailForVerify);
    }
}