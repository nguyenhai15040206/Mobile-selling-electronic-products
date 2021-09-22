package com.example.banhangonline.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Activity.AccessoriesActivity;
import com.example.banhangonline.Activity.LaptopDetailsActivity;
import com.example.banhangonline.Activity.LoginActivity;
import com.example.banhangonline.Activity.MainActivity;
import com.example.banhangonline.Activity.ProductsByCategoryActivity;
import com.example.banhangonline.CustomAdapter.CategoryAdapter;
import com.example.banhangonline.CustomAdapter.ProductsAdapter;
import com.example.banhangonline.Activity.PhoneDetailsActivity;
import com.example.banhangonline.Model.Banner;
import com.example.banhangonline.CustomAdapter.BannerAdapter;
import com.example.banhangonline.Model.Category;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import maes.tech.intentanim.CustomIntent;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    HomeFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private BannerAdapter bannerAdapter;
    private List<Banner> listBanner;
    private List<Banner> listBannerTemp;
    private Timer pTimer;

    //Category
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> listCategories;
    //Product
    private List<Product> listProduct;
    private ProductsAdapter productsAdapter;
    private RecyclerView rcvProducts;
    private NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    private int page=1, limit =16;
    public static Customer customer;
    private SharedPreferences sharedPreferences;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mapping(view);
        Login();
        Banner();
        Category();
        Product();
    }

    public void Category(){
        //Categories
        listCategories = new ArrayList<>();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        callApiListCategories();
        categoryAdapter = new CategoryAdapter(getContext(),listCategories);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    public void Banner(){
        listBannerTemp = new ArrayList<>();
        listBanner = new ArrayList<>();
        callApiListBanners();
        int count =  listBanner.size();
        bannerAdapter = new BannerAdapter(getContext(), listBanner);
        viewPager.setAdapter(bannerAdapter);
        autoSlideImages();
        circleIndicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    public void Login(){
        customer = new Customer();
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.DATA_LOGIN, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        String passWord = sharedPreferences.getString("password","");
        callApiCustomer_Login(userName,passWord);
    }

    public  void Mapping(View view){
        recyclerView = view.findViewById(R.id.rcvCategories);
        viewPager = view.findViewById(R.id.viewPagerImgSlider);
        circleIndicator = view.findViewById(R.id.circleIndicator);
        rcvProducts = view.findViewById(R.id.rcvProducts);
        nestedScrollView = view.findViewById(R.id.scrollView);
        progressBar = view.findViewById(R.id.progressBarProduct);
    }

    public  void Product(){
        //products
        listProduct = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcvProducts.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rcvProducts.addItemDecoration(itemDecoration);
        callApiListProducts(page,limit);
        productsAdapter = new ProductsAdapter(getContext(),listProduct) ;
        rcvProducts.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();

        try {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                    {
                        page ++;

                        progressBar.setVisibility(View.VISIBLE);
                        callApiListProducts(page,limit);
                        rcvProducts.setAdapter(productsAdapter);
                        nestedScrollView.setNestedScrollingEnabled(false);
                        productsAdapter.notifyDataSetChanged();

                    }
                }
            });
        }catch (Exception ex)
        {
            System.out.println(""+ex);
        }
    }




    /// Call Api
    public void callApiCustomer_Login(String userName, String passWord) {
        ApiService.apiService.getDataLogin(userName, passWord).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.body() !=null && response.isSuccessful())
                {
                    customer = new Customer(response.body().getCustomerCode(),response.body().getCustomerName(),response.body().getCustomerPhoneNumber(),
                            response.body().getCustomerEmail(),response.body().getCustomerAdress(),response.body().getCutomerUserName(),response.body().getCustomerPassword());
                    CustomIntent.customType(getContext(), "right-to-left");
                }
                else
                {
                    customer = null;
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                return;
            }
        });
    }




    //call API (GET) List Banners
    private void callApiListBanners()
    {
        ApiService.apiService.getAllDataBanner().enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                listBanner.clear();
                if(response.isSuccessful() && response.body() !=null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        listBanner.add(new Banner(response.body().get(i).maBanner, response.body().get(i).fileBanner,
                                response.body().get(i).kichHoat, response.body().get(i).ghiChu));
                        bannerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // call API (GET) list Products
    private  void callApiListProducts(int page,int limit)
    {
        ApiService.apiService.getAllDataProducts(page,limit).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.body() !=null && response.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().size(); i++) {
                                listProduct.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
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
                            Product product = listProduct.get(position);
                            Toast.makeText(getContext(),""+product.tenSanPham, Toast.LENGTH_SHORT).show();
                            if(product.ghiChu.equals("DienThoai")) {
                                Intent intent = new Intent(getActivity(), PhoneDetailsActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(getContext(),"left-to-right");
                            }
                            if(product.ghiChu.equals("Laptop"))
                            {
                                Intent intent = new Intent(getActivity(), LaptopDetailsActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(getContext(),"left-to-right");
                            }
                            if(product.ghiChu.equalsIgnoreCase("PhuKien"))
                            {
                                Intent intent = new Intent(getActivity(), AccessoriesActivity.class);
                                intent.putExtra("productCode",product.maSanPham);

                                startActivity(intent);
                                CustomIntent.customType(getContext(),"left-to-right");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(),"Mạng không ổn định vui lòng thử lại",Toast.LENGTH_SHORT).show();
            }
        });

    }

    // call API (GET) list categories
    public void callApiListCategories()
    {
        ApiService.apiService.getAllDataCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                //Toast.makeText(getContext(),"Call Api Success",Toast.LENGTH_SHORT).show();
                listCategories.clear();
                if(response.body()!=null && response.isSuccessful()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < response.body().size(); i++) {
                                listCategories.add(new Category(response.body().get(i).maDanhMuc, response.body().get(i).tenDanhMuc,
                                        response.body().get(i).ghiChu, response.body().get(i).logoTungSanPham));
                                categoryAdapter.notifyDataSetChanged();
                            }
                        }
                    },1000);
                }
                categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Category category = listCategories.get(position);
                        Intent intent = new Intent(getContext(),ProductsByCategoryActivity.class);
                        intent.putExtra("code",category.maDanhMuc);
                        intent.putExtra("name",category.tenDanhMuc);
                        startActivity(intent);
                        CustomIntent.customType(getContext(),"left-to-right");
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });
    }

    // auto sileImage
    public void autoSlideImages()
    {
        //if(listBanner == null || listBanner.isEmpty() || viewPager == null)
        //{
        //    return;
        //}
        // init timer
        if(pTimer ==null)
        {
            pTimer = new Timer();
        }
        pTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItem = viewPager.getCurrentItem();// index Images in ViewPager
                        int totalItem = listBanner.size()-1 ;// sum images slide
                        if(curentItem <totalItem)
                        {
                            curentItem++;
                            viewPager.setCurrentItem(curentItem);
                        }else { // run to last slide -> comback
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000); // delay is time delay before hading
    }


    // Activity is Active = false -> cancel Timer
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(pTimer !=null)
        {
            pTimer.cancel();
            pTimer = null; // activity enable -> set timer = null
        }
    }
}