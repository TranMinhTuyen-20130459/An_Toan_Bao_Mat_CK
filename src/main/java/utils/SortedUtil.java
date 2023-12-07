package utils;

import model.BillDetail;

import java.util.Comparator;
import java.util.List;

public class SortedUtil {

    /**
     * Sắp xếp danh sách chi tiết hóa đơn theo ID sản phẩm.
     *
     * @param billDetails Danh sách chi tiết hóa đơn cần sắp xếp.
     */
    public static void sortByProductId(List<BillDetail> billDetails) {
        // Sử dụng Comparator để so sánh theo id_product
        var byProductId = Comparator.comparingInt(BillDetail::getId_product);

        // Sắp xếp danh sách chi tiết hóa đơn sử dụng Comparator đã tạo
        billDetails.sort(byProductId);
    }

}
