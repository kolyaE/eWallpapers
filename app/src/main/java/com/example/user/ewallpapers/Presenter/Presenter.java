package com.example.user.ewallpapers.Presenter;

import com.example.user.ewallpapers.MVPContract;
import com.example.user.ewallpapers.Model.Model;
import com.example.user.ewallpapers.WallpapersItem;

import java.util.List;

public class Presenter implements MVPContract.IPresenter {
    private static final String TAG = "Presenter";
    private MVPContract.IView iView;
    private MVPContract.IModel iModel;

    public Presenter(MVPContract.IView iView) {
        this.iView = iView;
        this.iModel = new Model();
    }

    @Override
    public void getContentFromModel() {
        List<WallpapersItem> itemsGot = iModel.getContentFromServer();
        if (itemsGot != null) {
            onContentSuccess(itemsGot);
        } else {
            onContentError();
        }
    }

    @Override
    public void onContentSuccess(List<WallpapersItem> items) {
        iView.makeRecyclerView(items);
    }

    @Override
    public void onContentError() {

    }
}
