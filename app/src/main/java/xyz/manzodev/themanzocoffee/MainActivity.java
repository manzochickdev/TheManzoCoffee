package xyz.manzodev.themanzocoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import xyz.manzodev.themanzocoffee.Models.User;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    User user;
    String userID;
    private static final String TAG = "OK";
    private static final int LOCATION_PICKER = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userID = getIntent().getStringExtra(getString(R.string.userID));
        passUid(userID);


    }

    private void passUid(String userID){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.userID),userID);
        editor.commit();
    }

    @Override
    public void passUser(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.user),user);
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        userInfoFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_container,userInfoFragment,getString(R.string.fragment_user_info_tag));
        fragmentTransaction.commit();
    }

    @Override
    public void inflateFragment(int selected) {
        switch (selected){
            case 1 :{
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.user),user);
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_container,homeFragment,getString(R.string.fragment_home_tag));
                fragmentTransaction.commit();
                break;}

            case 2: {
                OrderFragment orderFragment = new OrderFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_container,orderFragment,getString(R.string.fragment_order_tag));
                fragmentTransaction.commit();
                break;
            }

            case 3 :{
                PlaceFragment placeFragment = new PlaceFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_container,placeFragment,getString(R.string.fragment_place_tag));
                fragmentTransaction.commit();
                break;}
            case 4 : {
                AccountFragment accountFragment = new AccountFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_container,accountFragment,getString(R.string.fragment_account_tag));
                fragmentTransaction.commit();
                break;
            }

        }
    }

    @Override
    public void inflateNews(String url) {
        Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra(getString(R.string.Url),url);
        startActivity(intent);
    }

    @Override
    public void updateUser(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(getString(R.string.user)).child(userID).setValue(user);
    }

    @Override
    public void getPlace(String address) {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_place_tag)).getChildFragmentManager().findFragmentByTag(getString(R.string.fragment_map_tag));
        mapFragment.moveCamera(address);
    }

    @Override
    public void getProduct() {
        Intent intent = new Intent(this,OrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void pickLocation() {
        //todo handle no result back
        Intent intent = new Intent(this,LocationPickerActivity.class);
        startActivityForResult(intent,LOCATION_PICKER);
    }

    @Override
    public void getCurrentLocation() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_place_tag)).getChildFragmentManager().findFragmentById( R.id.fr_map);
        mapFragment.getCurrentPosition();
    }

    @Override
    public void getDirection(String destination) {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_place_tag)).getChildFragmentManager().findFragmentById( R.id.fr_map);
        mapFragment.getDirection(destination);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyMainActivity: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case LOCATION_PICKER :
                String s = data.getStringExtra("address");
                OrderFragment orderFragment = (OrderFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_order_tag));
                orderFragment.fragmentOrderBinding.setLocation(s);
                break;
        }
    }
}
