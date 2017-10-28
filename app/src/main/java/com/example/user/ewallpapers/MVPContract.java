package com.example.user.ewallpapers;

import com.example.user.ewallpapers.Model.WallpapersItem;

import java.util.List;

public class MVPContract {

    public interface IView {
        void makeRecyclerView(List<WallpapersItem> rvItems);
    }

    public interface IPresenter {
        void getContentFromModel();
        void onContentSuccess(List<WallpapersItem> items);
        void onContentError();
    }

    public interface IModel {
        List<WallpapersItem> getContentFromServer();
    }
}
