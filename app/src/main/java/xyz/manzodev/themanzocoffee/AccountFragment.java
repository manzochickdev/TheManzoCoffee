package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import xyz.manzodev.themanzocoffee.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {
    FragmentAccountBinding fragmentAccountBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAccountBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false);
        fragmentAccountBinding.setAccountFragment(this);
        return fragmentAccountBinding.getRoot();
    }
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
    }

}
