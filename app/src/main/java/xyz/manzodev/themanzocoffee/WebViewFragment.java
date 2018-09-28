package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.themanzocoffee.databinding.FragmentWebViewBinding;


public class WebViewFragment extends Fragment {

    FragmentWebViewBinding fragmentWebViewBinding;
    String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        url = bundle.getString(getString(R.string.Url));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWebViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_web_view, container, false);
        fragmentWebViewBinding.wv.getSettings().setLoadsImagesAutomatically(true);
        fragmentWebViewBinding.wv.loadUrl(url);
        return fragmentWebViewBinding.getRoot();
    }

}
