package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CustomAdapter.ProductsAdapter;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsByCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ViewFlipper imageSlider;
    private RecyclerView rcvProductsByCategory;
    private ProgressBar progressBar;
    private List<Product> listProducts;
    private ProductsAdapter productsAdapter;

    private int coteCategory = 0;
    private String nameCategory = null;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_by_category);
        Mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        reciveData_Intent();
        getSupportActionBar().setTitle(nameCategory);
        ImagesSlide();
        setDataToRecycleView();
        Event();
    }

    public void Event(){
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                CustomIntent.customType(ProductsByCategoryActivity.this, "right-to-left");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void reciveData_Intent(){
        Intent intent = getIntent();
        coteCategory = intent.getIntExtra("code",0);
        nameCategory = intent.getStringExtra("name");
    }


    // ánh xạ
    public void Mapping(){
        imageSlider= (ViewFlipper)findViewById(R.id.imageSliderProduct);
        rcvProductsByCategory = findViewById(R.id.rcvProductsByCategory);
        progressBar = findViewById(R.id.progress_bar_ProByCate);
        toolbar = findViewById(R.id.back);
    }

    // ImageSlide
    public void ImagesSlide(){
        List<String> slideModels = new ArrayList<>();
        slideModels.add("https://images.fpt.shop/unsafe/fit-in/800x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2021/6/30/637606927404611164_F_H1_800x300.png");
        slideModels.add("https://img3.thuthuatphanmem.vn/uploads/2019/10/08/banner-quang-cao-dien-thoai-dep_103211368.jpg");
        slideModels.add("http://cdn.thegioididong.com/Files/2012/10/04/45504/banner_kmlatop10_775x295.jpg");
        slideModels.add("https://thietkehaithanh.com/wp-content/uploads/2019/01/thietkehaithanh-banner-laptop.png");
        slideModels.add("https://thietkehaithanh.com/wp-content/uploads/2019/06/huong-dan-thiet-ke-banner-dien-thoai-bang-photoshop.jpg");
        for (int i =0 ; i< slideModels.size(); i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(slideModels.get(i)).error(R.drawable.notfound).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageSlider.addView(imageView);
        }
        imageSlider.setFlipInterval(4000);
        imageSlider.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        imageSlider.setInAnimation(animation_slide_in);
        imageSlider.setOutAnimation(animation_slide_out);
    }

    public void setDataToRecycleView(){
        listProducts = new ArrayList<>();
        callApiProductByCatgory(coteCategory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        rcvProductsByCategory.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvProductsByCategory.addItemDecoration(itemDecoration);
        productsAdapter = new ProductsAdapter(this,listProducts);
        rcvProductsByCategory.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();
    }


    public void callApiProductByCatgory(int categoryCode){
        ApiService.apiService.getAllDataProductByCategory(categoryCode).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                listProducts.clear();
                if(response.isSuccessful() && response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    for (int i = 0; i < response.body().size(); i++) {
                        listProducts.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                response.body().get(i).ghiChu,response.body().get(i).tenDanhMuc));
                        productsAdapter.notifyDataSetChanged();
                    }
                    productsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Product product = listProducts.get(position);
                            Toast.makeText(ProductsByCategoryActivity.this,""+product.tenSanPham, Toast.LENGTH_SHORT).show();
                            if(product.ghiChu.equals("DienThoai")) {
                                Intent intent = new Intent(ProductsByCategoryActivity.this, PhoneDetailsActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(ProductsByCategoryActivity.this,"left-to-right");
                            }
                            if(product.ghiChu.equals("Laptop"))
                            {
                                Intent intent = new Intent(ProductsByCategoryActivity.this, LaptopDetailsActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(ProductsByCategoryActivity.this,"left-to-right");
                            }
                            if(product.ghiChu.equalsIgnoreCase("PhuKien"))
                            {
                                Intent intent = new Intent(ProductsByCategoryActivity.this, AccessoriesActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(ProductsByCategoryActivity.this,"left-to-right");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductsByCategoryActivity.this,"Call err",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(this, "right-to-left");
    }
}