package com.kishor.agnihotri.handycaft.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kishor.agnihotri.handycaft.R;

/*
 */
public class Address_Map extends FragmentActivity implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GoogleMap map;

public Address_Map(){

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Address_Map.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_address__map);

        SupportMapFragment  mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng Delhi=new LatLng(28.6031313,77.098911);
        map.addMarker(new MarkerOptions().position(Delhi).title("Sagarpur"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Delhi));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
