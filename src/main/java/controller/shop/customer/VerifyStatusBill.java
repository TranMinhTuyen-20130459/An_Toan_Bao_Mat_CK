package controller.shop.customer;

import database.dao.BillDAO;
import model.CustomerSecurity;
import service.BillService;
import service.CustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "VerifyStatusBill", urlPatterns = "/shop/verify-status-bill")
@MultipartConfig
public class VerifyStatusBill extends HttpServlet {
    String uuidStr;
    String email;
    String idStatus;
    String idBill;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        uuidStr = req.getParameter("key");
        email = req.getParameter("email");
        idStatus = req.getParameter("idStatus");
        idBill = req.getParameter("idBill");
        String uuidOfEmail = CustomerService.getUUID(email);
        try{
            if(uuidStr.equals(uuidOfEmail)){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shop/verify-status-bill.jsp");
                dispatcher.forward(req, res);
            }else{
                HttpSession session = (HttpSession) req.getSession();
                session.getAttribute("auth_session_customer");
                session.invalidate();
                req.setAttribute("error","Quá trình xác thực trạng thái đơn hàng thất bại!");
                req.getServletContext().getRequestDispatcher("/shop/login").forward(req, res);
            }
        }catch (Exception e){
            HttpSession session = (HttpSession) req.getSession();
            session.getAttribute("auth_session_customer");
            session.invalidate();
            req.setAttribute("error","Quá trình xác thực trạng thái đơn hàng thất bại!");
            req.getServletContext().getRequestDispatcher("/shop/login").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nav = req.getParameter("nav");
        if (nav != null) {
            res.sendRedirect(req.getContextPath() + nav);
            return;
        }

        int int_idStatus = Integer.parseInt(idStatus);
        int int_idBill = Integer.parseInt(idBill);
        String privateKey = req.getParameter("private_key");
        if (privateKey == null) {
            Part filePart = req.getPart("private_key_file");
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception ignored) {
            }
            privateKey = sb.toString();
        }

        if(BillService.checkVerifyPrivateKey(int_idBill, privateKey)){
            BillService.updateStatusABill(int_idBill, int_idStatus, privateKey);
            req.setAttribute("flag", "verify-successful");
            req.getRequestDispatcher("/shop/verify-status-bill.jsp").forward(req, res);
        }else{
            req.setAttribute("flag", "verify-failed");
            req.getRequestDispatcher("/shop/verify-status-bill.jsp").forward(req, res);
        }

    }
}
