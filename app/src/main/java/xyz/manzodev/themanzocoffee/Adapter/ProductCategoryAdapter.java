package xyz.manzodev.themanzocoffee.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.IMainActivity;
import xyz.manzodev.themanzocoffee.R;
import xyz.manzodev.themanzocoffee.databinding.LayoutProductCategoryBinding;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    Context mContext;
    ArrayList<String> categories;

    public ProductCategoryAdapter(Context mContext, ArrayList<String> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_product_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layoutProductCategoryBinding.setCategory(categories.get(position));
        holder.layoutProductCategoryBinding.setIMainActivity((IMainActivity) mContext);
        holder.layoutProductCategoryBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutProductCategoryBinding layoutProductCategoryBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutProductCategoryBinding = DataBindingUtil.bind(itemView);
        }
    }
}
