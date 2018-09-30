package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.themanzocoffee.Interface.IEditFragment;
import xyz.manzodev.themanzocoffee.databinding.FragmentEditDialogBinding;


public class EditDialogFragment extends DialogFragment {

    FragmentEditDialogBinding fragmentEditDialogBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEditDialogBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_dialog, container, false);
        Bundle bundle = this.getArguments();
        fragmentEditDialogBinding.setTitle(bundle.getString("title"));
        fragmentEditDialogBinding.setData(bundle.getString("data"));
        fragmentEditDialogBinding.setIEditFragment((IEditFragment)getTargetFragment());
        fragmentEditDialogBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentEditDialogBinding.getIEditFragment().onDataBack(fragmentEditDialogBinding.getData());
                getDialog().dismiss();
            }
        });
        return fragmentEditDialogBinding.getRoot();
    }
}
