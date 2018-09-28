package xyz.manzodev.themanzocoffee;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import xyz.manzodev.themanzocoffee.Models.Product;
import xyz.manzodev.themanzocoffee.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity implements IOrderActivity {

    ActivityOrderBinding activityOrderBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderBinding = DataBindingUtil.setContentView(OrderActivity.this,R.layout.activity_order);
        activityOrderBinding.tvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment cartFragment = new CartFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_container,cartFragment,getString(R.string.fragment_cart_tag));
                fragmentTransaction.addToBackStack(getString(R.string.fragment_cart_tag));
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void inflateProduct(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product",product);
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_container,productDetailFragment,getString(R.string.fragment_product_detail_tag));
        fragmentTransaction.addToBackStack(getString(R.string.fragment_product_detail_tag));
        fragmentTransaction.commit();
    }

    @Override
    public void updateCart() {

    }
}
