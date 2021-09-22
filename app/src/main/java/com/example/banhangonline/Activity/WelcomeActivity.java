package com.example.banhangonline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhangonline.Database.DataBaseHelper;
import com.example.banhangonline.R;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Animation top,bottom;
    private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        top = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.imgWelcom);
        textView = findViewById(R.id.tvLogoGhiChu);
        imageView.setAnimation(top);
        textView.setAnimation(bottom);
        dataBaseHelper = new DataBaseHelper(this);
        //dataBaseHelper.droptable();
        dataBaseHelper.createDB();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                //CustomIntent.customType(WelcomeActivity.this,"rotateout-to-rotatein");
                finish();
            }
        },2500);
    }
}