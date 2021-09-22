package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.ConvertStringToBitmapFromAsscess;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsAdapterFragment extends RecyclerView.Adapter<ProductsAdapterFragment.ViewHolder> {

    private List<Product> listProducts;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ProductsAdapterFragment(List<Product> listProducts, Context context) {
        this.listProducts = listProducts;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_fragment_layout,parent,false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  ProductsAdapterFragment.ViewHolder holder, int position) {
        Product product = listProducts.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#0,000");
        if(product == null)
        {
            return;
        }
        Picasso.get().load(product.hinhMinhHoa).error(R.drawable.notfound).into(holder.imageView);
        //holder.imageView.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,product.hinhMinhHoa));
        if(product.tenSanPham.length() <= 30)
        {
            holder.tvProductName.setText(product.tenSanPham);
        }
        else
        {
            holder.tvProductName.setText(product.tenSanPham.substring(0,30)+ "...");
        }
        if(product.giamGia !=0)
        {
            holder.tvPriceProduct.setText(""+decimalFormat.format(product.donGia - product.giamGia)+"đ");
        }
        else
        {
            holder.tvPriceProduct.setText(""+decimalFormat.format(product.donGia)+"đ");
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

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView tvPriceProduct, tvProductName;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProductFragment);
            tvPriceProduct = itemView.findViewById(R.id.tvUnitPriceProductFragment);
            tvProductName = itemView.findViewById(R.id.tvNameProductFragment);
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
