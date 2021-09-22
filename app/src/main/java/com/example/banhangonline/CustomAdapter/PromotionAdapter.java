package com.example.banhangonline.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.R;

import java.util.List;

public class PromotionAdapter extends  RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
    private List<String> promotion;
    public PromotionAdapter(List<String> promotion) {
        this.promotion = promotion;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  PromotionAdapter.ViewHolder holder, int position) {
        String sale = promotion.get(position);
        if(sale == null)
            return;
        holder.radioButton.setText(sale);
    }

    @Override
    public int getItemCount() {
        if(promotion !=null)
        {
            return promotion.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RadioButton radioButton;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.ckbSale);
        }
    }
}
