package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

import xyz.manzodev.themanzocoffee.Adapter.CartItemAdapter;
import xyz.manzodev.themanzocoffee.ViewModel.ProductViewModel;
import xyz.manzodev.themanzocoffee.databinding.FragmentCartBinding;


public class CartFragment extends Fragment {

    FragmentCartBinding fragmentCartBinding;
    Set<String> orderedProduct;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCartBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        orderedProduct = sharedPreferences.getStringSet("orderedProduct",null);
        ArrayList<ProductViewModel> viewModels = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<Long> totalPrice = new ArrayList<>();
        for (String s : orderedProduct){
            Gson gson = new Gson();
            ProductViewModel p = gson.fromJson(s,ProductViewModel.class);
            viewModels.add(p);
            quantity.add(sharedPreferences.getInt((s+"quantity"),0));
            totalPrice.add(sharedPreferences.getLong((s+"totalPrice"),0));
        }
        CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(),viewModels,quantity,totalPrice);
        fragmentCartBinding.rvItemCart.setAdapter(cartItemAdapter);
        fragmentCartBinding.rvItemCart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        return fragmentCartBinding.getRoot();
    }
}
