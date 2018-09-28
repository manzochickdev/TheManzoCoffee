package xyz.manzodev.themanzocoffee;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DataBinding {
    @BindingAdapter("getImage")
    public static void getImage(ImageView view,String imgUrl){
        Glide.with(view.getContext())
                .load(imgUrl)
                .into(view);

    }
    @BindingAdapter("setFont")
    public static void setFont(TextView view,String font){
        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(),"Fonts/"+font);
        view.setTypeface(typeface);
    }
}
