package com.example.fbulou.swipeem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class WishlistRVAdapter extends RecyclerView.Adapter<WishlistRVAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    static Map<Integer, ImageView> posTickMap = new HashMap<>();     // ImageView contains tick icon
    boolean longClickActivated = false;

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

        Glide.with(WishlistActivity.getInstance())
                .load(curObj.path)
                .placeholder(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)        //cache original sized image
                .centerCrop()
                .into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

   /* void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);

        //Deleting an existing item. Hence myWishlist is non-empty
        List<Information> myWishlist = WishlistActivity.getInstance().loadWishlistPref();
        //Deleting item at [position] from the list
        myWishlist.remove(position);
        //Saving back to the WishlistPrefs
        WishlistActivity.getInstance().saveWishlistPref(myWishlist);
    }*/

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener { // For onClick Events

        TextView title;
        ImageView icon;
        ImageView tick;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.id_RVtitle);
            icon = (ImageView) itemView.findViewById(R.id.id_RVicon);
            tick = (ImageView) itemView.findViewById(R.id.tick);

            itemView.setOnClickListener(this);  //setting clickListener on the whole itemView
            itemView.setOnLongClickListener(this);    //setting longClickListener on the whole itemView
        }

        @Override
        public void onClick(View v) {

            if (longClickActivated) {
                int position = getAdapterPosition();
                performSelections(position);
            } else
                WishlistActivity.Instance.onClicked(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {

          /*  int position = getAdapterPosition();

            if (position != -1 && position < data.size()) {
                Toast.makeText(WishlistActivity.getInstance(), "Item deleted at position : " + position, Toast.LENGTH_SHORT).show();
                delete(position);
            }*/

            longClickActivated = true;
            int position = getAdapterPosition();
            performSelections(position);
            return true;         //explicitly set to true to make it work
        }

        private void performSelections(int position) {
            if (position != -1 && position < data.size()) {
                if (data.get(position).isSelected) {

                    posTickMap.remove(position);
                    data.get(position).isSelected = false;

                    SupportAnimator animator = showCircularReveal(tick, 200, false);
                    animator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                        }

                        @Override
                        public void onAnimationEnd() {
                            tick.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel() {
                        }

                        @Override
                        public void onAnimationRepeat() {
                        }
                    });
                    animator.start();

                    Log.e("TAG", "Unselected at pos " + position);

                } else {

                    posTickMap.put(position, tick);
                    data.get(position).isSelected = true;

                    SupportAnimator animator = showCircularReveal(tick, 200, true);
                    animator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            tick.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd() {
                        }

                        @Override
                        public void onAnimationCancel() {
                        }

                        @Override
                        public void onAnimationRepeat() {
                        }
                    });
                    animator.start();

                    Log.e("TAG", "Selected at pos " + position);
                }
            }

            if (posTickMap.size() == 0) {
                longClickActivated = false;
                WishlistActivity.getInstance().mMenu.findItem(R.id.action_delete_long_press).setVisible(false);
                WishlistActivity.getInstance().getSupportActionBar().setTitle("Your Wishlist");
                WishlistActivity.getInstance().getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            } else {
                WishlistActivity.getInstance().mMenu.findItem(R.id.action_delete_long_press).setVisible(true);
                WishlistActivity.getInstance().getSupportActionBar().setTitle("" + posTickMap.size());
                WishlistActivity.getInstance().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
    }

    public SupportAnimator showCircularReveal(View v, long duration, boolean show) {

        int cx = (v.getLeft() + v.getRight()) / 2;
        int cy = (v.getTop() + v.getBottom()) / 2;
        float radius = (float) Math.hypot(v.getWidth() / 2, v.getHeight() / 2);
        SupportAnimator animator;

        if (show)
            animator = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, radius);
        else
            animator = ViewAnimationUtils.createCircularReveal(v, cx, cy, radius, 0);

        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        return animator;
    }

}
