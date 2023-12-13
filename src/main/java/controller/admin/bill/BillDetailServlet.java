package controller.admin.bill;

import database.dao.BillDAO;
import model.BillDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BillDetailManager", value = "/admin/chi-tiet-don-hang")
public class BillDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id_bill = Integer.parseInt(request.getParameter("id_bill"));

            List<BillDetail> list_bill_detail = new BillDAO().getBillDetailsById(id_bill);

            request.setAttribute("BillDetail", list_bill_detail);
            request.getRequestDispatcher("/admin-jsp/bill-detail.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/dang-nhap");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
