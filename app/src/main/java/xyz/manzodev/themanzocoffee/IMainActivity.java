package xyz.manzodev.themanzocoffee;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import xyz.manzodev.themanzocoffee.Models.User;

public interface IMainActivity {
    void passUser(User user);
    void inflateFragment(int selected);
    void inflateNews(String url);
    void updateUser(User user);
    void getPlace(String address);
    void getProduct();
    void pickLocation();
    void getCurrentLocation();
    void getDirection(String destination);

}
