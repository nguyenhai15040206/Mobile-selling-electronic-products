package com.example.banhangonline.CustomAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Activity.CartActivity;
import com.example.banhangonline.Activity.OderManagerActivity;
import com.example.banhangonline.Fragment.HomeFragment;
import com.example.banhangonline.Model.Invoice;
import com.example.banhangonline.Model.ProductManager;
import com.example.banhangonline.R;
import com.google.android.material.transition.Hold;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHodler> {
    List<Invoice> invoiceList;
    List<ProductManager> productManagerList;
    OrderProductsManagerAdapter orderProductsManagerAdapter;
    Context context;
    Invoice invoice;

    public InvoiceAdapter(List<Invoice> invoiceList, Context context) {
        this.invoiceList = invoiceList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon_layout, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InvoiceAdapter.ViewHodler holder, int position) {
        invoice = invoiceList.get(position);
        if (invoice == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        sdf.setTimeZone(tz);
        Date s = null;
        String da = null;
        Date strToDate = null;
        try {
            s = invoice.getNgayBan();
            s = sdf.parse(String.valueOf(invoice.getNgayBan()));
            System.out.println(s);
            sdf = new SimpleDateFormat("dd-MM-yyyy");
            da = sdf.format(s);
            System.out.println(da);
            holder.tvDate.setText("Ngày mua: " + da);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0,000");
        holder.tvSumoney.setText("Tổng tiền: " + decimalFormat.format(invoice.getTongTien()) + "đ");
        if (OderManagerActivity.ghiChu.equalsIgnoreCase("Chờ xác nhận")) {
            holder.tvCancel.setVisibility(View.VISIBLE);
            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.tvCancel.getText().toString().equalsIgnoreCase("Hủy đơn")) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Thông báo!");
                        dialog.setMessage("Bạn có chắc muốn hủy đơn hàng này không?");
                        dialog.setCancelable(false);
                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context,"Ok Bạn",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callApiUpdateInvoice(invoiceList.get(position).getMaHoaDon());
                            }
                        });
                        dialog.show();
                    }
                    if(holder.tvCancel.getText().toString().equalsIgnoreCase("Xác nhận đơn hàng"))
                    {
                        Toast.makeText(context.getApplicationContext(),"Aoooooo",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (OderManagerActivity.ghiChu.equalsIgnoreCase("Đã xác nhận")) {
            holder.tvCancel.setVisibility(View.GONE);
        } else if (OderManagerActivity.ghiChu.equalsIgnoreCase("Đang giao")) {
            holder.tvCancel.setVisibility(View.GONE);
        }
        else if (OderManagerActivity.ghiChu.equalsIgnoreCase("Đã thanh toán")) {
            holder.tvCancel.setVisibility(View.GONE);
        }

        holder.tvDetailsInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maHoaDon = invoiceList.get(position).getMaHoaDon();
                openDialog_InventoryNumber(Gravity.CENTER, maHoaDon);
            }
        });
    }

    private void openDialog_InventoryNumber(int gravity, int maHoaDon) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_details_invoice_layout);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribus = window.getAttributes();
        windowAttribus.gravity = gravity;
        window.setAttributes(windowAttribus);
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        RecyclerView recyclerView = dialog.findViewById(R.id.rcvDialogProductManager);
        productManagerList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        callAPIProductManager(maHoaDon);
        orderProductsManagerAdapter = new OrderProductsManagerAdapter(productManagerList);
        recyclerView.setAdapter(orderProductsManagerAdapter);
        orderProductsManagerAdapter.notifyDataSetChanged();
        AppCompatButton btnOk = dialog.findViewById(R.id.btnOKdiaLogPromanager);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void callAPIProductManager(int invoiceCode) {
        ApiService.apiService.getInvoiceStatus(invoiceCode).enqueue(new Callback<List<ProductManager>>() {
            @Override
            public void onResponse(Call<List<ProductManager>> call, Response<List<ProductManager>> response) {
                productManagerList.clear();
                if (response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        productManagerList.add(new ProductManager(response.body().get(i).getMaSanPham(), response.body().get(i).getTenSanPham(),
                                response.body().get(i).getHinhAnh(), response.body().get(i).getSoLuong(), response.body().get(i).getDonGia(),
                                response.body().get(i).getGiamGia()));
                        orderProductsManagerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductManager>> call, Throwable t) {
                Toast.makeText(context, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callApiUpdateInvoice(int invoiceCode) {
        Invoice invoice = new Invoice();
        invoice.setGhiChu("Đã hủy đơn");
        ApiService.apiService.putInvoice(invoiceCode, invoice).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context.getApplicationContext(), "Hủy đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                    OderManagerActivity.invoiceList.clear();
                    CallApiInvoiceList(HomeFragment.customer.getCustomerCode(), OderManagerActivity.ghiChu);
                    OderManagerActivity.invoiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Kiểm tra lại kết nối Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CallApiInvoiceList(int customerCode, String description) {
        ApiService.apiService.getDataListInvoice(customerCode, description).enqueue(new Callback<List<Invoice>>() {
            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                OderManagerActivity.invoiceList.clear();
                if (response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        OderManagerActivity.invoiceList.add(new Invoice(response.body().get(i).getMaHoaDon(), response.body().get(i).getNgayBan(),
                                response.body().get(i).getNgayGiao(), response.body().get(i).getMaKhachHang(), response.body().get(i).getTienBan(),
                                response.body().get(i).getGiamGia(), response.body().get(i).getTongTien(), response.body().get(i).getGhiChu(),
                                response.body().get(i).getMaNguoiDung(), response.body().get(i).isTinhTrang()));
                        OderManagerActivity.invoiceAdapter.notifyDataSetChanged();
                    }

                } else {
                    OderManagerActivity.linearLayout_ProductsManagerIsNull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (invoiceList != null) {
            return invoiceList.size();
        }
        return 0;
    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvSumoney;
        TextView tvCancel, tvDetailsInvoice;

        public ViewHodler(@NonNull @NotNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvNgayMuaDonHang);
            tvSumoney = itemView.findViewById(R.id.tvTongTienDonHang);
            tvCancel = itemView.findViewById(R.id.tvHuyDonHang);
            tvDetailsInvoice = itemView.findViewById(R.id.tvXemChiTietDH);
        }
    }
}
