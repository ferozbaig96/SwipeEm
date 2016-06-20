package com.example.fbulou.swipeem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LikeButton left, right;
    public static MyAppAdapter myAppAdapter;
    public static MyViewHolder viewHolder;
    private SwipeFlingAdapterView flingContainer;

    static int toolbarHeight;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        left = (LikeButton) findViewById(R.id.left);
        right = (LikeButton) findViewById(R.id.right);

        showSwipeCards();

        //TODO
        /*Intent i = new Intent(this, ShippingActivity.class);
        startActivity(i);*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        toolbarHeight = toolbar.getHeight();
    }

    public List<Information> loadWishlistPref() {       //loads wishlist products and returns a List of it
        SharedPreferences sharedPreferences = getSharedPreferences("mPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonPreferences = sharedPreferences.getString("mWishlistProductsLists", "");

        //For Collections only
        Type listOfInformationObject = new TypeToken<List<Information>>() {
        }.getType();

        List<Information> informationList = gson.fromJson(jsonPreferences, listOfInformationObject);

        return informationList;
    }

    public void saveWishlistPref(Information addedProduct) {        //saves wishlist products in an ArrayList
        List<Information> myWishlist = loadWishlistPref();
        if (myWishlist == null)                 // if Wishlist is empty
            myWishlist = new ArrayList<>();     // Create Wishlist ArrayList

        myWishlist.add(addedProduct);

        SharedPreferences.Editor spEditor = getSharedPreferences("mPrefs", MODE_PRIVATE).edit();      // should use getPreferences for a single value
        Gson gson = new Gson();
        String json = gson.toJson(myWishlist);

        spEditor.putString("mWishlistProductsLists", json);
        spEditor.apply();
    }

    public void addToWishlist(String imagePath, String description) {
        Information information = new Information();
        information.path = imagePath;
        information.desc = description;
        saveWishlistPref(information);
    }

    private void showSwipeCards() {

        //add the view via xml or programmatically
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        final ArrayList<Data> al = new ArrayList<>();
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "Image 1"));
        al.add(new Data("http://i.imgur.com/Lq7dyu0.jpg", "Image 2"));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "Image 3"));
        al.add(new Data("http://i.imgur.com/Lq7dyu0.jpg", "Image 4"));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "Image 5"));

        //set the listener and the adapter
        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            String topView_imagePath, topView_description;

            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)

                topView_description = al.get(0).getDescription();           //to get the topmost view's description
                topView_imagePath = al.get(0).getImagePath();             //to get the topmost view's imagePath

                Log.e("LIST", "removed object!");
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();


            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast toast = Toast.makeText(MainActivity.this, "Added to Wishlist!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, toolbarHeight);
                toast.show();

                addToWishlist(topView_imagePath, topView_description);
            }


            int i = 0;

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here


                if (itemsInAdapter == 0) {
                    right.setEnabled(false);
                    left.setEnabled(false);
                } else {
                    i++;
                    al.add(new Data("http://placehold.it/120x120&text=NewImage" + i, "New Image " + i));
                    right.setEnabled(true);
                    left.setEnabled(true);
                }
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //  Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        right.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                unLiked(likeButton);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(true);
                flingContainer.getTopCardListener().selectRight();

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(1);
            }
        });


        left.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                unLiked(likeButton);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(true);
                flingContainer.getTopCardListener().selectLeft();

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(1);
            }
        });

    }


    public static class MyViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;
    }

    public class MyAppAdapter extends BaseAdapter {

        public List<Data> productList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.productList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_swipe, parent, false);
                // configure view holder
                viewHolder = new MyViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(productList.get(position).getDescription() + "");

            Glide.with(MainActivity.this)
                    .load(productList.get(position).getImagePath())
                    .centerCrop()
                    .into(viewHolder.cardImage);

            return rowView;
        }
    }

    //MENUS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_wishlist) {
            Intent i = new Intent(this, WishlistActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
