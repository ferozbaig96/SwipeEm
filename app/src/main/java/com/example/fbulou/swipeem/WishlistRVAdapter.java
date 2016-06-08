package com.example.fbulou.swipeem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class WishlistRVAdapter extends RecyclerView.Adapter<WishlistRVAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public WishlistRVAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = inflater.inflate(R.layout.item_wishlist, parent, false);
        return new MyViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Information curObj = data.get(position);
        holder.title.setText(curObj.desc);

        //TODO
        int size = holder.icon.getContext().getResources().getDimensionPixelSize(R.dimen.item_height);

        Glide.with(WishlistActivity.Instance.getInstance())
                .load(curObj.path)
                .override(size, size)
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .into(holder.icon);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.id_RVtitle);
            icon = (ImageView) itemView.findViewById(R.id.id_RVicon);
        }
    }
}

