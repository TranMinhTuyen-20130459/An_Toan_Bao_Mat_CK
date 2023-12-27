package controller.shop.customer;

import model.Customer;
import service.CustomerService;

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
            out.write("success");
        } else {
            out.write("error");
        }
    }
}
