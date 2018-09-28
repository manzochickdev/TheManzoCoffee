package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import xyz.manzodev.themanzocoffee.Models.User;
import xyz.manzodev.themanzocoffee.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    FragmentUserBinding fragmentUserBinding;
    String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getUserId();
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String s = sharedPreferences.getString(getString(R.string.userID),null);
        return s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentUserBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);
        fragmentUserBinding.setIMainActivity((IMainActivity) getContext());
        getUser(userId);


        return fragmentUserBinding.getRoot();
    }


    private void getUser(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(getString(R.string.user)).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = (User)dataSnapshot.getValue(User.class);
                fragmentUserBinding.setUser(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateUser(){getUser(userId);}


}
