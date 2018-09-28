package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.themanzocoffee.Interface.IMapPickerActivity;
import xyz.manzodev.themanzocoffee.databinding.FragmentMapPickerBinding;

public class MapPickerFragment extends Fragment {

    FragmentMapPickerBinding fragmentMapPickerBinding;
    IMapPickerActivity iMapPickerActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapPickerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map_picker, container, false);
        fragmentMapPickerBinding.setIMapPickerActivity((IMapPickerActivity)getContext());

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.fr_map);
        mapFragment.getLatLng(iMapPickerActivity);
        return fragmentMapPickerBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMapPickerActivity = (IMapPickerActivity) context;
    }
}
