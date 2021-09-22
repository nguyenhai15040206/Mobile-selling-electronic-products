package com.example.banhangonline.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CustomAdapter.CategoryAdapterInFragment;
import com.example.banhangonline.CustomAdapter.ProductsAdapter;
import com.example.banhangonline.CustomAdapter.ProductsAdapterFragment;
import com.example.banhangonline.CustomAdapter.ViewPagerAdapter;
import com.example.banhangonline.Model.Category;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.ITabView.TabIcon;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {


    //
    private VerticalTabLayout tabLayout;
    private ViewPager viewpaggerCategories;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category_AppleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tablayoutCategories);
        viewpaggerCategories = view.findViewById(R.id.viewpaggerCategories);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getContext());
        viewpaggerCategories.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewpaggerCategories);
        tabLayout.getTabAt(3).setIcon(new TabView.TabIcon.Builder().setIcon(R.drawable.appleicon, R.drawable.appleicon)
                .setIconGravity(Gravity.TOP)
                .setIconMargin(ViewPagerAdapter.dp2px(getContext(),-55))
                .setIconSize(ViewPagerAdapter.dp2px(getContext(),65), ViewPagerAdapter.dp2px(getContext(),65))
                .build());
        tabLayout.getTabAt(0).setIcon(new TabView.TabIcon.Builder().setIcon(R.drawable.ngoisao, R.drawable.ngoisao)
                .setIconGravity(Gravity.TOP)
                .setIconMargin(ViewPagerAdapter.dp2px(getContext(),-50))
                .setIconSize(ViewPagerAdapter.dp2px(getContext(),55), ViewPagerAdapter.dp2px(getContext(),55))
                .build());

        tabLayout.setTabSelected(0,true);

    }










}