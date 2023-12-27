package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private int id;
    private List<CartItem> items;
    private Timestamp orderTime;
    private double total;
    private String status;
    private boolean isVerified;
}
