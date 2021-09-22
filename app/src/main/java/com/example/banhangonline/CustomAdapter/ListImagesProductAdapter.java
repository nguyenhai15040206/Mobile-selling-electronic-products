package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.ConvertStringToBitmapFromAsscess;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListImagesProductAdapter extends RecyclerView.Adapter<ListImagesProductAdapter.ViewHolder> {

    private List<String> listBangMau;
    Context context;

    public ListImagesProductAdapter(List<String> listBangMau, Context context) {
        this.listBangMau = listBangMau;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bangmau_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListImagesProductAdapter.ViewHolder holder, int position) {
        String bangMau = listBangMau.get(position);
        if(bangMau!=null)
        {
            Picasso.get().load(bangMau).error(R.drawable.notfound).into(holder.imageView);
            //holder.imageView.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,bangMau));
        }
    }

    @Override
    public int getItemCount() {
        if(listBangMau!=null)
        {
            return listBangMau.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgBangMauDetails);
        }
    }
}
