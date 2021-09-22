package com.example.banhangonline.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Activity.AccessoriesActivity;
import com.example.banhangonline.Activity.LaptopDetailsActivity;
import com.example.banhangonline.Activity.PhoneDetailsActivity;
import com.example.banhangonline.Activity.ProductsByCategoryActivity;
import com.example.banhangonline.CustomAdapter.CategoryAdapter;
import com.example.banhangonline.CustomAdapter.CategoryAdapterInFragment;
import com.example.banhangonline.CustomAdapter.ProductsAdapter;
import com.example.banhangonline.CustomAdapter.ProductsAdapterFragment;
import com.example.banhangonline.Model.Category;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Category_AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category_AllFragment extends Fragment {

    private RecyclerView rcvCategories;
    private RecyclerView rcvProducts;
    private NestedScrollView nestedScrollView;
    private CategoryAdapterInFragment categoryAdapter;
    private ProductsAdapterFragment productsAdapterFragment;
    private ProgressBar progressbarFragment;
    private List<Category> listCategories;
    private List<Product> listProducts;

    private int page=1, limit = 16;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Category_AllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category_IpadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Category_AllFragment newInstance(String param1, String param2) {
        Category_AllFragment fragment = new Category_AllFragment();
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
        return inflater.inflate(R.layout.fragment_category__all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping(view);
        Categories();
        Products();

        try {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                    {
                        page ++;

                        progressbarFragment.setVisibility(View.VISIBLE);
                        callApiProduct(page,limit);
                        rcvProducts.setAdapter(productsAdapterFragment);
                        nestedScrollView.setNestedScrollingEnabled(false);
                        productsAdapterFragment.notifyDataSetChanged();

                    }
                }
            });
        }catch (Exception ex)
        {
            System.out.println(""+ex);
        }
    }

    // ánh xạ
    public void mapping(View view) {
        rcvCategories = view.findViewById(R.id.rcvCategoriesAll);
        rcvProducts = view.findViewById(R.id.rcvProductInFragmentAll);
        progressbarFragment = view.findViewById(R.id.progressbarAllFragment);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewAll);
    }

    // đổ dữ liệu cho danh mục sản phẩm
    public void Categories() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);
        rcvCategories.setLayoutManager(gridLayoutManager);
        listCategories = new ArrayList<>();
        callApiListCategories();
        categoryAdapter = new CategoryAdapterInFragment(listCategories, getContext());
        rcvCategories.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    // đổ dữ liệu cho sản phẩm
    public void Products() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rcvProducts.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvProducts.addItemDecoration(itemDecoration);
        listProducts = new ArrayList<>();
        callApiProduct(page,limit);
        productsAdapterFragment = new ProductsAdapterFragment(listProducts, getContext());
        rcvProducts.setAdapter(productsAdapterFragment);
        productsAdapterFragment.notifyDataSetChanged();
    }


    //
    public void callApiListCategories() {
        ApiService.apiService.getAllDataCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                listCategories.clear();
                if (response.isSuccessful() && response.body() != null) {
                    progressbarFragment.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbarFragment.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().size(); i++) {
                                listCategories.add(new Category(response.body().get(i).maDanhMuc, response.body().get(i).tenDanhMuc,
                                        response.body().get(i).ghiChu, response.body().get(i).logoTungSanPham));
                                categoryAdapter.notifyDataSetChanged();
                                categoryAdapter.notifyDataSetChanged();
                            }
                            categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Category category = listCategories.get(position);
                                    Intent intent = new Intent(getContext(), ProductsByCategoryActivity.class);
                                    intent.putExtra("code",category.maDanhMuc);
                                    intent.putExtra("name",category.tenDanhMuc);
                                    startActivity(intent);
                                    CustomIntent.customType(getContext(),"left-to-right");
                                }
                            });
                        }

                    },1000);


                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                return;
            }
        });
    }

    //
    public void callApiProduct(int page,int limit) {
        ApiService.apiService.getAllDataProducts(page,limit).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.body() !=null && response.isSuccessful()) {
                    progressbarFragment.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbarFragment.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().size(); i++) {
                                listProducts.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                        response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                        response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                        response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                        response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                        response.body().get(i).ghiChu,response.body().get(i).tenDanhMuc));
                                productsAdapterFragment.notifyDataSetChanged();
                            }
                        }
                    },1500);
                    productsAdapterFragment.setOnItemClickListener(new ProductsAdapterFragment.OnItemClickListener(){
                        @Override
                        public void onItemClick(int position) {
                            Product product = listProducts.get(position);
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
                return;
            }
        });
    }
}