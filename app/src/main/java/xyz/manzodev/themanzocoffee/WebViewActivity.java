package xyz.manzodev.themanzocoffee;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.manzodev.themanzocoffee.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding activityWebViewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWebViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_web_view);
        String url = getIntent().getStringExtra(getString(R.string.Url));
        activityWebViewBinding.wv.getSettings().setLoadsImagesAutomatically(true);
        activityWebViewBinding.wv.loadUrl(url);

    }
}
