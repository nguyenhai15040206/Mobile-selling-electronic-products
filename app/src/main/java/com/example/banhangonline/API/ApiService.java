package com.example.banhangonline.API;

import com.example.banhangonline.Model.Banner;
import com.example.banhangonline.Model.Category;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.Model.DetailInvoice;
import com.example.banhangonline.Model.Invoice;
import com.example.banhangonline.Model.News;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.Model.ProductManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1&fbclid=IwAR3R8arOBTSkVfrdX7xeJzH2iecnpVTbHWNs68ocE5eZWnjB-IAyzaNSSjA
    Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").setLenient().create();
    okhttp3.logging.HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().addInterceptor(HTTP_LOGGING_INTERCEPTOR).build();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.5:5000/Home/Introduct/").addConverterFactory(GsonConverterFactory.create(gson))
            .client(OK_HTTP_CLIENT)
            .build().create(ApiService.class);

    @GET("Banner")
    Call<List<Banner>> getAllDataBanner();

    @GET("DanhMuc")
    Call<List<Category>> getAllDataCategories();

    @GET("SanPham/getSanPhamPaginationList")
    Call<List<Product>> getAllDataProducts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("SanPham/{maSanPham}")
    Call<Product> getDataProduct(@Path("maSanPham") int maSanPham);

    @GET("SanPhamFillter/getSanPhamFillter")
    Call<List<Product>> getProductFillter(
        @Query("page") int page,
        @Query("limit") int limit,
        @Query("input") String input
    );

    @GET("SanPham/DanhMuc/{maDanhMuc}")
    Call<List<Product>> getAllDataProductByCategory(@Path("maDanhMuc") int maDanhMuc);

    @GET("SanPham/Apple")
    Call<List<Product>> getAllDataApple();

    @GET("SanPham/ghiChu/{ghiChu}")
    Call<List<Product>> getDataProductsWithCategory(@Path("ghiChu") String ghiChu);

    //api/KhachHang/sonnguyen/tanhai123
    // login
    @GET("KhachHang/{tenDangNhap}/{matKhau}")
    Call<Customer> getDataLogin(@Path("tenDangNhap") String tenDangNhap,
                                @Path("matKhau") String matKhau);

    @GET("KhachHang/getCustomer/{tenDangNhap}")
    Call<Customer> getCustomerByUserName(@Path("tenDangNhap") String input);

    // Update customer
    @PUT("KhachHang/{maKhachHang}")
    Call<Customer> putCustomer(@Path("maKhachHang") int maKhachHang, @Body  Customer customer);

    @POST("KhachHang")
    Call<Customer> postCustomer(@Body Customer customer);


    //Post hóa đơn
    @POST("HoaDon/")
    Call<Invoice> postInvoice(@Body Invoice invoice);

    @GET("HoaDon/{maKhachHang}/{ghiChu}")
    Call<List<Invoice>> getDataListInvoice(@Path("maKhachHang")int maKhachHang, @Path("ghiChu") String ghiChu);

    @PUT("HoaDon/{maHoaDon}")
    Call<Invoice> putInvoice(@Path("maHoaDon") int maHoaDon, @Body Invoice invoice);

    @POST("CTHoaDon/")
    Call<DetailInvoice> postDetailInvoice (@Body DetailInvoice detailInvoice);

    @GET("CTHoaDon/{maHoaDon}")
    Call<List<ProductManager>> getInvoiceStatus(@Path("maHoaDon")int maHoaDon);

    @GET("TinTuc/getTinTucPaginationList")
    Call<List<News>> getAllDataNews(
            @Query("page") int page,
            @Query("limit") int limit
    );

    //
    @GET("Apriori/{tenSanPham}")
    Call<List<Product>> getDataDoApriori(@Path("tenSanPham")String tenSanPham);

}
