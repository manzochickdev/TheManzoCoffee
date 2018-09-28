package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import xyz.manzodev.themanzocoffee.Adapter.ProductCategoryAdapter;
import xyz.manzodev.themanzocoffee.Models.Product;
import xyz.manzodev.themanzocoffee.databinding.FragmentOrderBinding;


public class OrderFragment extends Fragment {

    FragmentOrderBinding fragmentOrderBinding;
    ArrayList<String> categories;
    ProductCategoryAdapter productCategoryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOrderBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_order, container, false);
        fragmentOrderBinding.setIMainActivity((IMainActivity) getContext());

        categories = new ArrayList<>();
        productCategoryAdapter = new ProductCategoryAdapter(getContext(),categories);
        getCategory();
        fragmentOrderBinding.rvMenu.setAdapter(productCategoryAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        fragmentOrderBinding.rvMenu.setLayoutManager(staggeredGridLayoutManager);
        return fragmentOrderBinding.getRoot();
    }

    private void pushData() {
//        ArrayList<Product> products = new ArrayList<>();
//        ArrayList<Product.Optional> size = new ArrayList<Product.Optional>();
//        size.add(new Product.Optional("Size nho",10000));
//        ArrayList<Product.Optional> additional = new ArrayList<Product.Optional>();
//        additional.add(new Product.Optional("Them Topping",10000));
//        products.add(new Product("A","Ca Phe","",10000,size,additional));
//        products.add(new Product("A","CocaCola","",10000,size,additional));
//        products.add(new Product("B","Kem Trang Tien","",10000,size,additional));
//        for (Product p : products){
//            FirebaseDatabase.getInstance().getReference().child("Product").child(p.category).child(p.name).setValue(p);
//        }

        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product.Optional> size = new ArrayList<Product.Optional>();
        size.add(new Product.Optional("Size vừa (350ml)",65000));
        products.add(new Product("Thức uống trái cây","Nước cam tươi","https://lh3.googleusercontent.com/NfGJ_Zsruonz1dQgN25pYrpPan_JOxIwu-poVmzIv3fxK0YLjVz7VfIjaZ5c9n2zV_ikJspludIAN6FgqrKawCqS_nhcmskXEMkiKWs1VvuvXKZVLvU2CyEaHpJQ6K6-4zwYZL2Iau5bso39fEJR4-z5zKS5nWtAxRvcgxauCLxEjDJdYohOezK1b8Y37wRKYqe2yffeGB9QN5XiZRCknR1fUwWM0l-yjXcwKuUC2YkKPUXoH6RlfMMwcDxVWsJ4hOnCgyqVo1UWeHcrrp0p3jzcVCYTtZBcbed2mSwqLow4MkAGhMOmpUrmmmwOz9X3ztsH1GErQNarq8MmFUiCVy-tCFv9sZBPeZVjveBcd0wyKWmctE1dGF-mA0zZY6yY2PlJ5limoKg0mX3k4DZKZ2_gIILSLnDJuZLjLxywBWTPgclsdg7vo9ZeI0eZujJxHp8tqwttKIfxrumxhnV8c61xXWAuSqY_ZT6gyL6yUtm4jI9XNlnzDOYUY-xgOAwqwYVDF6SngtIDStVtK0Af04Fiwl0v6ay4FAmJJq1Ts56T_XfI8imakqUhL-3DJHDldJ-zlouZxsWXnedFEKVUR8fpItmatGmD6Lt0ZMaU5Je64_8_wqlxDlURT225bA=s196-no",65000,size,null));
        products.add(new Product("Thức uống trái cây","Mojito chanh tươi","https://lh3.googleusercontent.com/mHAW1O4jc90KmkPqyDdfNW2ZTr6MAiRtaSRw5xNYh7-do8WtgzU2Cv7PH4FXAi3VkqHU7Cf6-oXIC-jPTUv3BdS8wT9tRxQodz0K9naMEWPGmkYIABbxK9Izlb036mZPcLUB85JK4QSv0MggxyWVELLCUzdnfv2eNd4VKAtAt5Xy7HwheI39c-m0biyo3DKRBj2CdLS4T64QsWrbLM_xhQow4lhaZ2p5OhDlzkExYhJwwqbCgB9e-DMQ0V4L35qhQ0FAYuZNM3Rz68W9PmkzFT3-sXhfj3GcJqNIvaYucMSfdKjlylWG8hgHKWJc3uCSsPSJoHTF1Coo1UJX8cy1pym65oqL8Ia0NUK20rPXWM4YqOhYm-vAmMobwQ67HKZl9oAp61eVcdQWnZDwFOmsoJ-DamwSdTQ6uy_Z2hOirOqfBcMY278-ZL5T6XB5KeONw5gh85RF8LQ_MnWZp_8-7vBD9QsR7wRufK6NiezrM0_moTJ04SaoYGspy-NCS72jxw-o6VBEa6iqAKR6424eDEnWDYHAU_6qXe-tFvK12cf_XirsjLtyLGPx4Qtdxe2oxZLuywTCfQEh9jPiZFKXSwGisJSk4QqiiSh9I5CwCl7B6e0jnVFdHm_y0OWakg=s196-no",65000,size,null));
        FirebaseDatabase.getInstance().getReference().child("Product").child("Thức uống trái cây").child("Items").setValue(products);

    }
    private void getCategory() {
        FirebaseDatabase.getInstance().getReference().child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String img = data.child("imgUrl").getValue(String.class);
                    categories.add(img);
                    productCategoryAdapter.notifyDataSetChanged();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
