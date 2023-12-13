package model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@ToString(of = {"id_bill", "id_product", "quantity", "listed_price", "current_price"})
public class BillDetail implements Serializable {

    private int id_bill;
    private int id_product;
    private int quantity;
    private BigDecimal listed_price;
    private BigDecimal current_price;

    private String name_product;
    private String url_img_product;
}
