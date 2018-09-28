package xyz.manzodev.themanzocoffee.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.Models.Product;
import xyz.manzodev.themanzocoffee.R;
import xyz.manzodev.themanzocoffee.ViewModel.ProductViewModel;
import xyz.manzodev.themanzocoffee.databinding.LayoutOptionalAdditionalBinding;
import xyz.manzodev.themanzocoffee.databinding.LayoutOptionalSizeBinding;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Product.Optional> sizes , additionals;
    ArrayList<Product.Optional> total;
    ProductViewModel productViewModel;
    int pos = 0;

    public ProductDetailAdapter(Context mContext, ArrayList<Product.Optional> sizes, ArrayList<Product.Optional> additionals,ProductViewModel productViewModel) {
        this.mContext = mContext;
        this.sizes = sizes;
        this.additionals = additionals;
        this.productViewModel = productViewModel;
        total = new ArrayList<>();
        total.addAll(sizes);
        total.addAll(additionals);
        productViewModel.sizeSelected(0);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        switch (viewType){
            case 0 :
                LayoutOptionalSizeBinding sizeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_optional_size,parent,false);
                return new ViewHolder(sizeBinding);
            case 1:
                LayoutOptionalAdditionalBinding additionalBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_optional_additional,parent,false);
                return new ViewHolder(additionalBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        switch (holder.getItemViewType()){
            case 0 :
                holder.layoutOptionalSizeBinding.setSize(total.get(position));
                holder.layoutOptionalSizeBinding.rb.setChecked(pos==position);
                holder.layoutOptionalSizeBinding.layoutSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyItemChanged(pos);
                        pos = position;
                        holder.layoutOptionalSizeBinding.rb.setChecked(pos==position);
                        productViewModel.sizeSelected(position);
                    }
                });
                holder.layoutOptionalSizeBinding.executePendingBindings();
                break;
            case 1 :
                holder.layoutOptionalAdditionalBinding.setAdditional(total.get(position));
                holder.layoutOptionalAdditionalBinding.layoutAdditional.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.layoutOptionalAdditionalBinding.cb.isChecked();
                        holder.layoutOptionalAdditionalBinding.cb.setChecked(!checked);
                        productViewModel.additionalSelected(position);

                    }
                });
                holder.layoutOptionalAdditionalBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return total.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position<sizes.size()){
            return 0;
        }
        else return 1;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutOptionalSizeBinding layoutOptionalSizeBinding;
        LayoutOptionalAdditionalBinding layoutOptionalAdditionalBinding;
        public ViewHolder(LayoutOptionalSizeBinding layoutOptionalSizeBinding) {
            super(layoutOptionalSizeBinding.getRoot());
            this.layoutOptionalSizeBinding = layoutOptionalSizeBinding;
        }
        public ViewHolder(LayoutOptionalAdditionalBinding layoutOptionalAdditionalBinding) {
            super(layoutOptionalAdditionalBinding.getRoot());
            this.layoutOptionalAdditionalBinding = layoutOptionalAdditionalBinding;
        }
    }
}
