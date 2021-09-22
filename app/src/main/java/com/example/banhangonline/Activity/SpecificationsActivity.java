package com.example.banhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.banhangonline.R;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;

public class SpecificationsActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tvMadeInTTCT,tvCPUTTCT,tvScreenTTCT,tvCameraSauTTCT,tvCameraSelfiTTCT,
            tvRAMTTCT,tvInternalMemoryTTCT,tvGPUTTCT,tvPinTTCT,tvSIMTTCT,tvOperatingSysTTCT,
            tvDateOfManufactureTTCT,tvNameProductTTCT;
    @SuppressLint({"RestrictedApi", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_specifications);
        toolbar=findViewById(R.id.toolbaCater);
        tvMadeInTTCT = findViewById(R.id.tvMadeInTTCT);
        tvCPUTTCT = findViewById(R.id.tvCPUTTCT);
        tvScreenTTCT = findViewById(R.id.tvScreenTTCT);
        tvCameraSauTTCT = findViewById(R.id.tvCameraSauTTCT);
        tvCameraSelfiTTCT = findViewById(R.id.tvCameraSelfiTTCT);
        tvRAMTTCT = findViewById(R.id.tvRAMTTCT);
        tvInternalMemoryTTCT = findViewById(R.id.tvInternalMemoryTTCT);
        tvGPUTTCT = findViewById(R.id.tvGPUTTCT);
        tvPinTTCT = findViewById(R.id.tvPinTTCT);
        tvSIMTTCT = findViewById(R.id.tvSIMTTCT);
        tvOperatingSysTTCT = findViewById(R.id.tvOperatingSysTTCT);
        tvDateOfManufactureTTCT = findViewById(R.id.tvDateOfManufactureTTCT);
        tvNameProductTTCT = findViewById(R.id.tvNameProductTTCT);

        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String madeInProduct = intent.getStringExtra("4");
        String decriptionProduct = intent.getStringExtra("1");
        String decriptionDetails = intent.getStringExtra("2");
        String dateOfManufacture = intent.getStringExtra("3");
        String nameProduct = intent.getStringExtra("5");
        tvNameProductTTCT.setText(nameProduct);
        String []arrDcrip = decriptionProduct.split(Pattern.quote("|"));
        String []arrDrcipDetails = decriptionDetails.split(Pattern.quote("|"));
        tvScreenTTCT.setText(arrDcrip[0].trim());
        tvCameraSauTTCT.setText(arrDcrip[1].trim());
        tvCameraSelfiTTCT.setText(arrDcrip[2].trim());
        tvRAMTTCT.setText(arrDcrip[4].toString());
        tvInternalMemoryTTCT.setText(arrDcrip[5].trim());
        tvCPUTTCT.setText(arrDcrip[3].trim());
        tvGPUTTCT.setText(arrDrcipDetails[0].trim());
        tvPinTTCT.setText(arrDrcipDetails[1].trim());
        tvSIMTTCT.setText(arrDrcipDetails[2].trim());
        tvOperatingSysTTCT.setText(arrDrcipDetails[3].trim());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
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
            tvDateOfManufactureTTCT.setText(da);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tvMadeInTTCT.setText(madeInProduct.trim());


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