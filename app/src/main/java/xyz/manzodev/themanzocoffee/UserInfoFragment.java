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

import xyz.manzodev.themanzocoffee.Interface.IEditFragment;
import xyz.manzodev.themanzocoffee.Models.User;
import xyz.manzodev.themanzocoffee.databinding.FragmentUserInfoBinding;

public class UserInfoFragment extends Fragment implements IEditFragment {


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
        //todo OK :  name+phone onClick -> inflate DialogFragment , tao 1 interface de lay du lieu
        fragmentUserInfoBinding.layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName();
            }
        });
        return fragmentUserInfoBinding.getRoot();
    }

    void editName(){
        Bundle bundle = new Bundle();
        bundle.putString("title","Edit Name");
        bundle.putString("data",fragmentUserInfoBinding.getUser().name);

        EditDialogFragment editDialogFragment = new EditDialogFragment();
        editDialogFragment.setArguments(bundle);
        editDialogFragment.setTargetFragment(this,1432);
        //todo OK chuyen tag sang string
        editDialogFragment.show(getFragmentManager(),getString(R.string.dialog_fragment_edit));
    }

    @Override
    public void onDataBack(String data) {
        Log.d("OK", "onDataBack: " + data);
        fragmentUserInfoBinding.getUser().setName(data);
        fragmentUserInfoBinding.getIMainActivity().updateUser(user);
    }
}
