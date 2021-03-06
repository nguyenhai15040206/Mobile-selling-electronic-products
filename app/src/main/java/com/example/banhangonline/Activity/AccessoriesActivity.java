package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CustomAdapter.ListImagesProductAdapter;
import com.example.banhangonline.CustomAdapter.PromotionAdapter;
import com.example.banhangonline.Database.DataBaseHelper;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessoriesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgProducts;
    private TextView tvNameProduct,tvSale;
    private RecyclerView rcvBangMau;
    private List<String>  listImgProducts;
    private ListImagesProductAdapter listImagesProductAdapter;
    private TextView tvUnitPriceDetails,tvDiscountProductsDetails;
    private DataBaseHelper dataBaseHelper;
    BottomNavigationView bottomNavigationView;

    //
    private int productCode = 0;
    String imgProductsDetail = null;
    String nameProduct = null;
    double unitPriceProduct=0;
    double saleProduct=0;
    String decriptionProducts = null;
    String decriptionDetailsProduct = null;
    String madeInProduct = null;
    String dateOfManufacture = null;
    String listImageProducts= null;
    String note=null;
    String categoryName = null;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);
        Mapping();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_blue_back_24);

        //
        dataBaseHelper = new DataBaseHelper(this);
        listImgProducts = new ArrayList<>();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rcvBangMau.setLayoutManager(linearLayoutManager1);
        Intent intent = getIntent();
        productCode = intent.getIntExtra("productCode",0);
        callApiProductByCode(productCode);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(AccessoriesActivity.this,SpecificationsLatopActivity.class);
            intent.putExtra("1",decriptionProducts);// m?? t???
            intent.putExtra("2",decriptionDetailsProduct);// m?? t??? chhi ti???t
            intent.putExtra("3",dateOfManufacture);// ng??y c???p nh???t
            intent.putExtra("4",madeInProduct);// xu???t x???
            intent.putExtra("5",nameProduct);// t??n s???n phaamr
            startActivity(intent);
            CustomIntent.customType(AccessoriesActivity.this,"bottom-to-up");
        }
    };

    public void Mapping(){
        bottomNavigationView = findViewById(R.id.nav_viewDetailPhuKien);
        toolbar=findViewById(R.id.toolbaPhuKienDetail);
        imgProducts = findViewById(R.id.imgProductsPhuKienDetails);
        tvNameProduct = findViewById(R.id.tvNameProductPhuKienDetails);
        tvSale = findViewById(R.id.tvSaleProductsPhuKienDetails);
        rcvBangMau = findViewById(R.id.rcvbangMauPhuKien);
        tvUnitPriceDetails = findViewById(R.id.tvUnitPricePhuKienDetails);
        tvDiscountProductsDetails = findViewById(R.id.tvSaleProductsPhuKienDetails);
    }

    public void callApiProductByCode(int productCode)
    {
        ApiService.apiService.getDataProduct(productCode).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Product product = new Product(response.body().maSanPham, response.body().tenSanPham,
                            response.body().soLuong, response.body().donGia, response.body().donGiaNhap,
                            response.body().moTa, response.body().moTaChiTiet, response.body().khuyenMai,
                            response.body().giamGia, response.body().ngayCapNhat, response.body().xuatXu,
                            response.body().hinhMinhHoa, response.body().dsHinh, response.body().tinhTrang,
                            response.body().ghiChu,response.body().tenDanhMuc);
                    nameProduct = product.tenSanPham;
                    imgProductsDetail = product.hinhMinhHoa;
                    unitPriceProduct = product.donGia;
                    saleProduct = product.giamGia;
                    decriptionProducts = product.moTa;
                    decriptionDetailsProduct = product.moTaChiTiet;
                    madeInProduct = product.xuatXu;
                    categoryName = product.tenDanhMuc;
                    note = product.ghiChu;
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                    TimeZone tz = TimeZone.getTimeZone("GMT+8");
                    sdf.setTimeZone(tz);
                    Date s = null;
                    String da = null;
                    try {
                        s = sdf.parse(product.ngayCapNhat.toString());
                        System.out.println(s);
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        da = sdf.format(s);
                        System.out.println(da);
                        dateOfManufacture = da;
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    listImageProducts = product.dsHinh;

                    //
                    DecimalFormat decimalFormat = new DecimalFormat("#0,000");
                    getSupportActionBar().setTitle(nameProduct);
                    Picasso.get().load(imgProductsDetail.trim()).error(R.drawable.notfound).into(imgProducts);
                    //imgProducts.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(getApplication(),imgProductsDetail));
                    tvNameProduct.setText(nameProduct);

                    if(saleProduct !=0) {
                        tvUnitPriceDetails.setText("" + decimalFormat.format(unitPriceProduct - saleProduct) + "??");
                        tvDiscountProductsDetails.setPaintFlags(tvDiscountProductsDetails.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        tvDiscountProductsDetails.setText("" + decimalFormat.format(unitPriceProduct) + "??");
                    }
                    else
                    {
                        tvUnitPriceDetails.setText("" + decimalFormat.format(unitPriceProduct) + "??");
                        tvDiscountProductsDetails.setText("");
                    }
                    listImgProducts.add(imgProductsDetail);
                    if(listImageProducts!=null)
                    {
                        String []arrImg = listImageProducts.split(Pattern.quote("|"));
                        for (String item : arrImg)
                        {
                            listImgProducts.add(item.trim());
                        }
                    }
                    listImagesProductAdapter = new ListImagesProductAdapter(listImgProducts,AccessoriesActivity.this);
                    rcvBangMau.setAdapter(listImagesProductAdapter);
                    listImagesProductAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId())
            {
                case R.id.addCart:
                    if(HomeFragment.customer!=null) {
                        if (!dataBaseHelper.check_record_Product(productCode)) {
                            int productAmount = 1;
                            double totalMoney = productAmount * (unitPriceProduct - saleProduct);
                            dataBaseHelper.InsertProduct(productCode, nameProduct, imgProductsDetail, productAmount, unitPriceProduct, saleProduct, totalMoney, note,categoryName,false);
                            Toast.makeText(AccessoriesActivity.this, "???? th??m s???n ph???m v??o gi??? h??ng!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AccessoriesActivity.this, "S???n ph???m n??y ???? c?? trong gi??? h??ng!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Intent intent = new Intent(AccessoriesActivity.this, LoginActivity.class);
                        startActivity(intent);
                        CustomIntent.customType(AccessoriesActivity.this,"left-to-right");
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.botton_nav_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                CustomIntent.customType(AccessoriesActivity.this,"right-to-left");
                return true;
            case R.id.backhome:
                Intent intent1= new Intent(AccessoriesActivity.this,MainActivity.class);
                startActivity(intent1);
                CustomIntent.customType(AccessoriesActivity.this,"right-to-left");
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        CustomIntent.customType(AccessoriesActivity.this,"right-to-left");
    }


    //

}