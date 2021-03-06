package com.kishor.agnihotri.handycaft.ProductFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.kishor.agnihotri.handycaft.R;
import com.kishor.agnihotri.handycaft.activities.LoginActivity;
import com.kishor.agnihotri.handycaft.adapters.ProductCartRecyclerAdapter;
import com.kishor.agnihotri.handycaft.fragments.HomeFragment;
import com.kishor.agnihotri.handycaft.model.CartHandiCraft;
import com.kishor.agnihotri.handycaft.userfragments.UserAddressFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCartFragment extends Fragment implements View.OnClickListener {

    private Toolbar toolbarFragmentCart;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private LinearLayout linearLayoutCart;
    private LinearLayout linearLayoutCartEmpty;

    private LinearLayout circleProgressBarLayout;
    private CircleProgressBar circleProgressBar;

    private DatabaseReference databaseReference;

    private List<CartHandiCraft> cartHandiCrafts;

    private TextView textViewItemCount;
    private TextView textViewTotalAmount;
    private TextView textViewTotalAmountPayable;

    private Button buttonTotalAmount;
    private Button buttonShopNow;
    private Button buttonContinueBuy;

    //To check user logged in or not
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email;
    String email_google;
    String first_name;
    String last_name;

    View view;
    String initial_price;
    int sumSp;

    public ProductCartFragment() {
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
        view = inflater.inflate(R.layout.fragment_product_cart, container, false);

        initViews();
        initListeners();
        initObjects();

        return view;
    }


    /**
     * This method ot initialize views
     */
    private void initViews() {

        toolbarFragmentCart = view.findViewById(R.id.toolbarFragmentCart);

        linearLayoutCart = view.findViewById(R.id.linearLayoutCart);
        linearLayoutCartEmpty = view.findViewById(R.id.linearLayoutCartEmpty);
        recyclerView = view.findViewById(R.id.recyclerViewShowCartItems);

        circleProgressBarLayout = view.findViewById(R.id.circleProgressBarLayout);
        circleProgressBar = view.findViewById(R.id.circleProgressBar);

        textViewItemCount = view.findViewById(R.id.textViewItemCount);
        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);
        textViewTotalAmountPayable = view.findViewById(R.id.textViewTotalAmountPayable);

        buttonTotalAmount = view.findViewById(R.id.buttonTotalAmount);
        buttonShopNow = view.findViewById(R.id.buttonShopNow);
        buttonContinueBuy = view.findViewById(R.id.buttonContinueBuy);
    }


    /**
     * This method ot initialize Listeners
     */
    private void initListeners() {
        buttonShopNow.setOnClickListener(this);
        buttonContinueBuy.setOnClickListener(this);
        buttonTotalAmount.setOnClickListener(this);
    }


    /**
     * This method ot initialize Objects
     */
    private void initObjects() {
        setUpToolbar();
        cartHandiCrafts = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("myEmailPass", Context.MODE_PRIVATE);

        circleProgressBar.setColorSchemeResources(R.color.colorPrimary);
        setUpRecyclerView();

        databaseReference = FirebaseDatabase.getInstance().getReference("Cart Items");

        getDataFromFirebase();

    }


    /**
     * This method shows toolbar
     */
    private void setUpToolbar() {
        toolbarFragmentCart.setTitle("Your cart");
        toolbarFragmentCart.setTitleTextColor(Color.WHITE);
        toolbarFragmentCart.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarFragmentCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    /**
     * This method to setup the recyclerView
     */
    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * This method to get data from Firebase Database
     */
    private void getDataFromFirebase() {
        circleProgressBarLayout.setVisibility(View.VISIBLE);
        circleProgressBar.setVisibility(View.VISIBLE);


        final List<String> updatedSpList = new ArrayList<String>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                circleProgressBarLayout.setVisibility(View.GONE);
                circleProgressBar.setVisibility(View.GONE);

                if (cartHandiCrafts != null && updatedSpList != null) {
                    cartHandiCrafts.clear();
                    updatedSpList.clear();
                }
                for (DataSnapshot postDataSnapshot : dataSnapshot.getChildren()) {
                    CartHandiCraft cartHandiCraft = postDataSnapshot.getValue(CartHandiCraft.class);
                    cartHandiCrafts.add(cartHandiCraft);
                    updatedSpList.add(cartHandiCraft.getProduct_sp());

                }

                sumSp = 0;
                for (int i = 0; i < updatedSpList.size(); i++) {
                    sumSp = sumSp + Integer.parseInt(updatedSpList.get(i));
                }
                adapter = new ProductCartRecyclerAdapter(getActivity(), cartHandiCrafts);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (cartHandiCrafts.size() == 0) {
                    linearLayoutCart.setVisibility(View.GONE);
                    linearLayoutCartEmpty.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutCartEmpty.setVisibility(View.GONE);
                    linearLayoutCart.setVisibility(View.VISIBLE);
                    textViewItemCount.setText("" + cartHandiCrafts.size());
                    textViewTotalAmount.setText("₹" + sumSp);
                    textViewTotalAmountPayable.setText("₹" + sumSp);
                    buttonTotalAmount.setText("₹" + sumSp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
    @Override
    public void onClick(View v) {

        if (v.getId() == buttonShopNow.getId()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new HomeFragment());
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.commit();
        } else if (v.getId() == buttonContinueBuy.getId()) {
            goToAddressOrLogin();
        }
    }


    /**
     * This method to go to Address Fragment
     * First verify user logged in or not
     * If user logged in go to address Fragment otherwise go to Login Activity
     */
    private void goToAddressOrLogin() {

        email = sharedPreferences.getString("email", null);
        email_google = sharedPreferences.getString("email_google", null);
        first_name = sharedPreferences.getString("facebook_first_name", null);
        last_name = sharedPreferences.getString("facebook_last_name", null);

        if (email != null || email_google != null || first_name != null || last_name != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new UserAddressFragment());
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
