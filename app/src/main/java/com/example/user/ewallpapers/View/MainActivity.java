package com.example.user.ewallpapers.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.ewallpapers.MVPContract;
import com.example.user.ewallpapers.Presenter.Presenter;
import com.example.user.ewallpapers.R;
import com.example.user.ewallpapers.Model.WallpapersItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MVPContract.IView{
    private final static String TAG = "MainActivity";

    RecyclerView mWallpapersRecyclerView;
    MVPContract.IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWallpapersRecyclerView = (RecyclerView) findViewById(R.id.wallpapers_recycler_view);

        if (presenter == null) {
            presenter = new Presenter(this);
        }
        presenter.getContentFromModel();
    }

    @Override
    public void makeRecyclerView(List<WallpapersItem> rvItems) {
        mWallpapersRecyclerView.setAdapter(new WallpapersAdapter(rvItems));
    }

    private class WallpapersAdapter extends RecyclerView.Adapter<WallpapersHolder> {
        private List<WallpapersItem> mWallpapersItems;

        public WallpapersAdapter(List<WallpapersItem> items) {
            this.mWallpapersItems = items;
        }

        @Override
        public WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_recycler_view, parent, false);
            return new WallpapersHolder(view);
        }

        @Override
        public void onBindViewHolder(WallpapersHolder holder, int position) {
            WallpapersItem item = mWallpapersItems.get(position);
            Picasso.with(MainActivity.this).load(item.getUrl()).into(holder.itemImageView);
        }

        @Override
        public int getItemCount() {
            return mWallpapersItems.size();
        }
    }

    private class WallpapersHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;

        public WallpapersHolder(View itemView) {
            super(itemView);
            itemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }
    }
}
