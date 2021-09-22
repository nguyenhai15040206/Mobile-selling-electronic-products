package com.example.banhangonline.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.News;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsAdapter extends  RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<News> newsList;
    Context context;

    public NewsAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        Picasso.get().load(news.anhMinhHoa).error(R.drawable.notfound).into(holder.img);
        holder.tvTen.setText(news.getTenTinTuc());
        holder.tvNoiDung.setText(news.getNoiDung());
        holder.tvNgyDang.setText(news.getNgayDang());
    }

    @Override
    public int getItemCount() {
        if(newsList!=null)
        {
            return newsList.size();
        }
        return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvNoiDung, tvNgyDang;
        ImageView img;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvNewsName);
            tvNgyDang = itemView.findViewById(R.id.tvNgayDang);
            tvNoiDung = itemView.findViewById(R.id.tvNewNoiDung);
            img = itemView.findViewById(R.id.imgNews);
        }
    }
}
