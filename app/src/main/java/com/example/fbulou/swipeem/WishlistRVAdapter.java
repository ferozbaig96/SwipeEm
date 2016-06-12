package com.example.fbulou.swipeem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class WishlistRVAdapter extends RecyclerView.Adapter<WishlistRVAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public interface MyOnClickListener {
        public void onClicked(int position);
    }

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

    void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener { // For onClick Events

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.id_RVtitle);
            icon = (ImageView) itemView.findViewById(R.id.id_RVicon);

            itemView.setOnClickListener(this);  //setting clickListener on the whole itemView
            itemView.setOnLongClickListener(this);    //setting longClickListener on the whole itemView
        }

        @Override
        public void onClick(View v) {

           /* int position = getAdapterPosition();

            if (position != -1 && position < data.size()) {
                Toast.makeText(WishlistActivity.Instance.getInstance(), "Item deleted at position : " + position, Toast.LENGTH_SHORT).show();
                delete(position);
            }*/

            WishlistActivity.Instance.onClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(WishlistActivity.Instance.getInstance(), "Long clicked!", Toast.LENGTH_SHORT).show();
            return true;    //explicitly set to true to make it work
        }
    }
}

