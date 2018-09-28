package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.themanzocoffee.databinding.FragmentBottomBarBinding;

public class BottomBarFragment extends Fragment {

    FragmentBottomBarBinding fragmentBottomBarBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBottomBarBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_bottom_bar,container,false);
        fragmentBottomBarBinding.setBottomBarFragment(this);
        fragmentBottomBarBinding.setIMainActivity((IMainActivity) getContext());
        setSelectedItem(1);
        return fragmentBottomBarBinding.getRoot();
    }

    public void setSelectedItem(int selected){
        fragmentBottomBarBinding.setSelected(selected);
        fragmentBottomBarBinding.getIMainActivity().inflateFragment(selected);
    }
}
