package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Model.Banner;
import com.example.banhangonline.Model.ConvertStringToBitmapFromAsscess;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerAdapter extends PagerAdapter {
    List<Banner> listBanner;
    Context context;

    public BannerAdapter( Context context, List<Banner> listBanner) {
        this.context = context;
        this.listBanner = listBanner;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_imageslider_layout,container,false);
        ImageView imageView = view.findViewById(R.id.imageSlider);
        Banner banner = listBanner.get(position);
        if(banner !=null)
        {
            Picasso.get().load(banner.fileBanner).error(R.drawable.notfound).into(imageView);
            //Glide.with(context).load(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,banner.fileBanner)).into(imageView);
        }
        // add view to Viewgroup
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(listBanner!=null)
        {
            return listBanner.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull  View view, @NonNull  Object object) {
        return view== object;
    }

    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position, @NonNull  Object object) {
        container.removeView((View)object);
    }
}
