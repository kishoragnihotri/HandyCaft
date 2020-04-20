package com.kishor.agnihotri.handycaft.userfragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kishor.agnihotri.handycaft.R;
import com.kishor.agnihotri.handycaft.adapters.ProductWishRecyclerAdapter;
import com.kishor.agnihotri.handycaft.fragments.HomeFragment;
import com.kishor.agnihotri.handycaft.model.CartHandiCraft;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserOrdersFragment extends Fragment {


    private Toolbar toolbarOrderFragment;

    View view;


    public UserOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * This is override method to hide activity toolbar on onResume method
     */
    @Override
    public void onResume() {
        super.onResume();
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_orders, container, false);
        initViews();
        initListeners();
        initObjects();

        return view;
    }

    /**
     * This method is for initialization views
     */
    private void initViews() {
        toolbarOrderFragment = view.findViewById(R.id.toolbarOrderFragment);


    }


    /**
     * This method is for initialization Listeners
     */
    private void initListeners() {
        setUpToolbar();


    }


    /**
     * This method is for initialization Objects
     */
    private void initObjects() {
        setUpToolbar();
    }

    /**
     * This method is to setup Toolbar
     */
    private void setUpToolbar() {
        toolbarOrderFragment.setTitle("Orders");
        toolbarOrderFragment.setTitleTextColor(Color.WHITE);
        toolbarOrderFragment.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarOrderFragment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    /**
     * This is override method to show toolbar of activity
     */
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }

    /**
     * this implemented method is to listen the click on view
     *
     * @param v to get id of view
     */


}
