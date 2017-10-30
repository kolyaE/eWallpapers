package com.example.user.ewallpapers.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.user.ewallpapers.MVPContract;
import com.example.user.ewallpapers.WallpapersItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Model implements MVPContract.IModel {
    private static final String TAG = "Model";
    private List<WallpapersItem> mItems;

    @Override
    public List<WallpapersItem> getContentFromServer() {
        FetchItemsTask task = new FetchItemsTask();
        task.execute();
        try {
            mItems = task.get();
        } catch (InterruptedException | ExecutionException ie) {
            Log.e(TAG, "InterruptedException", ie);
        }
        return mItems;
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<WallpapersItem>> {

        @Override
        protected List<WallpapersItem> doInBackground(Void... params) {
            return new GoogleCustomSearchFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<WallpapersItem> list) {
            mItems = list;
        }
    }
}
