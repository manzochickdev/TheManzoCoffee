package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import xyz.manzodev.themanzocoffee.Models.News;
import xyz.manzodev.themanzocoffee.Models.User;
import xyz.manzodev.themanzocoffee.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    User user;
    ArrayList<News> news;
    FragmentHomeBinding fragmentHomeBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        user = (User)bundle.getSerializable(getString(R.string.user));
        //todo remove code
        news = new ArrayList<>();
//        try {
//            //todo get news here
//            news = new getNews().execute("https://www.thecoffeehouse.com/blogs/news").get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        NewsAdapter adapter = new NewsAdapter(news,getContext());
        fragmentHomeBinding.rvNews.setAdapter(adapter);
        fragmentHomeBinding.rvNews.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return fragmentHomeBinding.getRoot();
    }

    public User getUser(){
        return user;
    }

    private class getNews extends AsyncTask<String , Void , ArrayList<News>>{
        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            ArrayList<News> news = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements elements = doc.select("div.col-md-6.col-sm-6.col-xs-12.t-fix");
                Log.d("OK", ""+elements.size());
                for (int i=0;i<elements.size();i++){
                    String title = elements.get(i).select("div.news-dt > h3").text();
                    String imgUrl = "https:"+ elements.get(i).select("div.img-hover > img").attr("src");
                    String postUrl = "https://www.thecoffeehouse.com" + elements.get(i).select("a.news-ct").attr("href");
                    news.add(new News(title,imgUrl,postUrl));
                }
                Log.d("OK", ""+news.size());
                for (News n : news){
                    Log.d("OK", n.getTitle() +" : "+n.getImgUrl()+" : "+n.getPostUrl());
                }

                //todo: Handle page 2



            } catch (IOException e) {
                e.printStackTrace();
            }
            return news;
        }
    }


}
