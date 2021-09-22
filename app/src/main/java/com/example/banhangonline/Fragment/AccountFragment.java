package com.example.banhangonline.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Activity.CartActivity;
import com.example.banhangonline.Activity.InfomationCustomerActivity;
import com.example.banhangonline.Activity.LoginActivity;
import com.example.banhangonline.Activity.OderManagerActivity;
import com.example.banhangonline.Model.Customer;
import com.example.banhangonline.R;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private Button btnLogin;
    public static CardView cardView;
    public static TextView tvNameCustomerLogin,tvPhoneNumberCustomer;
    public static LinearLayout linearLayout_NoAccount;
    private ImageView imgInfomationCustomer,btnCartAccount,imgLogoutAccount;
    private SharedPreferences sharedPreferences;
    private ImageButton btnChoXacNhan,btnChoLayHang,btnDangGiao, btnDaThanhToan;

    private Customer customer;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        Mapping(view);
        setLayout();
        btnCartAccount.setOnClickListener(this);
        imgLogoutAccount.setOnClickListener(this);
        btnChoXacNhan.setOnClickListener(this);
        btnChoLayHang.setOnClickListener(this);
        btnDangGiao.setOnClickListener(this);
        //

    }
    public void setLayout(){
        if(HomeFragment.customer != null)
        {
            cardView.setVisibility(View.VISIBLE);
            linearLayout_NoAccount.setVisibility(View.GONE);
            tvNameCustomerLogin.setVisibility(View.VISIBLE);
            tvPhoneNumberCustomer.setVisibility(View.VISIBLE);
            tvNameCustomerLogin.setText(""+HomeFragment.customer.getCustomerName());
            tvPhoneNumberCustomer.setText(""+HomeFragment.customer.getCustomerPhoneNumber());
            imgInfomationCustomer.setOnClickListener(this);
        }
        else {

            linearLayout_NoAccount.setVisibility(View.VISIBLE);
            tvNameCustomerLogin.setVisibility(View.GONE);
            tvPhoneNumberCustomer.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            btnLogin.setOnClickListener(this);
        }
    }

    public  void Mapping(View view){
        btnLogin =  view.findViewById(R.id.btnLoginOrRegister);
        cardView = view.findViewById(R.id.cardViewLogin);
        tvNameCustomerLogin = view.findViewById(R.id.tvNameCustomerLogin);
        tvPhoneNumberCustomer = view.findViewById(R.id.tvPhoneNumberCustomer);
        linearLayout_NoAccount = view.findViewById(R.id.linearLayout_NoAccount);
        imgInfomationCustomer = view.findViewById(R.id.ImgInfomationCusotmerAcc);
        btnCartAccount = view.findViewById(R.id.ImgCartViewAccount);
        imgLogoutAccount = view.findViewById(R.id.btnLogoutAccount);
        btnChoXacNhan = view.findViewById(R.id.btnChoXacNhan);
        btnChoLayHang = view.findViewById(R.id.btnChoLayHang);
        btnDangGiao = view.findViewById(R.id.btnDangGiaoHang);
        btnDaThanhToan = view.findViewById(R.id.btnDaThanhToan);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.ImgInfomationCusotmerAcc:
                Intent intent = new Intent(getContext(), InfomationCustomerActivity.class);
                intent.putExtra("CustomerCode",HomeFragment.customer.getCustomerCode());
                intent.putExtra("CustomerName",HomeFragment.customer.getCustomerName());
                intent.putExtra("CustomerPhoneNumber",HomeFragment.customer.getCustomerPhoneNumber());
                intent.putExtra("CusomerEmail",HomeFragment.customer.getCustomerEmail());
                intent.putExtra("CustomerAddress",HomeFragment.customer.getCustomerAdress());
                intent.putExtra("CustomerUsername",HomeFragment.customer.getCutomerUserName());
                startActivity(intent);
                CustomIntent.customType(getContext(),"left-to-right");
                break;
            case R.id.btnLoginOrRegister:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        CustomIntent.customType(getContext(),"left-to-right");
                    }
                },100);
                break;
            case R.id.ImgCartViewAccount:
                if(HomeFragment.customer!=null)
                {
                    Intent intentCart = new Intent(getContext(), CartActivity.class);
                    startActivity(intentCart);
                    CustomIntent.customType(getContext(),"left-to-right");
                }else{
                    Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    CustomIntent.customType(getContext(),"left-to-right");
                }
                break;
            case R.id.btnLogoutAccount:
                if(HomeFragment.customer!=null)
                {
                    Intent intentLogout = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogout);
                    CustomIntent.customType(getContext(),"left-to-right");
                    HomeFragment.customer = null;
                    sharedPreferences = getActivity().getSharedPreferences(LoginActivity.DATA_LOGIN,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("userName");
                    editor.remove("password");
                    editor.commit();
                }
                break;
            case R.id.btnChoXacNhan:
                if(HomeFragment.customer!=null)
                {
                    Intent intentProductManager = new Intent(getContext(), OderManagerActivity.class);
                    intentProductManager.putExtra("Data","Chờ xác nhận");
                    startActivity(intentProductManager);
                    CustomIntent.customType(getContext(),"left-to-right");
                }else{
                    Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    CustomIntent.customType(getContext(),"left-to-right");
                }
                break;
            case R.id.btnChoLayHang:
                if(HomeFragment.customer!=null)
                {
                    Intent intentProductManager = new Intent(getContext(), OderManagerActivity.class);
                    intentProductManager.putExtra("Data","Đã xác nhận");
                    startActivity(intentProductManager);
                    CustomIntent.customType(getContext(),"left-to-right");
                }else{
                    Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    CustomIntent.customType(getContext(),"left-to-right");
                }
                break;
            case R.id.btnDangGiaoHang:
                if(HomeFragment.customer!=null)
                {
                    Intent intentProductManager = new Intent(getContext(), OderManagerActivity.class);
                    intentProductManager.putExtra("Data","Đang giao");
                    startActivity(intentProductManager);
                    CustomIntent.customType(getContext(),"left-to-right");
                }else{
                    Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    CustomIntent.customType(getContext(),"left-to-right");
                }
                break;
            case R.id.btnDaThanhToan:
                if(HomeFragment.customer !=null)
                {
                    Intent intentProductManager = new Intent(getContext(), OderManagerActivity.class);
                    intentProductManager.putExtra("Data","Đang giao");
                    startActivity(intentProductManager);
                    CustomIntent.customType(getContext(),"left-to-right");
                }
                else{
                    Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    CustomIntent.customType(getContext(),"left-to-right");
                }
                break;

        }
    }
}