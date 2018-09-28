package xyz.manzodev.themanzocoffee;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.themanzocoffee.Models.News;
import xyz.manzodev.themanzocoffee.databinding.LayoutNewsBinding;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    ArrayList<News> news;
    Context mContext;

    public NewsAdapter(ArrayList<News> news, Context mContext) {
        this.news = news;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layoutNewsBinding.setNews(news.get(position));
        holder.layoutNewsBinding.setIMainActivity((IMainActivity)mContext);
        holder.layoutNewsBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutNewsBinding layoutNewsBinding;
        public ViewHolder(View itemView) {
            super(itemView);
            layoutNewsBinding = DataBindingUtil.bind(itemView);
        }
    }
}
