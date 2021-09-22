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

import java.util.List;

public class CategoryAdapterInFragment extends RecyclerView.Adapter<CategoryAdapterInFragment.ViewHolder> {
    private List<Category> listCategory;
    private Context context;
    private CategoryAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public  void setOnItemClickListener(CategoryAdapter.OnItemClickListener listener){
        onItemClickListener  = listener;
    }
    public CategoryAdapterInFragment(List<Category> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_in_fragment_layout,parent,false);

        return new ViewHolder(view, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterInFragment.ViewHolder holder, int position) {
        Category category = listCategory.get(position);
        if(category ==null)
        {
            return;
        }
        Picasso.get().load(category.getLogoTungSanPham()).error(R.drawable.notfound).into(holder.imgCategory);
        //holder.imgCategory.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,category.getLogoTungSanPham()));
        if(category.getGhiChu().equals("DienThoai"))
        {
            holder.tvnaameCategory.setText("Điện thoại\n"+category.getTenDanhMuc());
        }
        if(category.ghiChu.equals("Laptop")) {
            holder.tvnaameCategory.setText("Laptop \n"+ category.getTenDanhMuc());
        }
        if(category.ghiChu.equals("PhuKien")) {
            holder.tvnaameCategory.setText("Phụ kiện \n"+ category.getTenDanhMuc());
        }
    }

    @Override
    public int getItemCount() {
        if(listCategory !=null)
        {
            return listCategory.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imgCategory;
        TextView tvnaameCategory;
        public ViewHolder(@NonNull View itemView, final CategoryAdapter.OnItemClickListener listener) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategoryInFragment);
            tvnaameCategory = itemView.findViewById(R.id.tvNameCategoryInFragment);
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
