package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.themanzocoffee.Models.User;
import xyz.manzodev.themanzocoffee.databinding.FragmentUserInfoBinding;

public class UserInfoFragment extends Fragment {


    FragmentUserInfoBinding fragmentUserInfoBinding;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        user = (User)bundle.getSerializable(getString(R.string.user));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentUserInfoBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_info,container,false);
        fragmentUserInfoBinding.setUser(user);
        fragmentUserInfoBinding.setIMainActivity((IMainActivity)getContext());
        return fragmentUserInfoBinding.getRoot();
    }

}
