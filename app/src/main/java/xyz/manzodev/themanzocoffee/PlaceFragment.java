package xyz.manzodev.themanzocoffee;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import xyz.manzodev.themanzocoffee.Models.Place;
import xyz.manzodev.themanzocoffee.databinding.FragmentPlaceBinding;


public class PlaceFragment extends Fragment{

    FragmentPlaceBinding fragmentPlaceBinding;
    ArrayList<Place> places;
    PlaceAdapter adapter;
    private static String TAG = "OK";
    LatLng curentLocation , place;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlace();
    }

    private void getPlace() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        places = new ArrayList<>();
        databaseReference.child("Place").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Place place = dataSnapshot.getValue(Place.class);
                places.add(place);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPlaceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false);
        fragmentPlaceBinding.setIMainActivity((IMainActivity)getContext());
        adapter = new PlaceAdapter(places, getContext());
        fragmentPlaceBinding.rvPlace.setAdapter(adapter);
        fragmentPlaceBinding.rvPlace.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        return fragmentPlaceBinding.getRoot();
    }

    void getDirection(){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+curentLocation.latitude+","+curentLocation.longitude+"&destination="+place.latitude+","+place.longitude));
        startActivity(intent);
    }
}
