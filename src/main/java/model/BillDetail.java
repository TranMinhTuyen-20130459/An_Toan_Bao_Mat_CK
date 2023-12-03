package model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Builder
public class BillDetail implements Serializable {

    private int id_bill;
    private int id_product;
    private int quantity;
    private BigInteger listed_price;
    private BigInteger current_price;

}
