package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.Models.Place;
import xyz.manzodev.themanzocoffee.databinding.LayoutPlaceBinding;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    ArrayList<Place> places;
    Context mContext;
    int selected = -1;

    public PlaceAdapter(ArrayList<Place> places, Context mContext) {
        this.places = places;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //Todo Đảy location lên nếu chưa có latlng , nếu có rồi thì lấy latlng xuống
        holder.layoutPlaceBinding.setPlace(places.get(position));
        holder.layoutPlaceBinding.setIsSelected(selected==position);
        holder.layoutPlaceBinding.setIMainActivity((IMainActivity) mContext);
        holder.layoutPlaceBinding.layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selected);
                ((IMainActivity) mContext).getPlace(holder.layoutPlaceBinding.getPlace().getAddress());
                selected = position;
                holder.layoutPlaceBinding.setIsSelected(selected==position);

            }
        });
        holder.layoutPlaceBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPlaceBinding layoutPlaceBinding;
        public ViewHolder(View itemView) {
            super(itemView);
            layoutPlaceBinding = DataBindingUtil.bind(itemView);
        }
    }
}
