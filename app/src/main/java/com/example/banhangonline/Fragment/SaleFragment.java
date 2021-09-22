package com.example.banhangonline.Fragment;

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

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.CustomAdapter.CategoryAdapter;
import com.example.banhangonline.CustomAdapter.NewsAdapter;
import com.example.banhangonline.Model.Category;
import com.example.banhangonline.Model.News;
import com.example.banhangonline.R;

import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleFragment extends Fragment {

    private List<News> newsList;
    private int page=1;
    private int limit=15;
    //Category
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaleFragment newInstance(String param1, String param2) {
        SaleFragment fragment = new SaleFragment();
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
        return inflater.inflate(R.layout.fragment_sale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcvTinTuc);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewNews);
        progressBar = view.findViewById(R.id.progressBarNews);
        News();
        try {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                    {
                        page ++;

                        progressBar.setVisibility(View.VISIBLE);
                        callApiNews(page,limit);
                        recyclerView.setAdapter(newsAdapter);
                        nestedScrollView.setNestedScrollingEnabled(false);
                        newsAdapter.notifyDataSetChanged();

                    }
                }
            });
        }catch (Exception ex)
        {
            System.out.println(""+ex);
        }
    }

    public void News(){
        //Categories
        newsList = new ArrayList<>();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),1,RecyclerView.VERTICAL,false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        callApiNews(page,limit);
        newsAdapter = new NewsAdapter(newsList,getContext());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }

    public void callApiNews(int page, int limit)
    {
        ApiService.apiService.getAllDataNews(page,limit).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        if(response.isSuccessful() && response.body()!=null)
                        {
                            for (int i =0 ; i < response.body().size(); i++)
                            {
                                newsList.add(new News(response.body().get(i).maTinTuc,response.body().get(i).tenTinTuc,response.body().get(i).noiDung,
                                        response.body().get(i).ngayDang,response.body().get(i).anhMinhHoa,response.body().get(i).kichHoat,response.body().get(i).ghiChu,
                                        response.body().get(i).maLoaiTin));
                                newsAdapter.notifyDataSetChanged();

                            }
                        }//int maTinTuc, String tenTinTuc, String noiDung, String ngayDang, String anhMinhHoa, boolean kichHoat, String ghiChu, int maLoaiTin
                    }
                },1500);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }
}