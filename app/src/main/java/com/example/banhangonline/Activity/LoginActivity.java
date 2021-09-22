package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Fragment.AccountFragment;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.R;
import com.google.android.material.textfield.TextInputEditText;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText edtUserName, edtPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private TextView tvCreateAccount;
    public static final String DATA_LOGIN = "DATA_LOGIN";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbarLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent intent = getIntent();
        String username = intent.getStringExtra("userName");
        String pass = intent.getStringExtra("password");
        edtPassword.setText(pass);
        edtUserName.setText(username);
        //
        //HomeFragment.customer = new Customer();
        btnLogin.setOnClickListener(onClickListener);
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAcccountActivity.class);
                startActivity(intent);
                CustomIntent.customType(LoginActivity.this, "left-to-right");
            }
        });
        
    }

    // sự kiện khi login
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(edtUserName.getText().toString())||TextUtils.isEmpty(edtPassword.getText().toString()))
            {
                Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                callApiCustomer_Login(edtUserName.getText().toString(), edtPassword.getText().toString());
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    if (HomeFragment.customer != null) {
                        AccountFragment.cardView.setVisibility(View.VISIBLE);
                        AccountFragment.linearLayout_NoAccount.setVisibility(View.GONE);
                        AccountFragment.tvNameCustomerLogin.setVisibility(View.VISIBLE);
                        AccountFragment.tvPhoneNumberCustomer.setVisibility(View.VISIBLE);
                        AccountFragment.tvNameCustomerLogin.setText("" + HomeFragment.customer.getCustomerName());
                        AccountFragment.tvPhoneNumberCustomer.setText("" + HomeFragment.customer.getCustomerPhoneNumber());
                    } else {
                        AccountFragment.linearLayout_NoAccount.setVisibility(View.VISIBLE);
                        AccountFragment.tvNameCustomerLogin.setVisibility(View.GONE);
                        AccountFragment.tvPhoneNumberCustomer.setVisibility(View.GONE);
                        AccountFragment.cardView.setVisibility(View.GONE);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                finish();
                CustomIntent.customType(LoginActivity.this, "right-to-left");

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // call API Customer
    public void callApiCustomer_Login(String userName, String passWord) {
        ApiService.apiService.getDataLogin(userName, passWord).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.body() !=null && response.isSuccessful())
                {
                    HomeFragment.customer = new Customer(response.body().getCustomerCode(),response.body().getCustomerName(),response.body().getCustomerPhoneNumber(),
                            response.body().getCustomerEmail(),response.body().getCustomerAdress(),response.body().getCutomerUserName(),response.body().getCustomerPassword());
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    sharedPreferences = getSharedPreferences(DATA_LOGIN,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName",userName);
                    editor.putString("password",passWord);
                    editor.commit();
                    AccountFragment.cardView.setVisibility(View.VISIBLE);
                    AccountFragment.linearLayout_NoAccount.setVisibility(View.GONE);
                    AccountFragment.tvNameCustomerLogin.setVisibility(View.VISIBLE);
                    AccountFragment.tvPhoneNumberCustomer.setVisibility(View.VISIBLE);
                    AccountFragment.tvNameCustomerLogin.setText(""+HomeFragment.customer.getCustomerName());
                    AccountFragment.tvPhoneNumberCustomer.setText(""+HomeFragment.customer.getCustomerPhoneNumber());
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(LoginActivity.this, "right-to-left");
                }
                else
                {
                    HomeFragment.customer = null;
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(LoginActivity.this,"right-to-left");
    }

}



