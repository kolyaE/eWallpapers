package com.example.user.ewallpapers.Model;

import android.net.Uri;
import android.util.Log;

import com.example.user.ewallpapers.WallpapersItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleCustomSearchFetcher {
    private static final String TAG = "GoogleCSFetcher";
    private static final String GOOGLE_API = "https://www.googleapis.com/customsearch/v1?";
    private static final String API_KEY = "key";
    private static final String API_KEY_VALUE = "AIzaSyCL1aPgHsDC0_AdI51TAptZeWhUU111EW8";
    private static final String CX = "cx";
    private static final String CX_VALUE = "008414728778636002353:mfykwj1r1zq";
    private static final String QUERY = "q";
    private static final String QUERY_VALUE_1 = "forest";
    private static final String SEARCH_TYPE = "searchType";
    private static final String SEARCH_TYPE_VALUE = "image";
    private static final String FIELDS = "fields";
    private static final String FIELDS_VALUE = "items(link,image/thumbnailLink)";
    private static final String START = "start";
    private static final String START_VALUE = "1";
    private static final String JSONPH_API = "https://jsonplaceholder.typicode.com/users";
    private static final String METHOD = "method";
    private static final String METHOD_VALUE = "GET";

    public String getJSONString(String urlSpec) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlSpec).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public List<WallpapersItem> fetchItems() {
        List<WallpapersItem> wallpapersItems = new ArrayList<>();

        try {
            String google_url = Uri.parse(GOOGLE_API)
                    .buildUpon()
                    .appendQueryParameter(API_KEY, API_KEY_VALUE)
                    .appendQueryParameter(CX, CX_VALUE)
                    .appendQueryParameter(QUERY, QUERY_VALUE_1)
                    .appendQueryParameter(SEARCH_TYPE, SEARCH_TYPE_VALUE)
                    .appendQueryParameter(FIELDS, FIELDS_VALUE)
                    .appendQueryParameter(START, START_VALUE)
                    .build().toString();
            JSONObject jsonGoogleBody = new JSONObject(getJSONString(google_url));

            String ph_url = Uri.parse(JSONPH_API)
                    .buildUpon()
                    .appendQueryParameter(METHOD, METHOD_VALUE)
                    .build().toString();
            JSONArray jsonPhBody = new JSONArray(getJSONString(ph_url));

            parseItems(wallpapersItems, jsonGoogleBody, jsonPhBody);

        } catch (IOException ie) {
            Log.e(TAG, "IOException", ie);
        } catch (JSONException je) {
            Log.e(TAG, "JSONException", je);
        }
        return wallpapersItems;
    }

    private void parseItems(List<WallpapersItem> list, JSONObject jsonGoogleObject, JSONArray jsonPhArray) throws IOException, JSONException {
        JSONArray googleItemsArray = jsonGoogleObject.getJSONArray("items");

        for (int i = 0; i < googleItemsArray.length(); i++) {
            JSONObject googleItemJsonObject = googleItemsArray.getJSONObject(i);
            JSONObject imageObject = googleItemJsonObject.getJSONObject("image");
            JSONObject phJsonObject = jsonPhArray.getJSONObject(i);
            WallpapersItem item = new WallpapersItem();
            if (googleItemJsonObject.has("link")) {
                item.setUrlHD(googleItemJsonObject.getString("link"));
            }
            if (imageObject.has("thumbnailLink")) {
                item.setUrl(imageObject.getString("thumbnailLink"));
            }
            if (phJsonObject.has("username")) {
                item.setTitle(phJsonObject.getString("username"));
            }
            list.add(item);
        }
    }
}
