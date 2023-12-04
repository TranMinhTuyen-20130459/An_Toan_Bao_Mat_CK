package model;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
@Builder
public class BillDetail implements Serializable {

    private int id_bill;
    private int id_product;
    private int quantity;
    private BigDecimal listed_price;
    private BigDecimal current_price;

}
