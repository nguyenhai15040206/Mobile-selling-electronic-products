package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.banhangonline.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;

public class SpecificationsLatopActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvScreenLatopTTCT,tvCPULaptopTTCT,tvRAMLaptopTTCT,tvGrapicsLaptopTTCT,tvOperatingSysLaptopTTCT,
            tvWeightLaptopTTCT,tvSizeLaptopTTCT,tvMadInLaptopTTCT,tvDateOfManufactureLaptopTTCT;
    private TextView tvNameProductTTCT;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifications_latop);
        toolbar=findViewById(R.id.toolbarLaptopCT);
        tvScreenLatopTTCT = findViewById(R.id.tvScreenLatopTTCT);
        tvCPULaptopTTCT = findViewById(R.id.tvCPULaptopTTCT);
        tvRAMLaptopTTCT = findViewById(R.id.tvRAMLaptopTTCT);
        tvGrapicsLaptopTTCT = findViewById(R.id.tvGrapicsLaptopTTCT);
        tvOperatingSysLaptopTTCT = findViewById(R.id.tvOperatingSysLaptopTTCT);
        tvWeightLaptopTTCT = findViewById(R.id.tvWeightLaptopTTCT);
        tvMadInLaptopTTCT = findViewById(R.id.tvMadInLaptopTTCT);
        tvSizeLaptopTTCT = findViewById(R.id.tvSizeLaptopTTCT);
        tvDateOfManufactureLaptopTTCT = findViewById(R.id.tvDateOfManufactureLaptopTTCT);
        tvNameProductTTCT = findViewById(R.id.tvNameProductLaptopTTCT);

        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //
        reveiceData_Intent();
    }

    // nhận dữ liệu từ Intent rồi gán vào các dữ liêu tương đương
    public void reveiceData_Intent()
    {
        Intent intent = getIntent();
        String madeInProduct = intent.getStringExtra("4");
        String decriptionProduct = intent.getStringExtra("1");
        String decriptionDetails = intent.getStringExtra("2");
        String dateOfManufacture = intent.getStringExtra("3");
        String nameProduct = intent.getStringExtra("5");
        tvNameProductTTCT.setText(nameProduct);
        String []arrDcrip = decriptionProduct.split(Pattern.quote("|"));
        String []arrDrcipDetails = decriptionDetails.split(Pattern.quote("|"));
        tvCPULaptopTTCT.setText(arrDcrip[1].trim());
        tvRAMLaptopTTCT.setText(arrDcrip[2].trim());
        tvScreenLatopTTCT.setText(arrDcrip[0].trim());
        tvGrapicsLaptopTTCT.setText(arrDcrip[4].trim());
        tvWeightLaptopTTCT.setText(arrDrcipDetails[1].trim());
        tvOperatingSysLaptopTTCT.setText(arrDrcipDetails[0].trim());
        tvSizeLaptopTTCT.setText(arrDrcipDetails[2].trim());
        tvMadInLaptopTTCT.setText(madeInProduct.trim());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        sdf.setTimeZone(tz);
        Date s = null;
        String da = null;
        Date strToDate = null;
        try {
            s = sdf.parse(dateOfManufacture);
            System.out.println(s);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            da = sdf.format(s);
            System.out.println(da);
            tvDateOfManufactureLaptopTTCT.setText(da);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                CustomIntent.customType(this,"up-to-bottom");
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}