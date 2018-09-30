package xyz.manzodev.themanzocoffee.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import xyz.manzodev.themanzocoffee.BR;
import xyz.manzodev.themanzocoffee.IOrderActivity;
import xyz.manzodev.themanzocoffee.Models.Product;

public class ProductViewModel extends BaseObservable{
    Product product;
    public Boolean[] selectedAdditional;
    public int selectedSize;
    transient int correctPosition;
    transient int quantity=1;
    transient long sizeCost,additionalCost;
    transient long totalPrice;
    transient Context context;

    public ProductViewModel(Product product,Context context) {
        this.product = product;
        this.context = context;
        selectedAdditional = new Boolean[product.getAdditional().size()];
        for (int i=0;i<selectedAdditional.length;i++){
            selectedAdditional[i] = false;
        }
        correctPosition = product.getSize().size();
    }

    public void sizeSelected(int position){
        selectedSize = position;
        String name = product.getSize().get(position).getName();
        long price = product.getSize().get(position).getPrice();
        sizeCost = price;
        updatePrice();
    }

    public void additionalSelected(int position){
        position -= correctPosition;
        selectedAdditional[position] = !selectedAdditional[position];
        additionalCost=0;
        for (int i=0;i<selectedAdditional.length;i++){
            if (selectedAdditional[i]){
                String name = product.getAdditional().get(i).getName();
                long price = product.getAdditional().get(i).getPrice();
                additionalCost += price;
                updatePrice();
            }
        }

    }

    private Long updatePrice(){
        totalPrice = (sizeCost + additionalCost)*quantity;
        notifyPropertyChanged(BR.price);
        return totalPrice;
    }

    public void increaseQuantity(){
        quantity++;
        notifyPropertyChanged(BR.quantity);
    }

    public void decreaseQuantity(){
        if (quantity >1){ quantity-- ;
        notifyPropertyChanged(BR.quantity);}
    }

    public void addToCart(ProductViewModel productViewModel){
        Gson gson = new Gson();
        String json = gson.toJson(productViewModel);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        if (sp.getStringSet("orderedProduct",null)==null){
            Set<String> ordered = new HashSet<>();
            ordered.add(json);
            editor.putStringSet("orderedProduct",ordered);
            editor.putInt((json+"quantity"),this.quantity);
            editor.putLong((json+"totalPrice"),this.totalPrice);
            editor.commit();

        }
        else {
            Boolean found = false;
            Set<String> ordered = sp.getStringSet("orderedProduct", null);
            for (String s : ordered) {
                if (json.equals(s)) {
                    int quantity = sp.getInt((json+"quantity"),0);
                    long price = sp.getLong((json+"totalPrice"),0);
                    quantity+=this.quantity;
                    price+=this.totalPrice;
                    editor.putInt((json+"quantity"),quantity);
                    editor.putLong((json+"totalPrice"),price);
                    editor.commit();
                    found=true;
                }
            }
            if (!found){
                ordered.add(json);
                editor.putStringSet("orderedProduct",ordered);
                editor.putInt((json+"quantity"),this.quantity);
                editor.putLong((json+"totalPrice"),this.totalPrice);
                editor.commit();
            }
        }

        Set<String> ordered = sp.getStringSet("orderedProduct", null);
        for (String s : ordered){
            Log.d("OK", "addToCart: "+s);
        }

        //todo add onback listener
        IOrderActivity iOrderActivity = (IOrderActivity) context;
        iOrderActivity.onBackListener();
    }

    //todo y tuong time picker
    /*1 fragment ui chua pick ngay + gio
    * 1 dialpg fragment chứa recycle view
    * dùng hàm gettime lấy ngày + giờ hiện tại
    * for (i= (7<giờ hiện tại<21 ?? giờ hiện tại : 7 ) ;i<21;i++)
    * add(i){
    * String time = (i):00 --> (i):30;
    * }*/


    public Product getProduct(){return product;}

    @Bindable
    public String getPrice(){return Long.toString(updatePrice());}
    @Bindable
    public String getQuantity(){return Integer.toString(quantity);}

}
