package xyz.manzodev.themanzocoffee.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.R;
import xyz.manzodev.themanzocoffee.ViewModel.ProductViewModel;
import xyz.manzodev.themanzocoffee.databinding.LayoutCartItemBinding;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ProductViewModel> viewModels;
    ArrayList<Integer> quantity;
    ArrayList<Long> totalPrice;

    public CartItemAdapter(Context mContext, ArrayList<ProductViewModel> viewModels, ArrayList<Integer> quantity, ArrayList<Long> totalPrice) {
        this.mContext = mContext;
        this.viewModels = viewModels;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_cart_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layoutCartItemBinding.setViewmodel(viewModels.get(position));
        holder.layoutCartItemBinding.setQuantity(quantity.get(position));
        holder.layoutCartItemBinding.setTotalPrice(totalPrice.get(position));
        int sizePosition = viewModels.get(position).selectedSize;
        String optional = viewModels.get(position).getProduct().getSize().get(sizePosition).getName() + "\n";
        for (int i=0;i<viewModels.get(position).selectedAdditional.length;i++){
            if (viewModels.get(position).selectedAdditional[i]){
                optional = optional + viewModels.get(position).getProduct().getAdditional().get(i).getName() + "\n";
            }
        }
        holder.layoutCartItemBinding.tvOptional.setText(optional);
        holder.layoutCartItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutCartItemBinding layoutCartItemBinding;
        public ViewHolder(View itemView) {
            super(itemView);
            layoutCartItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
