package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.Adapter.ProductAdapter;
import xyz.manzodev.themanzocoffee.Models.Product;
import xyz.manzodev.themanzocoffee.databinding.FragmentProductBinding;


public class ProductFragment extends Fragment {

    FragmentProductBinding fragmentProductBinding;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product, container, false);
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(),products);
        getData();
        fragmentProductBinding.rvProduct.setAdapter(productAdapter);
        fragmentProductBinding.rvProduct.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        return fragmentProductBinding.getRoot();
    }

    void getData(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    for (DataSnapshot d : data.getChildren()){
                        for (DataSnapshot a : d.getChildren()){
                            Product product = a.getValue(Product.class);
                            products.add(product);
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
