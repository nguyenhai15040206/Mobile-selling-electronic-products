package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CheckDataInput;
import com.example.banhangonline.Fragment.AccountFragment;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAcccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextInputLayout txtPasswordLayout, txtRePasswordLayout, txtNameCustomerLayout,
            txtPhoneNumberLayout, txtEmailLayout, txtUserNameLayout;
    private AppCompatAutoCompleteTextView txtNameCustomer, txtPhoneNumber, txtEmail, txtUserName,
            txtPassword, txtRePassword;
    private Button btnCreateAccount;
    private Customer customer;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createa_account);
        Mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validateNameCustomer();
        validatePhonnumber();
        ValidateEmailAddress();
        ValidateUserName();
        ValidatePassword();
        ValidateRePassword();
        btnCreateAccountClick();
    }

    public void btnCreateAccountClick() {
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmailValidateCustomer() && checkPasswordValidate() && checkRePasswordValidate() && checkUsernameValidate()
                        && checkValidateNameCustomer() && checkValidatePhoneNumberCustomer()) {

                    if (txtPassword.getText().toString().trim().equals(txtRePassword.getText().toString().trim())) {
                        txtRePasswordLayout.setErrorEnabled(false);
                        txtRePasswordLayout.setError(null);
                        customer = new Customer();
                        callApiGetCustomerByUserName(txtUserName.getText().toString().trim());
                    } else {
                        txtRePasswordLayout.setErrorEnabled(true);
                        txtRePasswordLayout.setError("Nhập lại mật khẩu không khớp");
                    }
                } else {
                    checkData();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                CustomIntent.customType(this, "right-to-left");
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void Mapping() {
        toolbar = findViewById(R.id.toolbarCreateAccount);
        txtNameCustomer = findViewById(R.id.tvHoTen);
        txtPhoneNumber = findViewById(R.id.tvSoDienThoai);
        txtEmail = findViewById(R.id.tvEmail);
        txtNameCustomerLayout = findViewById(R.id.tvHoTenLayout);
        txtPhoneNumberLayout = findViewById(R.id.tvSoDienThoaiLayout);
        txtEmailLayout = findViewById(R.id.tvEmailLayout);
        txtUserName = findViewById(R.id.tvTenDangNhap);
        txtUserNameLayout = findViewById(R.id.tvTenDangNhapLayout);
        txtPassword = findViewById(R.id.tvMatKhau);
        txtPasswordLayout = findViewById(R.id.tvMatKhauLayout);
        txtRePassword = findViewById(R.id.tvNhapLaiMatKhau);
        txtRePasswordLayout = findViewById(R.id.tvNhapLaiMatKhauLayout);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
    }


    public void validateNameCustomer() {
        txtNameCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidateNameCustomer()) {
                    txtNameCustomerLayout.setErrorEnabled(true);
                    txtNameCustomerLayout.setError("Họ tên không được để trống");
                } else {
                    txtNameCustomerLayout.setErrorEnabled(false);
                    txtNameCustomerLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkValidateNameCustomer() {
        if (TextUtils.isEmpty(txtNameCustomer.getText().toString().trim())) {
            return false;
        } else {
            return true;
        }
    }

    public void validatePhonnumber() {
        txtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidatePhoneNumberCustomer()) {
                    txtPhoneNumberLayout.setErrorEnabled(true);
                    txtPhoneNumberLayout.setError("Số điện thoại không hợp lệ!");
                } else {
                    txtPhoneNumberLayout.setErrorEnabled(false);
                    txtPhoneNumberLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkValidatePhoneNumberCustomer() {
        if (TextUtils.isEmpty(txtPhoneNumber.getText().toString().trim()) || !CheckDataInput.isPhoneNumber(txtPhoneNumber.getText().toString().trim())) {
            return false;
        } else {
            return true;
        }
    }

    public void ValidateEmailAddress() {
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkEmailValidateCustomer() || TextUtils.isEmpty(txtEmail.getText().toString().trim())) {
                    txtEmailLayout.setErrorEnabled(true);
                    txtEmailLayout.setError("Email không hợp lệ");
                } else {
                    txtEmailLayout.setErrorEnabled(false);
                    txtEmailLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkEmailValidateCustomer() {
        if (CheckDataInput.isEmail(txtEmail.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    public void ValidateUserName() {
        txtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkUsernameValidate()) {
                    txtUserNameLayout.setErrorEnabled(true);
                    txtUserNameLayout.setError("Tên đăng nhập từ 6-32 kí tự không bao gồm khoảng trống");
                } else {
                    txtUserNameLayout.setErrorEnabled(false);
                    txtUserNameLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkUsernameValidate() {
        if (!TextUtils.isEmpty(txtUserName.getText().toString().trim()) && CheckDataInput.isTextContainSpace(txtUserName.getText().toString().trim()) &&
                (txtUserName.getText().toString().trim().length() >= 6 && txtUserName.getText().toString().trim().length() <= 32)) {
            return true;
        } else {
            return false;
        }
    }

    public void ValidatePassword() {
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkPasswordValidate()) {
                    txtPasswordLayout.setErrorEnabled(true);
                    txtPasswordLayout.setError("Mật khẩu từ 6-32 kí tự không bao gồm khoảng trống");
                } else {
                    txtPasswordLayout.setErrorEnabled(false);
                    txtPasswordLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkPasswordValidate() {
        if (!TextUtils.isEmpty(txtPassword.getText().toString().trim()) && CheckDataInput.isTextContainSpace(txtPassword.getText().toString().trim()) &&
                (txtPassword.getText().toString().trim().length() >= 6 && txtPassword.getText().toString().trim().length() <= 32)) {
            return true;
        } else {
            return false;
        }
    }

    public void ValidateRePassword() {
        txtRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkRePasswordValidate()) {
                    txtRePasswordLayout.setErrorEnabled(true);
                    txtRePasswordLayout.setError("Mật khẩu từ 6-32 kí tự không bao gồm khoảng trống");
                } else {
                    txtRePasswordLayout.setErrorEnabled(false);
                    txtRePasswordLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkRePasswordValidate() {
        if (!TextUtils.isEmpty(txtRePassword.getText().toString().trim()) && CheckDataInput.isTextContainSpace(txtRePassword.getText().toString().trim()) &&
                (txtRePassword.getText().toString().trim().length() >= 6 && txtRePassword.getText().toString().trim().length() <= 32)) {
            return true;
        } else {
            return false;
        }
    }


    public void checkData() {
        if (!checkValidateNameCustomer()) {
            txtNameCustomerLayout.setErrorEnabled(true);
            txtNameCustomerLayout.setError("Họ tên không được để trống");
        } else {
            txtNameCustomerLayout.setErrorEnabled(false);
            txtNameCustomerLayout.setError(null);
        }
        if (!checkValidatePhoneNumberCustomer()) {
            txtPhoneNumberLayout.setErrorEnabled(true);
            txtPhoneNumberLayout.setError("Số điện thoại không hợp lệ!");
        } else {
            txtPhoneNumberLayout.setErrorEnabled(false);
            txtPhoneNumberLayout.setError(null);
        }
        if (!checkEmailValidateCustomer() || TextUtils.isEmpty(txtEmail.getText().toString().trim())) {
            txtEmailLayout.setErrorEnabled(true);
            txtEmailLayout.setError("Email không hợp lệ");
        } else {
            txtEmailLayout.setErrorEnabled(false);
            txtEmailLayout.setError("");
        }
        if (!checkUsernameValidate()) {
            txtUserNameLayout.setErrorEnabled(true);
            txtUserNameLayout.setError("Tên đăng nhập từ 6-32 kí tự không bao gồm khoảng trống");
        } else {
            txtUserNameLayout.setErrorEnabled(false);
            txtUserNameLayout.setError("");
        }
        if (!checkPasswordValidate()) {
            txtPasswordLayout.setErrorEnabled(true);
            txtPasswordLayout.setError("Mật khẩu từ 6-32 kí tự không bao gồm khoảng trống");
        } else {
            txtPasswordLayout.setErrorEnabled(false);
            txtPasswordLayout.setError("");
        }
        if (!checkRePasswordValidate()) {
            txtRePasswordLayout.setErrorEnabled(true);
            txtRePasswordLayout.setError("Mật khẩu từ 6-32 kí tự không bao gồm khoảng trống");
        } else {
            txtRePasswordLayout.setErrorEnabled(false);
            txtRePasswordLayout.setError("");
        }
    }

    public void callApiGetCustomerByUserName(String userName) {
        ApiService.apiService.getCustomerByUserName(userName).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    customer = new Customer(response.body().getCustomerCode(), response.body().getCustomerName(), response.body().getCustomerPhoneNumber(),
                            response.body().getCustomerEmail(), response.body().getCustomerAdress(), response.body().getCutomerUserName(), response.body().getCustomerPassword());
                    if (customer != null) {
                        txtUserNameLayout.setErrorEnabled(true);
                        txtUserNameLayout.setError("Tên đăng nhập đã tồn tại!");
                    }
                } else {
                    txtUserNameLayout.setErrorEnabled(false);
                    txtUserNameLayout.setError(null);
                    postCustomer();
                    Intent intent = new Intent(CreateAcccountActivity.this, LoginActivity.class);
                    intent.putExtra("userName", txtUserName.getText().toString().trim());
                    intent.putExtra("password", txtPassword.getText().toString().trim());
                    startActivity(intent);
                    CustomIntent.customType(CreateAcccountActivity.this, "right-to-left");
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                //Toast.makeText(CreateAcccountActivity.this, "Err", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postCustomer() {
        Customer customer1 = new Customer(0, txtNameCustomer.getText().toString().trim(), txtPhoneNumber.getText().toString().trim(),
                txtEmail.getText().toString().trim(), null, txtUserName.getText().toString().trim(), txtPassword.getText().toString().trim());
        ApiService.apiService.postCustomer(customer1).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateAcccountActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }
}