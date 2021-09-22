package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CheckDataInput;
import com.example.banhangonline.CustomAdapter.AprioriAdapter;
import com.example.banhangonline.CustomAdapter.CartAdapter;
import com.example.banhangonline.Database.DataBaseHelper;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.CartModel;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.Model.DetailInvoice;
import com.example.banhangonline.Model.Invoice;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    public static LinearLayout layoutSumMoeny, layoutChoseAllProduct;
    public static List<CartModel> cartModelList;
    private DataBaseHelper dataBaseHelper;
    private RecyclerView recyclerView;
    public static CartAdapter cartAdapter;
    public static LinearLayout linearLayout_NoProducts;
    public static BottomNavigationView bottomNavigationView;
    public static TextView tvCountProductCart;
    private AppCompatButton btnContinueBuyProducts;
    public AppCompatButton btnPayCart;
    public static CheckBox ckbChoseProductAll;
    public static TextView tvSumMoney, tvRemoveAllProductsCart;
    public static List<CartModel> listProductsSelect;
    public static List<Product> listProductByCode, listProductDoApriori;
    public static AprioriAdapter aprioriAdapter;
    public RecyclerView rcvDoApriori;
    public static LinearLayout layoutByDoApriori;


    // thanh toán
    private TextInputLayout txtNameCustomerLayout, txtPhoneNumberLayout, txtEmailLayout, txtCustomerAddressLayout;
    private AppCompatAutoCompleteTextView txtNameCustomer, txtPhoneNumber, txtCustomerEmail, txtCustomerAddress;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //
        listProductsSelect = new ArrayList<>();
        listProductByCode = new ArrayList<>();
        listProductDoApriori = new ArrayList<>();
        CartItem();
        tvCountProductCart.setText("(" + cartModelList.size() + " sản phẩm)");
        btnContinueBuyProducts.setOnClickListener(this);

        btnPayCart.setOnClickListener(this);
        DoApriori();
    }

    public void Mapping() {
        toolbar = findViewById(R.id.toolbar);
        linearLayout_NoProducts = findViewById(R.id.linearLayout_NoProducts);
        layoutSumMoeny = findViewById(R.id.linearLayoutSumMoney);
        bottomNavigationView = findViewById(R.id.nav_muahang);
        tvCountProductCart = findViewById(R.id.countProductCart);
        layoutChoseAllProduct = findViewById(R.id.layoutChoseAllProduct);
        btnContinueBuyProducts = findViewById(R.id.btnContinueBuyProducts);
        ckbChoseProductAll = findViewById(R.id.ckbChoseProductAll);
        tvSumMoney = findViewById(R.id.txtTongTien);
        tvRemoveAllProductsCart = findViewById(R.id.tvRemoveAllProductsCart);
        btnPayCart = findViewById(R.id.btnPayCart);
        rcvDoApriori = findViewById(R.id.rcvProductDoApriori);
        layoutByDoApriori = findViewById(R.id.layoutByDoApriori);

    }

    private void openInfomationPaymentCart(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_paycart_custom);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        // bắt sự kiện khi người dùng chạm vào màn hình bên ngoài thì out window
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        AppCompatButton btnYesPaymnet = dialog.findViewById(R.id.btnYesPayment);
        ImageView btnCloseWindow = dialog.findViewById(R.id.btncloseWindow);
        txtCustomerAddressLayout = dialog.findViewById(R.id.txtCustomerAddressCartLayout);
        txtPhoneNumberLayout = dialog.findViewById(R.id.txtPhoneNumberCartLayout);
        txtEmailLayout = dialog.findViewById(R.id.txtEmailCartLayout);
        txtNameCustomerLayout = dialog.findViewById(R.id.txtNameCustomerCartLayout);
        txtNameCustomer = dialog.findViewById(R.id.txtNameCustomerCart);
        txtPhoneNumber = dialog.findViewById(R.id.txtPhoneNumberCart);
        txtCustomerEmail = dialog.findViewById(R.id.txtEmailCart);
        txtCustomerAddress = dialog.findViewById(R.id.txtCustomerAddressCart);
        validateNameCustomer();
        validatePhonnumber();
        ValidateEmailAddress();
        ValidateAddress();
        setTextDialog();
        btnCloseWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYesPaymnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidateNameCustomer() && checkValidatePhoneNumberCustomer() && checkEmailValidateCustomer() && checkAdressValidateCustomer()) {
                    Customer customer = HomeFragment.customer;
                    updateCutomer(HomeFragment.customer.getCustomerCode(), customer);
                    customer.setCustomerName(txtNameCustomer.getText().toString().trim());
                    customer.setCustomerPhoneNumber(txtPhoneNumber.getText().toString().trim());
                    customer.setCustomerEmail(txtCustomerEmail.getText().toString().trim());
                    customer.setCustomerAdress(txtCustomerAddress.getText().toString().trim());
                    updateCutomer(HomeFragment.customer.getCustomerCode(), customer);
                    sendInvice();
                    dialog.dismiss();
                }
                //Toast.makeText(CartActivity.this,"Có:" + listProductsSelect.size()+ "SP",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    // cập nhật thông tin khách hàng
    public void updateCutomer(int cutomerCode, Customer customer) {
        ApiService.apiService.putCustomer(cutomerCode, customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                HomeFragment.customer = response.body();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
            }
        });
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
        txtCustomerEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkEmailValidateCustomer() || TextUtils.isEmpty(txtCustomerEmail.getText().toString().trim())) {
                    txtEmailLayout.setErrorEnabled(true);
                    txtEmailLayout.setError("Vui lòng nhập địa chỉ email hợp lệ!");
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
        if (CheckDataInput.isEmail(txtCustomerEmail.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    public void ValidateAddress() {
        txtCustomerAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkAdressValidateCustomer()) {
                    txtCustomerAddressLayout.setErrorEnabled(true);
                    txtCustomerAddressLayout.setError("Vui lòng nhập địa chỉ giao hàng");
                } else {
                    txtCustomerAddressLayout.setErrorEnabled(false);
                    txtCustomerAddressLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean checkAdressValidateCustomer() {
        if (!TextUtils.isEmpty(txtCustomerAddress.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }


    public void setTextDialog() {
        txtCustomerEmail.setText(HomeFragment.customer.getCustomerEmail());
        txtNameCustomer.setText(HomeFragment.customer.getCustomerName());
        if (HomeFragment.customer.getCustomerAdress().isEmpty()) {
            txtCustomerAddress.setText("");
        } else {
            txtCustomerAddress.setText(HomeFragment.customer.getCustomerAdress());
        }
        txtPhoneNumber.setText(HomeFragment.customer.getCustomerPhoneNumber());
    }

    public void CartItem() {
        dataBaseHelper = new DataBaseHelper(this);
        cartModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.rcvCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
        for (CartModel product : cartModelList) {
            callApiProductByProductCode(product.productCode);
        }
        cartAdapter = new CartAdapter(this);
        if (cartModelList.isEmpty()) {
            layoutChoseAllProduct.setVisibility(View.GONE);
            layoutSumMoeny.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
            linearLayout_NoProducts.setVisibility(View.VISIBLE);
        } else {
            layoutChoseAllProduct.setVisibility(View.VISIBLE);
            linearLayout_NoProducts.setVisibility(View.GONE);
            layoutSumMoeny.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.botton_nav_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                CustomIntent.customType(CartActivity.this, "right-to-left");
                return true;
            case R.id.chatCart:
                Intent intent = new Intent(CartActivity.this, ProductsByCategoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(CartActivity.this, "right-to-left");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinueBuyProducts:
                finish();
                CustomIntent.customType(CartActivity.this, "right-to-left");
                break;
            case R.id.btnPayCart:
                if (listProductsSelect.size() != 0) {
                    openInfomationPaymentCart(Gravity.CENTER);
                } else {
                    return;
                }
                break;
        }
    }

    // thêm hóa đơn
    public void sendInvice() {
        double tienBan = 0.0;
        double giamGia = 0.0;
        List<CartModel> listTmp = new ArrayList<>();
        for (int i = 0; i < listProductsSelect.size(); i++) {
            tienBan += listProductsSelect.get(i).productUnitPrice * listProductsSelect.get(i).productAmount;
            giamGia += listProductsSelect.get(i).productSale * listProductsSelect.get(i).productAmount;
        }
        double sumMoney = tienBan - giamGia;
        Invoice invoice = new Invoice(0, null, null, HomeFragment.customer.getCustomerCode(), tienBan, giamGia, sumMoney, "Chờ xác nhận", null, true);
        ApiService.apiService.postInvoice(invoice).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()) {
                    Invoice invoice1 = response.body();
                    for (int i = 0; i < listProductsSelect.size(); i++) {
                        double sumMoney = listProductsSelect.get(i).productAmount * (listProductsSelect.get(i).getProductUnitPrice() - listProductsSelect.get(i).getProductSale());
                        DetailInvoice detailInvoice = new DetailInvoice(invoice1.getMaHoaDon(), listProductsSelect.get(i).productCode,
                                listProductsSelect.get(i).productAmount, listProductsSelect.get(i).productUnitPrice, listProductsSelect.get(i).productSale,
                                sumMoney, null);
                        dataBaseHelper.DeleteProduct(listProductsSelect.get(i).productCode);
                        cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
                        cartAdapter.notifyDataSetChanged();
                        if (cartModelList.isEmpty()) {
                            layoutChoseAllProduct.setVisibility(View.GONE);
                            layoutSumMoeny.setVisibility(View.GONE);
                            bottomNavigationView.setVisibility(View.GONE);
                            linearLayout_NoProducts.setVisibility(View.VISIBLE);
                        } else {
                            tvCountProductCart.setText("(" + CartActivity.cartModelList.size() + " sản phẩm)");
                            layoutChoseAllProduct.setVisibility(View.VISIBLE);
                            linearLayout_NoProducts.setVisibility(View.GONE);
                            layoutSumMoeny.setVisibility(View.VISIBLE);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                        ApiService.apiService.postDetailInvoice(detailInvoice).enqueue(new Callback<DetailInvoice>() {
                            @Override
                            public void onResponse(Call<DetailInvoice> call, Response<DetailInvoice> response) {
                                Toast.makeText(CartActivity.this, "Mua hàng thành công!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<DetailInvoice> call, Throwable t) {
                                Toast.makeText(CartActivity.this, "Send Data err", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (cartModelList == null || cartModelList.size() == 0) {
                        layoutByDoApriori.setVisibility(View.GONE);
                        listProductDoApriori.clear();
                        aprioriAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("Lỗi: ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Send Data err", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callApiProductByProductCode(int productCode) {
        ApiService.apiService.getDataProduct(productCode).enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listProductByCode.add(new Product(response.body().maSanPham, response.body().tenSanPham,
                            response.body().soLuong, response.body().donGia, response.body().donGiaNhap,
                            response.body().moTa, response.body().moTaChiTiet, response.body().khuyenMai,
                            response.body().giamGia, response.body().ngayCapNhat, response.body().xuatXu,
                            response.body().hinhMinhHoa, response.body().dsHinh, response.body().tinhTrang,
                            response.body().ghiChu, response.body().tenDanhMuc));
                } else {
                    System.out.println("Khong tim được sản phẩm này!");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void DoApriori() {
        List<String> listString = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.HORIZONTAL, true);
        rcvDoApriori.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        rcvDoApriori.addItemDecoration(itemDecoration);

        // duyệt danh sách cách sản phẩm có trong giỏ hàng
        if (cartModelList == null || cartModelList.size() == 0) {
            layoutByDoApriori.setVisibility(View.GONE);
            listProductDoApriori.clear();
            return;
        }
        layoutByDoApriori.setVisibility(View.VISIBLE);
        for (CartModel item : cartModelList) {
            if (item.note.equalsIgnoreCase("DienThoai") || item.note.equalsIgnoreCase("Laptop")) {
                listString.add(item.note);
            }
            if (item.note.equalsIgnoreCase("PhuKien")) {
                listString.add(item.categoryName);
            }
        }

        // lấy các ds khong trùng nhau đê khai phá luật
        Set<String> set = new HashSet<>(listString);
        listString.clear();
        listString.addAll(set);
        // timf cacs tập hợp con của chuỗi không trùng nhau
        List<String> listSubs = new ArrayList<>();
        for (int i = 0; i < listString.size(); i++) {
            findSubset(listString.get(i).toString().trim(), listSubs);
        }
        for (String item : listSubs) {
            callApiDoApriori(item.substring(0, item.length() - 1).trim());
        }
        aprioriAdapter = new AprioriAdapter(listProductDoApriori, this);
        rcvDoApriori.setAdapter(aprioriAdapter);
        aprioriAdapter.notifyDataSetChanged();
    }

    public void findSubset(String s, List<String> listSub) {
        if (listSub.size() == 0) {
            listSub.add(s + ",");
        } else {
            int size = listSub.size();
            for (int i = 0; i < size; i++) {
                if (!listSub.contains(s + ",")) {
                    listSub.add(s + ",");
                }
                listSub.add(listSub.get(i) + s + ",");
            }
        }
    }

    public void callApiDoApriori(String input) {
        ApiService.apiService.getDataDoApriori(input).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                //listProductDoApriori.clear();
                if (response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if (listProductDoApriori.size() == 0 || listProductDoApriori == null) {
                            listProductDoApriori.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                    response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                    response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                    response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                    response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                    response.body().get(i).ghiChu, response.body().get(i).tenDanhMuc));
                        } else {
                            Product product = new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                    response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                    response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                    response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                    response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                    response.body().get(i).ghiChu, response.body().get(i).tenDanhMuc);
                            boolean check = true;
                            for (int j = 0; j < listProductDoApriori.size(); j++) {
                                if (listProductDoApriori.get(j).maSanPham == product.maSanPham) {
                                    check = false;
                                }
                            }
                            if (check) {
                                listProductDoApriori.add(product);
                            }
                        }
                        aprioriAdapter.notifyDataSetChanged();
                    }
                    for (CartModel cartModel : cartModelList) {
                        for (Product item : listProductDoApriori) {
                            if (cartModel.getNote().equalsIgnoreCase("DienThoai") || cartModel.getNote().equalsIgnoreCase("Laptop")) {
                                if (cartModel.getNote().equalsIgnoreCase(item.getGhiChu())) {
                                    listProductDoApriori.remove(item);
                                    aprioriAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                            if (cartModel.getNote().equalsIgnoreCase("PhuKien")) {
                                if (cartModel.getCategoryName().equalsIgnoreCase(item.getTenDanhMuc())) {
                                    listProductDoApriori.remove(item);
                                    aprioriAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }


}