package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.ConvertStringToBitmapFromAsscess;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsAdapterRcvHolder>  {
    private List<Product> listProducts;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ProductsAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
    }
    @NonNull
    @Override
    public ProductsAdapterRcvHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
            return new ProductsAdapterRcvHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductsAdapterRcvHolder holder, int position) {
            Product product = listProducts.get(position);
            DecimalFormat decimalFormat = new DecimalFormat("#0,000");
            if(product ==null)
            {
                return;
            }
            Picasso.get().load(product.hinhMinhHoa).error(R.drawable.notfound).into(holder.imageView);
            //holder.imageView.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,product.hinhMinhHoa));
            if(product.tenSanPham.length() <=30) {
                holder.tvNameProduct.setText(product.tenSanPham);
            }
            else
            {
                holder.tvNameProduct.setText(product.tenSanPham.substring(0,30)+"...");
            }
            if(product.giamGia != 0) {
                holder.tvUnitPriceProduct.setText("" + decimalFormat.format(product.donGia - product.giamGia) + "đ");
                holder.tvDiscountProduct.setText("" + (decimalFormat.format(product.donGia)) + "đ");
                holder.tvDiscountProduct.setPaintFlags(holder.tvDiscountProduct.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else
            {
                holder.tvUnitPriceProduct.setText("" + decimalFormat.format(product.donGia) + "đ");
                holder.tvDiscountProduct.setText("");
            }
    }

    @Override
    public int getItemCount() {
        if(listProducts !=null)
        {
            return listProducts.size();
        }
        return 0;
    }

    public class ProductsAdapterRcvHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvNameProduct;
        private TextView tvUnitPriceProduct;
        private TextView tvDiscountProduct;
        public ProductsAdapterRcvHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener ) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvUnitPriceProduct = itemView.findViewById(R.id.tvUnitPriceProduct);
            tvDiscountProduct = itemView.findViewById(R.id.tvDiscountProduct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position !=RecyclerView.NO_POSITION)
                    {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
