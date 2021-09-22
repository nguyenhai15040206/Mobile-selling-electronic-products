package com.example.banhangonline.Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PanigationScrollListener extends RecyclerView.OnScrollListener {
    private GridLayoutManager gridLayoutManager;

    public PanigationScrollListener(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibaleItemcout = gridLayoutManager.getChildCount();
        int totalItemCount = gridLayoutManager.getItemCount();
        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

        // giả xử dữ liệu đang load hoặc đến page cuối cùng
        if(isLoading() || isLastPage())
        {
            return;
        }
        if(firstVisibleItemPosition>=0 && (visibaleItemcout+firstVisibleItemPosition)>=totalItemCount)
        {
             loadMoreItems();
        }
    }

    public  abstract  void loadMoreItems();
    public  abstract  boolean isLoading();
    public  abstract  boolean isLastPage();
}
