package controller.shop;

import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomeServlet", value = "/shop/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("type");

        List<Product> all_products = ProductService.getProducts();

        Map<String, List<Product>> map = new LinkedHashMap<>() {{
            put("hoa-chat", all_products);
            put("dung-cu", all_products);
            put("thiet-bi", all_products);
        }};
        req.setAttribute("product_map", map);

        req.setAttribute("discount_products", all_products);

        req.setAttribute("selling_products", all_products);

        req.setAttribute("hot_products", all_products);

        req.setAttribute("new_products", all_products);

        req.setAttribute("view_products", all_products);

        getServletContext().getRequestDispatcher("/shop/home.jsp").forward(req, resp);
    }
}