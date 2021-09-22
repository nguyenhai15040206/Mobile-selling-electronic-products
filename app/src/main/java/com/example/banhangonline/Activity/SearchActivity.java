package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CustomAdapter.ProductsAdapter;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import me.gujun.android.taggroup.TagGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUnitPrice,tvUnitPriceUp,tvUnitDown;
    private SearchView txtSearch;
    private Toolbar toolbar;
    private LinearLayout linearLayoutNotFond;
    private TagGroup tagGroup;
    private ProgressBar progressBar;
    private List<Product> listProducts, listProductsFillter;
    ProductsAdapter productsAdapter;
    private RecyclerView rcvProducts;
    private NestedScrollView nestedScrollView;
    private int page = 1, limit = 16;

    @SuppressLint({"RestrictedApi", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Mapping();
        linearLayoutNotFond.setVisibility(View.GONE);
        tvUnitPriceUp.setVisibility(View.GONE);
        tvUnitDown.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        SearchData();
        Taggroup();
        Product();
        EventOnCLick();
    }

    public void Mapping() {
        txtSearch = findViewById(R.id.searchData);
        toolbar = findViewById(R.id.toolbarSearch);
        tagGroup = findViewById(R.id.taggroup);
        nestedScrollView = findViewById(R.id.nestedScrollViewSearch);
        rcvProducts = findViewById(R.id.rcvproductSearch);
        progressBar = findViewById(R.id.progressBarSearch);
        linearLayoutNotFond = findViewById(R.id.layoutNotFond);
        tvUnitPrice = findViewById(R.id.tvUnitPriceSearch);
        tvUnitPriceUp = findViewById(R.id.tvUnitPriceUp);
        tvUnitDown = findViewById(R.id.tvUnitPriceDown);
    }
    public void SearchData() {
        txtSearch.setFocusable(true);
        txtSearch.setSelected(true);
        txtSearch.requestFocusFromTouch();
        if (txtSearch.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equalsIgnoreCase("Điện thoại")) {
                    searchProducts("DienThoai");
                } else if (query.equalsIgnoreCase("Laptop giá rẻ")) {
                    searchProducts("Laptop");
                } else {
                    searchProducts(query.trim());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    //listProductsFillter.clear();
                    page = 1;
                    Product();
                }
                return false;
            }
        });
    }

    public void Taggroup() {
        tagGroup.setTags(new String[]{"Điện thoại", "Bàn phím", "Laptop giá rẻ", "Tai nghe"});
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                txtSearch.setQuery(tag, true);
                hideSoftKeyboard(txtSearch);
            }
        });
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm
                = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(SearchActivity.this, "right-to-left");
    }

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


    public void Product() {
        //products
        listProducts = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        rcvProducts.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(SearchActivity.this, DividerItemDecoration.VERTICAL);
        rcvProducts.addItemDecoration(itemDecoration);
        callApiListProducts(page, limit);
        productsAdapter = new ProductsAdapter(SearchActivity.this, listProducts);
        rcvProducts.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();
        if (productsAdapter.getItemCount() == 0) {
            linearLayoutNotFond.setVisibility(View.VISIBLE);
        } else {
            linearLayoutNotFond.setVisibility(View.GONE);
        }
        try {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        page++;

                        progressBar.setVisibility(View.VISIBLE);
                        callApiListProducts(page, limit);
                        rcvProducts.setAdapter(productsAdapter);
                        nestedScrollView.setNestedScrollingEnabled(false);
                        productsAdapter.notifyDataSetChanged();

                    }
                }
            });
        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }

    // call API (GET) list Products
    private void callApiListProducts(int page, int limit) {
        ApiService.apiService.getAllDataProducts(page, limit).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    linearLayoutNotFond.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            linearLayoutNotFond.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().size(); i++) {
                                listProducts.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                        response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                        response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                        response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                        response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                        response.body().get(i).ghiChu,response.body().get(i).tenDanhMuc));
                                productsAdapter.notifyDataSetChanged();
                            }
                        }
                    },1500);
                    productsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Product product = listProducts.get(position);
                            Toast.makeText(SearchActivity.this, "" + product.tenSanPham, Toast.LENGTH_SHORT).show();
                            if (product.ghiChu.equals("DienThoai")) {
                                Intent intent = new Intent(SearchActivity.this, PhoneDetailsActivity.class);
                                intent.putExtra("productCode", product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(SearchActivity.this, "left-to-right");
                            }
                            if (product.ghiChu.equals("Laptop")) {
                                Intent intent = new Intent(SearchActivity.this, LaptopDetailsActivity.class);
                                intent.putExtra("productCode", product.maSanPham);
                                startActivity(intent);
                                CustomIntent.customType(SearchActivity.this, "left-to-right");
                            }
                            if(product.ghiChu.equalsIgnoreCase("PhuKien"))
                            {
                                Intent intent = new Intent(SearchActivity.this, AccessoriesActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(SearchActivity.this,"left-to-right");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Mạng không ổn định vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callApiProductsFillter(int page, int limit, String input) {
        ApiService.apiService.getProductFillter(page, limit, input).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    linearLayoutNotFond.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            linearLayoutNotFond.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().size(); i++) {
                                listProductsFillter.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                        response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                        response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                        response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                        response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                        response.body().get(i).ghiChu,response.body().get(i).tenDanhMuc));
                                productsAdapter.notifyDataSetChanged();
                            }
                            if(productsAdapter.getItemCount()!=0)
                            {
                                linearLayoutNotFond.setVisibility(View.GONE);
                            }else{
                                linearLayoutNotFond.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 1500);
                    productsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Product product = listProductsFillter.get(position);
                            Toast.makeText(SearchActivity.this, "" + product.tenSanPham, Toast.LENGTH_SHORT).show();
                            if (product.ghiChu.equals("DienThoai")) {
                                Intent intent = new Intent(SearchActivity.this, PhoneDetailsActivity.class);
                                intent.putExtra("productCode", product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(SearchActivity.this, "left-to-right");
                            }
                            if (product.ghiChu.equals("Laptop")) {
                                Intent intent = new Intent(SearchActivity.this, LaptopDetailsActivity.class);
                                intent.putExtra("productCode", product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(SearchActivity.this, "left-to-right");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Mạng không ổn định", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchProducts(String input) {
        if (input.isEmpty()) {
            return;
        } else {
            listProductsFillter = new ArrayList<>();
            rcvProducts.setAdapter(null);
            page = 1;
            callApiProductsFillter(page, limit, input);
            productsAdapter = new ProductsAdapter(SearchActivity.this, listProductsFillter);
            rcvProducts.setAdapter(productsAdapter);
            productsAdapter.notifyDataSetChanged();
            if (productsAdapter.getItemCount() == 0) {
                linearLayoutNotFond.setVisibility(View.VISIBLE);
            } else {
                linearLayoutNotFond.setVisibility(View.GONE);
            }
            try {
                nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            page++;
                            progressBar.setVisibility(View.VISIBLE);
                            callApiProductsFillter(page, limit, input);
                            rcvProducts.setAdapter(productsAdapter);
                            nestedScrollView.setNestedScrollingEnabled(false);
                            productsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            } catch (Exception ex) {
                System.out.println("" + ex);
            }
        }
    }

    public void EventOnCLick(){
        tvUnitPrice.setOnClickListener(this);
        tvUnitDown.setOnClickListener(this);
        tvUnitPriceUp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvUnitPriceSearch:
                tvUnitPrice.setVisibility(View.GONE);
                tvUnitPriceUp.setVisibility(View.VISIBLE);
                tvUnitDown.setVisibility(View.GONE);
                break;
            case R.id.tvUnitPriceUp:
                tvUnitDown.setVisibility(View.VISIBLE);
                tvUnitPriceUp.setVisibility(View.GONE);
                break;
            case R.id.tvUnitPriceDown:
                tvUnitPriceUp.setVisibility(View.VISIBLE);
                tvUnitDown.setVisibility(View.GONE);
                break;
        }
    }
}