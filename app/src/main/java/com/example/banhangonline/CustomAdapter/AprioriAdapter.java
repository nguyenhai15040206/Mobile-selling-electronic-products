package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Activity.CartActivity;
import com.example.banhangonline.Database.DataBaseHelper;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class AprioriAdapter extends RecyclerView.Adapter<AprioriAdapter.ViewHolder> {
    private List<Product> listProducts;
    private Context context;
    private DataBaseHelper dataBaseHelper;

    public AprioriAdapter(List<Product> listProducts, Context context) {
        this.listProducts = listProducts;
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_doapriori_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Product product = listProducts.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#0,000");
        if(product ==null)
        {
            return;
        }
        Picasso.get().load(product.hinhMinhHoa).error(R.drawable.notfound).into(holder.imgImageView);
        if(product.tenSanPham.length() <=35)
        {
            holder.tvName.setText(product.tenSanPham);
        }else{
            holder.tvName.setText(product.tenSanPham.substring(0,32)+"...");
        }
        if(product.giamGia != 0) {
            holder.tvUnitPrice.setText("" + decimalFormat.format(product.donGia - product.giamGia) + "đ");
        }
        else
        {
            holder.tvUnitPrice.setText("" + decimalFormat.format(product.donGia) + "đ");
        }
        btnAddCartClick(holder,position);

    }

    @Override
    public int getItemCount() {
        if(listProducts!=null)
        {
            return listProducts.size();
        }
        return 0;
    }

    public void btnAddCartClick(ViewHolder holder, int position)
    {
        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = CartActivity.listProductDoApriori.get(position);
                int productAmount =1;
                double totalMoney = productAmount * (product.donGia - product.giamGia);
                dataBaseHelper.InsertProduct(product.maSanPham, product.tenSanPham, product.hinhMinhHoa, productAmount, product.donGia, product.giamGia, totalMoney, product.ghiChu,product.tenDanhMuc,false);
                Toast.makeText(context.getApplicationContext(),"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                CartActivity.cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
                CartActivity.cartAdapter.notifyDataSetChanged();

                CartActivity.listProductDoApriori.remove(product);
                CartActivity.aprioriAdapter.notifyDataSetChanged();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgImageView;
        private TextView tvName;
        private TextView tvUnitPrice;
        private AppCompatButton btnAddCart;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgImageView = itemView.findViewById(R.id.imgDoApriori);
            tvName = itemView.findViewById(R.id.tvNameProductDoApriori);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPriceProductDoApriori);
            btnAddCart = itemView.findViewById(R.id.btnAddCartDoApriori);
        }
    }

}
