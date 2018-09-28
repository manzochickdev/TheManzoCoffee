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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import xyz.manzodev.themanzocoffee.Interface.IMapPickerActivity;
import xyz.manzodev.themanzocoffee.databinding.FragmentMapBinding;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    FragmentMapBinding fragmentMapBinding;
    GoogleMap googleMap;
    DataHandle dataHandle;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng currentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        return fragmentMapBinding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (dataHandle != null) {
            dataHandle.onMapReady();
        }
    }

    void getCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(currentLocation));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

            }
        });
    }

    public void moveCamera(String address) {
        FetchAddress fetchAddress = new FetchAddress(getContext());
        try {
            LatLng latLng = fetchAddress.execute(address).get();
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getLatLng(final IMapPickerActivity iMapPickerActivity) {
        dataHandle = new DataHandle() {
            @Override
            public void onMapReady() {
                moveCamera("Hà Nội , Việt Nam");
                final Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        int x = fragmentMapBinding.getRoot().getWidth() / 2;
                        int y = fragmentMapBinding.getRoot().getHeight() / 2;
                        LatLng position = googleMap.getProjection().fromScreenLocation(new Point(x, y));
                        String s = "";
                        try {
                            List<Address> addresses = geocoder.getFromLocation(position.latitude,position.longitude,1);
                            if (addresses.size()!=0){
                                s = addresses.get(0).getAddressLine(0);
                            }

                            iMapPickerActivity.getAddress(s);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    void getDirection(String destination){
        if (currentLocation!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+currentLocation.latitude+","+currentLocation.longitude+"&destination="+destination));
            startActivity(intent);
        }
        //Todo Handle người dùng từ chối cung cấp vị trí hiện tại
    }

    private interface DataHandle{
        void onMapReady();

    }
    private class FetchAddress extends AsyncTask<String,Void,LatLng> {
        Context mContext;

        public FetchAddress(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected LatLng doInBackground(String... strings) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            LatLng latLng = null;
            try {
                List<Address> addresses = geocoder.getFromLocationName(strings[0],1);
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();
                latLng = new LatLng(lat,lng);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return latLng;
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            super.onPostExecute(latLng);
        }
    }
}
