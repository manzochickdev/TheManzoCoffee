package xyz.manzodev.themanzocoffee;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import xyz.manzodev.themanzocoffee.Interface.IMapPickerActivity;
import xyz.manzodev.themanzocoffee.databinding.ActivityLocationPickerBinding;

public class LocationPickerActivity extends AppCompatActivity implements IMapPickerActivity{

    ActivityLocationPickerBinding activityLocationPickerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLocationPickerBinding = DataBindingUtil.setContentView(this,R.layout.activity_location_picker);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                onAddressBack(place.getAddress().toString());
            }

            @Override
            public void onError(Status status) {

            }
        });

        activityLocationPickerBinding.mapPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapPickerFragment mapPickerFragment = new MapPickerFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_container,mapPickerFragment,getString(R.string.fragment_map_picker_tag));
                fragmentTransaction.addToBackStack(getString(R.string.fragment_map_picker_tag));
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void getAddress(String address) {
        MapPickerFragment mapPickerFragment = (MapPickerFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_map_picker_tag));
        mapPickerFragment.fragmentMapPickerBinding.setLocation(address);
    }

    @Override
    public void onAddressBack(String address) {
        Intent intent = new Intent();
        intent.putExtra("address",address);
        setResult(RESULT_OK,intent);
        finish();
    }
}
