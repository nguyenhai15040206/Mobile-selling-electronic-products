package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CustomAdapter.InvoiceAdapter;
import com.example.banhangonline.CustomAdapter.OrderProductsManagerAdapter;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.Invoice;
import com.example.banhangonline.Model.ProductManager;
import com.example.banhangonline.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OderManagerActivity extends AppCompatActivity {

    public static LinearLayout linearLayout_ProductsManagerIsNull;
    public static InvoiceAdapter invoiceAdapter;
    public static List<Invoice> invoiceList;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    public static String ghiChu;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_manager);
        Intent intent = getIntent();
        ghiChu = intent.getStringExtra("Data");
        Mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sản phẩm "+ghiChu);
        loadDataToRcvInvoice();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(OderManagerActivity.this, "right-to-left");
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
    public void Mapping(){
        linearLayout_ProductsManagerIsNull = findViewById(R.id.linearLayout_ProductsManagerIsNull);
        recyclerView = findViewById(R.id.rcvProductManager);
        toolbar = findViewById(R.id.toolbarManager);
    }

    public void loadDataToRcvInvoice(){
        invoiceList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        CallApiInvoiceList(HomeFragment.customer.getCustomerCode(),ghiChu);
        invoiceAdapter = new InvoiceAdapter(invoiceList,this);
        recyclerView.setAdapter(invoiceAdapter);
        invoiceAdapter.notifyDataSetChanged();
    }
    public void CallApiInvoiceList(int customerCode, String description){
        ApiService.apiService.getDataListInvoice(customerCode,description).enqueue(new Callback<List<Invoice>>() {
            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                invoiceList.clear();
                if(response.isSuccessful() && response.body()!=null)
                {
                    for (int i=0 ; i< response.body().size(); i++){
                        invoiceList.add(new Invoice(response.body().get(i).getMaHoaDon(), response.body().get(i).getNgayBan(),
                                response.body().get(i).getNgayGiao(),response.body().get(i).getMaKhachHang(),response.body().get(i).getTienBan(),
                                response.body().get(i).getGiamGia(),response.body().get(i).getTongTien(),response.body().get(i).getGhiChu(),
                                response.body().get(i).getMaNguoiDung(),response.body().get(i).isTinhTrang()));
                        invoiceAdapter.notifyDataSetChanged();
                    }
                }else{
                    invoiceList = null;
                        linearLayout_ProductsManagerIsNull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                Toast.makeText(OderManagerActivity.this,"Vui lòng kiểm tra kết nối Internet",Toast.LENGTH_SHORT).show();
            }
        });
    }


}