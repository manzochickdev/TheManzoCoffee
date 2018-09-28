package xyz.manzodev.themanzocoffee.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.IOrderActivity;
import xyz.manzodev.themanzocoffee.Models.Product;
import xyz.manzodev.themanzocoffee.R;
import xyz.manzodev.themanzocoffee.databinding.LayoutProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Product> products;

    public ProductAdapter(Context mContext, ArrayList<Product> products) {
        this.mContext = mContext;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.layoutProductBinding.setProduct(products.get(position));
        holder.layoutProductBinding.setIOrderActivity((IOrderActivity) mContext);
        holder.layoutProductBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutProductBinding layoutProductBinding;
        public ViewHolder(View itemView) {
            super(itemView);
            layoutProductBinding = DataBindingUtil.bind(itemView);
        }
    }
}
