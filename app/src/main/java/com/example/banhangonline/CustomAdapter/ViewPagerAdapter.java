package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.banhangonline.Fragment.AppleFragment;
import com.example.banhangonline.Fragment.Category_AllFragment;
import com.example.banhangonline.Fragment.Category_LapFragment;
import com.example.banhangonline.Fragment.Category_PKFragment;
import com.example.banhangonline.Fragment.Category_PhoneFragment;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.MenuBean;
import com.example.banhangonline.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements TabAdapter {
    List<MenuBean> menus;
    Context context;
    public ViewPagerAdapter(@NonNull  FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context=context;
        menus = new ArrayList<>();
        Collections.addAll(menus,new MenuBean(R.drawable.ngoisao,R.drawable.ngoisao,"Tất cả")
                ,new MenuBean(R.drawable.iphonevector,R.drawable.iphonevector,"Điện thoại"),
                new MenuBean(R.drawable.laptopvector,R.drawable.laptopvector,"Máy tính"),
                new MenuBean(R.drawable.appleicon1,R.drawable.appleicon1,"Apple"),
                new MenuBean(R.drawable.phukienvector,R.drawable.phukienvector,"Phụ kiện"));
    }


    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return new TabView.TabBadge.Builder().setBadgeTextSize(px2dp(context,22))
                .setBackgroundColor(0xff2faae5).setBadgeText("193+")
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    }
                }).build();
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        MenuBean menu = menus.get(position);
        return new TabView.TabIcon.Builder()
                .setIcon(menu.mSelectIcon, menu.mNormalIcon)
                .setIconGravity(Gravity.TOP)
                .setIconMargin(dp2px(context,-60))
                .setIconSize(dp2px(context,35), dp2px(context,35))
                .build();
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        MenuBean menu = menus.get(position);
        return new TabView.TabTitle.Builder()
                .setContent(menu.mTitle).setTextSize(px2dp(context,42) )
                .setTextColor(Color.RED,Color.DKGRAY)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return -1;
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Category_AllFragment();
            case 1: return new Category_PhoneFragment();
            case 2: return new Category_LapFragment();
            case 3: return new AppleFragment();
            case 4: return new Category_PKFragment();
            default: return new Category_AllFragment();

        }
    }
}
