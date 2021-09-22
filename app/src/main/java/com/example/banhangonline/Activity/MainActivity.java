package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Fragment.AccountFragment;
import com.example.banhangonline.Fragment.CategoryFragment;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Fragment.SaleFragment;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView navView;
    RelativeLayout relativeLayout;
    EditText searchView;
    ImageView cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cart= findViewById(R.id.Cart);
        navView = findViewById(R.id.nav_view);
        relativeLayout = findViewById(R.id.relative);
        searchView = findViewById(R.id.search_view);
        LoadFragment(new HomeFragment());

        //BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

        searchView.setOnClickListener(this);
        cart.setOnClickListener(this);
    }

    // call API Customer

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    fragment= new HomeFragment();
                    LoadFragment(fragment);
                    relativeLayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_flashsale:
                    fragment= new SaleFragment();
                    LoadFragment(fragment);
                    relativeLayout.setVisibility(View.VISIBLE);
                    return  true;
                case R.id.navigation_category:
                    fragment = new CategoryFragment();
                    LoadFragment(fragment);
                    relativeLayout.setVisibility(View.VISIBLE);
                    return  true;
                case R.id.navigation_account:
                    fragment = new AccountFragment();
                    LoadFragment(fragment);
                    relativeLayout.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };
    private void LoadFragment(Fragment fragment)
    {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(navView.getSelectedItemId() ==R.id.navigation_home)
        {
            finish();
            super.onBackPressed();
        }
        else{
            if(navView.getSelectedItemId()== R.id.navigation_category){
                navView.setSelectedItemId(R.id.navigation_home);
            }
            if(navView.getSelectedItemId() == R.id.navigation_flashsale)
            {
                navView.setSelectedItemId(R.id.navigation_category);
            }
            if(navView.getSelectedItemId() == R.id.navigation_account)
            {
                navView.setSelectedItemId(R.id.navigation_flashsale);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Cart:
                if(HomeFragment.customer != null) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(MainActivity.this, "left-to-right");
                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(MainActivity.this, "left-to-right");
                }
                break;
            case R.id.search_view:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                CustomIntent.customType(MainActivity.this, "left-to-right");
                break;
        }
    }
}