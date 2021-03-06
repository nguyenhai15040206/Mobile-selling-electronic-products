package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CheckDataInput;
import com.example.banhangonline.Fragment.AccountFragment;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfomationCustomerActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private CheckBox ckbChangePass;
    private LinearLayout linearLayoutChangPass;
    private TextInputLayout txtPasswordOldLayout, txtPasswordNewLayout, txtRePasswordNewLayout, txtNameCustomerUpdateLayout,
            txtPhoneNumberUpdateLayout, txtEmailUpdateLayout, txtCustomerAddressUpdateLayout;
    private TextInputEditText txtUserNameUpdate;
    private Button btnUpdateCusotmer;
    private SharedPreferences sharedPreferences;

    private AppCompatAutoCompleteTextView txtNameCustomerUpdate, txtPhoneNumberUpdate, txtEmailUpdate, txtCustomerAddressUpdate,
            txtPasswordOld, txtPasswordNew, txtRePasswordNew;


    //
    private int customerCode = 0;
    private String customerName = null, customerPhoneNumber = null,
            customerEmail = null, customerAddress = null, customerUserName = null;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_customer);
        mapping();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //
        receiveData_Intent();
        setText();
        // s??? ki???n
        ckbChangePass.setChecked(false);
        linearLayoutChangPass.setVisibility(View.GONE);
        ckbChangePass.setOnClickListener(this);
        btnUpdateCusotmer.setOnClickListener(this);

        validateNameCustomer();
        validatePhonnumber();
        ValidateEmailAddress();
        validatePasswordOld();
        validateRePasswordNew();
        validatePasswordNew();
        //
    }

    // ??nh x???
    public void mapping() {
        toolbar = findViewById(R.id.toolbarInfomationCustomer);
        ckbChangePass = findViewById(R.id.ckbChangePass);
        linearLayoutChangPass = findViewById(R.id.linearLayoutChangPass);

        txtEmailUpdate = findViewById(R.id.txtEmailUpdate);
        txtCustomerAddressUpdate = findViewById(R.id.txtCustomerAddressUpdate);
        txtUserNameUpdate = findViewById(R.id.txtUserNameUpdate);
        btnUpdateCusotmer = findViewById(R.id.btnUpdateCusotmer);

        //
        txtNameCustomerUpdate = findViewById(R.id.txtNameCustomerUpdate);
        txtPhoneNumberUpdate = findViewById(R.id.txtPhoneNumberUpdate);
        txtPasswordOld = findViewById(R.id.txtPasswordOld);
        txtPasswordNew = findViewById(R.id.txtPasswordNew);
        txtRePasswordNew = findViewById(R.id.txtRePasswordNew);
        txtPasswordOldLayout = findViewById(R.id.txtPasswordOldLayout);
        txtPasswordNewLayout = findViewById(R.id.txtPasswordNewLayout);
        txtRePasswordNewLayout = findViewById(R.id.txtRePasswordNewLayout);
        txtPhoneNumberUpdateLayout = findViewById(R.id.txtPhoneNumberUpdateLayout);
        txtNameCustomerUpdateLayout = findViewById(R.id.txtNameCustomerUpdateLayout);
        txtEmailUpdateLayout = findViewById(R.id.txtEmailUpdateLayout);


    }

    public void validatePasswordOld() {
        txtPasswordOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidatePasswordOld()) {
                    txtPasswordOldLayout.setErrorEnabled(true);
                    txtPasswordOldLayout.setError("M???t kh???u t??? 6 ?????n 32 k?? t???, kh??ng c?? kho???ng tr???ng");
                } else {
                    txtPasswordOldLayout.setErrorEnabled(false);
                    txtPasswordOldLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void validatePasswordNew() {
        txtPasswordNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidatePasswordNew()) {
                    txtPasswordNewLayout.setErrorEnabled(true);
                    txtPasswordNewLayout.setError("M???t kh???u t??? 6 ?????n 32 k?? t???, kh??ng c?? kho???ng tr???ng");
                } else {
                    txtPasswordNewLayout.setErrorEnabled(false);
                    txtPasswordNewLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void validateRePasswordNew() {
        txtRePasswordNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidateRepasswordNew()) {
                    txtRePasswordNewLayout.setErrorEnabled(true);
                    txtRePasswordNewLayout.setError("M???t kh???u t??? 6 ?????n 32 k?? t???, kh??ng c?? kho???ng tr???ng");
                } else {
                    txtRePasswordNewLayout.setErrorEnabled(false);
                    txtRePasswordNewLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // ki???m tra h???p l??? c???a m???t kh???u c???
    public boolean checkValidatePasswordOld() {
        if (TextUtils.isEmpty(txtPasswordOldLayout.getEditText().getText().toString().trim()) ||
                !CheckDataInput.isTextContainSpace(txtPasswordOldLayout.getEditText().getText().toString()) ||
                (txtPasswordOldLayout.getEditText().getText().toString().trim().length() < 6 || txtPasswordOldLayout.getEditText().getText().toString().trim().length() > 32)) {
            return false;
        } else {
            return true;
        }
    }

    // ki???m tra h???p l??? c???a m???t kh???u m???i
    public boolean checkValidatePasswordNew() {
        if (TextUtils.isEmpty(txtPasswordNewLayout.getEditText().getText().toString().trim()) ||
                !CheckDataInput.isTextContainSpace(txtPasswordNewLayout.getEditText().getText().toString()) ||
                (txtPasswordNewLayout.getEditText().getText().toString().trim().length() < 6 || txtPasswordNewLayout.getEditText().getText().toString().trim().length() > 32)) {
            return false;
        } else {
            return true;
        }
    }

    // ki???m tra h???p l??? c???a nh???p l???i m???t kh???u
    public boolean checkValidateRepasswordNew() {
        if (TextUtils.isEmpty(txtRePasswordNewLayout.getEditText().getText().toString().trim()) ||
                !CheckDataInput.isTextContainSpace(txtRePasswordNewLayout.getEditText().getText().toString()) ||
                (txtRePasswordNewLayout.getEditText().getText().toString().trim().length() < 6 || txtRePasswordNewLayout.getEditText().getText().toString().trim().length() > 32)) {
            return false;
        } else {
            return true;
        }
    }

    public void validateNameCustomer() {
        txtNameCustomerUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidateNameCustomer()) {
                    txtNameCustomerUpdateLayout.setErrorEnabled(true);
                    txtNameCustomerUpdateLayout.setError("H??? t??n kh??ng ???????c ????? tr???ng");
                } else {
                    txtNameCustomerUpdateLayout.setErrorEnabled(false);
                    txtNameCustomerUpdateLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkValidateNameCustomer() {
        if (TextUtils.isEmpty(txtNameCustomerUpdate.getText().toString().trim())) {
            return false;
        } else {
            return true;
        }
    }

    public void validatePhonnumber() {
        txtPhoneNumberUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidatePhoneNumberCustomer()) {
                    txtPhoneNumberUpdateLayout.setErrorEnabled(true);
                    txtPhoneNumberUpdateLayout.setError("S??? ??i???n tho???i kh??ng h???p l???!");
                } else {
                    txtPhoneNumberUpdateLayout.setErrorEnabled(false);
                    txtPhoneNumberUpdateLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkValidatePhoneNumberCustomer() {
        if (TextUtils.isEmpty(txtPhoneNumberUpdate.getText().toString().trim()) || !CheckDataInput.isPhoneNumber(txtPhoneNumberUpdate.getText().toString().trim())) {
            return false;
        } else {
            return true;
        }
    }

    public void ValidateEmailAddress() {
        txtEmailUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(txtEmailUpdate.getText().toString().trim())) {
                    txtEmailUpdate.setHint("n??n s??? d???ng email ^^");
                    txtEmailUpdateLayout.setErrorEnabled(false);
                    txtEmailUpdateLayout.setError("");
                } else {
                    txtEmailUpdate.setHint(null);
                    if (!checkEmailValidateCustomer()) {
                        txtEmailUpdateLayout.setErrorEnabled(true);
                        txtEmailUpdateLayout.setError("Email kh??ng h???p l???");
                    } else {
                        txtEmailUpdateLayout.setErrorEnabled(false);
                        txtEmailUpdateLayout.setError("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkEmailValidateCustomer() {
        if (CheckDataInput.isEmail(txtEmailUpdate.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }


    // g??n d??? li???u cho t???ng textInput
    public void setText() {
        txtNameCustomerUpdate.setText(customerName);
        txtPhoneNumberUpdate.setText(customerPhoneNumber);
        if (TextUtils.isEmpty(customerEmail)) {
            txtEmailUpdate.setHint("n??n s??? d???ng email ^^");
            txtEmailUpdate.setText("");
        } else {
            txtEmailUpdate.setHint(null);
            txtEmailUpdate.setText(customerEmail);
        }
        txtCustomerAddressUpdate.setText(customerAddress);
        txtUserNameUpdate.setText(customerUserName);
    }


    // nh???n d??? li???u tu Intent
    public void receiveData_Intent() {
        Intent intent = getIntent();
        customerCode = intent.getIntExtra("CustomerCode", 0);
        customerName = intent.getStringExtra("CustomerName");
        customerPhoneNumber = intent.getStringExtra("CustomerPhoneNumber");
        customerEmail = intent.getStringExtra("CusomerEmail");
        customerAddress = intent.getStringExtra("CustomerAddress");
        customerUserName = intent.getStringExtra("CustomerUsername");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sharedPreferences = getSharedPreferences(LoginActivity.DATA_LOGIN, Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("userName","");
                String passWord = sharedPreferences.getString("password","");
                callApiCustomer_Login(userName,passWord);
                AccountFragment.tvNameCustomerLogin.setText(""+HomeFragment.customer.getCustomerName().toString().trim());
                AccountFragment.tvPhoneNumberCustomer.setText(""+HomeFragment.customer.getCustomerPhoneNumber().toString().trim());
                finish();
                CustomIntent.customType(this, "right-to-left");
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ckbChangePass:
                if (ckbChangePass.isChecked()) {
                    linearLayoutChangPass.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutChangPass.setVisibility(View.GONE);
                    txtPasswordNew.getText().clear();
                    txtPasswordOld.getText().clear();
                    txtRePasswordNew.getText().clear();
                    txtPasswordOldLayout.setErrorEnabled(false);
                    txtPasswordOldLayout.setError("");
                    txtPasswordNewLayout.setErrorEnabled(false);
                    txtPasswordNewLayout.setError("");
                    txtRePasswordNewLayout.setErrorEnabled(false);
                    txtRePasswordNewLayout.setError("");
                }
                break;
            case R.id.btnUpdateCusotmer:
                if (!checkValidateNameCustomer()) {
                    return;
                } else {
                    if (!checkValidatePhoneNumberCustomer()) {
                        return;
                    } else {
                        if (TextUtils.isEmpty(txtUserNameUpdate.getText().toString().trim())) {
                            Toast.makeText(InfomationCustomerActivity.this, "T??n ????ng nh???p kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                        } else {
                            Customer customer = HomeFragment.customer;
                            customer.setCustomerName(txtNameCustomerUpdate.getText().toString().trim());
                            customer.setCustomerPhoneNumber(txtPhoneNumberUpdate.getText().toString().trim());
                            if (TextUtils.isEmpty(txtEmailUpdate.getText().toString().trim())) {
                                customer.setCustomerEmail(customer.getCustomerEmail());
                            } else {
                                if (checkEmailValidateCustomer()) {
                                    customer.setCustomerEmail(txtEmailUpdate.getText().toString().trim());
                                } else {
                                    return;
                                }
                            }
                            customer.setCustomerAdress(txtCustomerAddressUpdate.getText().toString().trim());
                            customer.setCutomerUserName(customer.getCutomerUserName());
                            checkBoxIsChecked(customer);
                        }
                    }
                }
                break;
        }
    }

    // kkieemr tra n???u checkbox ?????i m???t kh???u checked... Update customer
    public void checkBoxIsChecked(Customer customer){
        if (ckbChangePass.isChecked()) {
            if (!checkValidatePasswordOld()) {
                return;
            } else {
                if (CheckDataInput.getMD5(txtPasswordOldLayout.getEditText().getText().toString().trim()).equals(customer.getCustomerPassword())) {
                    txtPasswordOldLayout.setErrorEnabled(false);
                    txtPasswordOldLayout.setError(null);
                    if (!checkValidatePasswordNew()) {
                        return;
                    } else {
                        if (!checkValidateRepasswordNew()) {
                            return;
                        } else {
                            if (txtPasswordNewLayout.getEditText().getText().toString().trim().equals(txtRePasswordNewLayout.getEditText().getText().toString().trim())) {
                                customer.setCustomerPassword(CheckDataInput.getMD5(txtPasswordNewLayout.getEditText().getText().toString().trim()));
                                updateCutomer(customerCode, customer);
                                sharedPreferences = getSharedPreferences(LoginActivity.DATA_LOGIN, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("userName");
                                editor.remove("password");
                                editor.commit();
                                editor.putString("userName",customer.getCutomerUserName());
                                editor.putString("password",txtPasswordNew.getText().toString().trim());
                                editor.commit();
                            } else {
                                txtRePasswordNewLayout.setErrorEnabled(true);
                                txtRePasswordNewLayout.setError("Nh???p l???i m???t kh???u kh??ng kh???p");
                                return;
                            }
                        }
                    }
                } else {
                    txtPasswordOldLayout.setErrorEnabled(true);
                    txtPasswordOldLayout.setError("M???t kh???u c??? kh??ng kh???p! vui l??ng ki???m tra l???i");
                    return;
                }
            }
        } else {
            customer.setCustomerPassword(customer.getCustomerPassword());
            updateCutomer(customerCode, customer);
        }
    }


    // c???p nh???t th??ng tin kh??ch h??ng
    public void updateCutomer(int cutomerCode, Customer customer) {
        ApiService.apiService.putCustomer(cutomerCode, customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                HomeFragment.customer = response.body();
                if (customer != null && response.isSuccessful()) {
                    Toast.makeText(InfomationCustomerActivity.this, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                    sharedPreferences = getSharedPreferences(LoginActivity.DATA_LOGIN, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", HomeFragment.customer.getCutomerUserName());
                    editor.putString("password", HomeFragment.customer.getCustomerPassword());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
            }
        });
    }

    public void callApiCustomer_Login(String userName, String passWord) {
        ApiService.apiService.getDataLogin(userName, passWord).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.body() !=null && response.isSuccessful())
                {
                    HomeFragment.customer = new Customer(response.body().getCustomerCode(),response.body().getCustomerName(),response.body().getCustomerPhoneNumber(),
                            response.body().getCustomerEmail(),response.body().getCustomerAdress(),response.body().getCutomerUserName(),response.body().getCustomerPassword());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedPreferences = getSharedPreferences(LoginActivity.DATA_LOGIN, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        String passWord = sharedPreferences.getString("password","");
        callApiCustomer_Login(userName,passWord);
        AccountFragment.tvNameCustomerLogin.setText(""+HomeFragment.customer.getCustomerName().toString().trim());
        AccountFragment.tvPhoneNumberCustomer.setText(""+HomeFragment.customer.getCustomerPhoneNumber().toString().trim());
        finish();
        CustomIntent.customType(InfomationCustomerActivity.this, "right-to-left");
    }
}