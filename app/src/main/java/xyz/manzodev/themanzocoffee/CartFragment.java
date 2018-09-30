package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TimeZone;

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
        fragmentCartBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return fragmentCartBinding.getRoot();
    }
    //todo 2 :  tao 1 ham void luu data duoi dang Gson json
    //todo FirebaseDb.getInstance.getRef.child("History").child("lay ngay gio").setValue("gson")
    //todo tao 1 class chua viewmodes , quantity , totalprice

    void saveData(){
        String timeStamp = getCurrentTime();
        testDate();
        Log.d("OK", "saveData: " + timeStamp);
    }

    //todo lay ngay o day
    void testDate(){
        //todo thuat toan hen gio giao hang
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,1);
        Log.d("OK", "testDate: "+ cal.get(Calendar.DAY_OF_MONTH)+" "+(cal.get(Calendar.MONTH)+1));
        cal.add(Calendar.DAY_OF_MONTH,1);
        Log.d("OK", "testDate: "+ cal.get(Calendar.DAY_OF_MONTH)+" "+(cal.get(Calendar.MONTH)+1));
        //giờ < 7{
        // date = nay , mai , kia - time = 7->21}
        //7<giờ<21{
        // date = nay , mai , kia - time(0)=giờ ->21 time(1)(2)=7->21}
        //giờ > 21{
        // date = mai , kia - time = 7->21}
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        int currentTime = cal.get(Calendar.HOUR_OF_DAY);

        //class DeliveryTime{
        //string date;
        //ArrayList<String> time;
        // }
        if(currentTime<7){
            //ArrayList<DeliveryTime>.add("Nay","mai","kia")
            //<DeliveryTime>.addTime(for i=7;i<=21;i++)

        }
        else if(currentTime>7 && currentTime<21){
            //ArrayList<DeliveryTime>.add("Hom nay","mai","kia")
            //ArrayList<Time>.add("Ngay bay gio")
            //for i=currentTime;i<21;i++
            //object(0).addTime{
            //add(i)
            // }
        }
        else{
            //ArrayList<DeliveryTime>.add("mai","kia")
            //<DeliveryTime>.addTime(for i=7;i<=21;i++)
        }
    }
    //todo Tai cau truc user : (Information , History , Coupon) , them key Coupon

    String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd' 'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }
    private class SaveObject{
        String timeStamp;
        ArrayList<ProductViewModel> viewModels;
        ArrayList<Integer> quantity;
        ArrayList<Long> totalPrice;

        public SaveObject(String timeStamp, ArrayList<ProductViewModel> viewModels, ArrayList<Integer> quantity, ArrayList<Long> totalPrice) {
            this.timeStamp = timeStamp;
            this.viewModels = viewModels;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }
    }
}
