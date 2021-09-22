package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.Category;
import com.example.banhangonline.Model.ConvertStringToBitmapFromAsscess;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.List;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private OnItemClickListener onItemClickListener;
    private List<Category> listcategory;
    private Context context;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener  = listener;
    }

    public  CategoryAdapter(Context context, List<Category> listcategory)
    {
        this.listcategory = listcategory;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        return new CategoryHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        Category category = listcategory.get(position);
        if(category ==null) {
            return;
        }
        Picasso.get().load(category.getLogoTungSanPham()).error(R.drawable.notfound).into(holder.imageViewCategory);
        //holder.imageViewCategory.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,category.logoTungSanPham));
        if(category.ghiChu.equals("DienThoai")) {
            holder.tvTitle.setText("Điện thoại \n"+ category.getTenDanhMuc());
        }
        if(category.ghiChu.equals("Laptop")) {
            holder.tvTitle.setText("Laptop \n"+ category.getTenDanhMuc());
        }
        if(category.ghiChu.equals("PhuKien")) {
            holder.tvTitle.setText("Phụ kiện \n"+ category.getTenDanhMuc());
        }

    }

    @Override
    public int getItemCount() {
        if(listcategory !=null)
        {
            return listcategory.size();
        }
        return 0;
    }

    public  class CategoryHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewCategory;
        private TextView tvTitle;
        public CategoryHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageCategory);
            tvTitle = itemView.findViewById(R.id.txtCategory);
            itemView.setOnClickListener((v -> {
                if(listener !=null)
                {
                    int position = getAdapterPosition();
                    if(position !=RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(position);
                    }
                }
            }));
        }
    }
}
