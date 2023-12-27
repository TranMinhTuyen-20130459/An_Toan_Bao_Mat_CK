package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    private Product product;
    private int quantity = 0;

    public CartItem(Product product) {
        this.product = product;
    }
}
