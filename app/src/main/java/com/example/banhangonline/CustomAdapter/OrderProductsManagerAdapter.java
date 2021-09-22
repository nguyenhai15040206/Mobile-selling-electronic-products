package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.ProductManager;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class OrderProductsManagerAdapter extends  RecyclerView.Adapter<OrderProductsManagerAdapter.ViewHolder> {
    private List<ProductManager> listProductManagers;
    private Context context;

    public OrderProductsManagerAdapter(List<ProductManager> listProductManagers) {
        this.listProductManagers = listProductManagers;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_productsmanager_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ProductManager productManager = listProductManagers.get(position);
        if(productManager ==null){
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0,000");
        Picasso.get().load(productManager.getHinhAnh()).error(R.drawable.notfound).into(holder.imgViewProductManager);
        holder.tvNameProductsManager.setText(productManager.getTenSanPham());
        holder.tvAmountProductManager.setText("Số lượng "+ productManager.getSoLuong());
        if(productManager.getGiamGia() != 0) {
            holder.tvUnitPriceManager.setText("" + decimalFormat.format(productManager.getSoLuong()* productManager.getDonGia() -productManager.getSoLuong()*  productManager.getGiamGia()) + "đ");
            holder.tvProductManagerSale.setText("" + (decimalFormat.format(productManager.getDonGia() * productManager.getSoLuong())) + "đ");
            holder.tvProductManagerSale.setPaintFlags(holder.tvProductManagerSale.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            holder.tvUnitPriceManager.setText("" + decimalFormat.format(productManager.getSoLuong()* productManager.getDonGia() -productManager.getSoLuong()*  productManager.getGiamGia()) + "đ");
            holder.tvProductManagerSale.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(listProductManagers!=null)
        {
            return listProductManagers.size();
        }
        return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameProductsManager,tvAmountProductManager,tvProductManagerSale,tvUnitPriceManager;
        ImageView imgViewProductManager;
        TextView tvDate;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvNameProductsManager = itemView.findViewById(R.id.tvNameProductsManager);
            tvAmountProductManager = itemView.findViewById(R.id.tvAmountProductManager);
            tvProductManagerSale = itemView.findViewById(R.id.tvProductManagerSale);
            tvUnitPriceManager = itemView.findViewById(R.id.tvUnitPriceManager);
            imgViewProductManager = itemView.findViewById(R.id.imgProductManager);
        }
    }
}
