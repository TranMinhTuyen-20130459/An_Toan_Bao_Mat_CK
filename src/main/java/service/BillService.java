package service;

import database.dao.BillDAO;
import model.Bill;
import java.util.ArrayList;
import java.util.List;

public class BillService {

    public static List<Bill> getAllBill() {
        BillDAO dao = new BillDAO();
        List<Bill> result = new ArrayList<>();
        try {

            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (dao.connectDB != null) dao.connectDB.close();
        }
    }

}
