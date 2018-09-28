package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.Adapter.ProductDetailAdapter;
import xyz.manzodev.themanzocoffee.Models.Product;
import xyz.manzodev.themanzocoffee.ViewModel.ProductViewModel;
import xyz.manzodev.themanzocoffee.databinding.FragmentProductDetailBinding;


public class ProductDetailFragment extends Fragment {
    FragmentProductDetailBinding fragmentProductDetailBinding;
    ArrayList<Product.Optional> sizes , additionals;
    ProductViewModel productViewModel;
    Product product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            product = (Product)bundle.getSerializable("product");
            if (product.getSize() != null) {
                sizes = product.getSize();
            } else {
                sizes = new ArrayList<>();
                product.size = sizes;
            }
            if (product.getAdditional() != null) {
                additionals = product.getAdditional();
            } else {
                additionals = new ArrayList<>();
                product.additional = additionals;
            }
            productViewModel = new ProductViewModel(product,getContext());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail, container, false);
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getContext(),sizes,additionals,productViewModel);
        fragmentProductDetailBinding.setProduct(product);
        fragmentProductDetailBinding.setProductViewModel(productViewModel);
        fragmentProductDetailBinding.rvOptional.setAdapter(productDetailAdapter);
        fragmentProductDetailBinding.rvOptional.setNestedScrollingEnabled(false);
        fragmentProductDetailBinding.rvOptional.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return fragmentProductDetailBinding.getRoot();
    }
}
