package controller.shop.checkout;

import database.dao.BillDAO;
import model.*;
import service.CustomerService;
import utils.HashUtil;
import utils.RSACipher;
import utils.RounderUtil;
import utils.SortedUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "checkout-servlet", urlPatterns = "/shop/checkout")
@MultipartConfig
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = (Customer) req.getSession().getAttribute("auth_customer");
        if (customer != null) {
            Cart cart = (Cart) req.getSession().getAttribute("cart");
            if (cart != null) {
                double billPrice = cart.getTotalPrice();
                req.setAttribute("bill_price", billPrice);
                double transportFee = CustomerService.getTransportFee(customer.getId_city());
                req.setAttribute("transport_fee", transportFee);
                req.setAttribute("cities", CustomerService.getCities());
                req.getRequestDispatcher("/shop/checkout.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nav = req.getParameter("nav");
        if (nav != null) {
            req.getSession().removeAttribute("cart");
            resp.sendRedirect(req.getContextPath() + nav);
            return;
        }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

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


        Customer cus = (Customer) req.getSession().getAttribute("auth_customer");
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        var time_order = new Timestamp(System.currentTimeMillis());

        // Create a bill.
        Bill bill = Bill.builder()
                .id_user(cus.getId())
                .id_status_bill(1)
                .id_city(cus.getId_city())
                .name_customer(name)
                .phone_customer(phone)
                .email_customer(email)
                .address_customer(address)
                .bill_price(cart.getTotalPrice())
                .total_price(cart.getTotalPrice() + CustomerService.getTransportFee(cus.getId_city()))
                .time_order(RounderUtil.roundTimestampToSeconds(time_order))
                .build();

        final var billDao = new BillDAO();
        int idBill;
        try {
            // Save to database.
            idBill = billDao.addBill(bill);
            bill.setId_bill(idBill);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Create bill details.
        List<BillDetail> billDetails = new ArrayList<>();
        for (Map.Entry<Integer, CartItem> item : cart.getMap().entrySet()) {
            final CartItem cartItem = item.getValue();

            BillDetail billDetail = BillDetail.builder()
                    .id_bill(idBill)
                    .id_product(cartItem.getProduct().getIdProduct())
                    .quantity(cartItem.getQuantity())
                    .listed_price(BigDecimal.valueOf((long) cartItem.getProduct().getListed_price() * cartItem.getQuantity()))
                    .current_price(BigDecimal.valueOf((long) cartItem.getProduct().getCurrent_price() * cartItem.getQuantity()))
                    .build();

            billDetails.add(billDetail);

            try {
                // Save bill details to database.
                billDao.addBillDetail(billDetail);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Sort bill details by product id.
        SortedUtil.sortByProductId(billDetails);
        // Update bill's bill details.
        bill.setBill_details(billDetails);

        String hashedBill;
        try {
            // Hash the bill.
            hashedBill = HashUtil.hashText(bill.toString(), HashUtil.SHA_1);
            var rsa = new RSACipher();
            // Encrypt the hash.
            var hashedBillEncrypted = rsa.encrypt(hashedBill, privateKey);
            bill.setHash_bill_encrypted(hashedBillEncrypted);
            // Update bill with the hash.
            billDao.updateEncryptedHash(bill.getId_bill(), hashedBillEncrypted);
        } catch (Exception e) {
            // TODO if encryption failed
        }

        req.setAttribute("flag", "checkout_successful");
        doGet(req, resp);
    }
}
