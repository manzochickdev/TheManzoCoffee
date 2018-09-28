package xyz.manzodev.themanzocoffee;

import xyz.manzodev.themanzocoffee.Models.Product;

public interface IOrderActivity {
    void inflateProduct(Product product);
    void updateCart();
}
