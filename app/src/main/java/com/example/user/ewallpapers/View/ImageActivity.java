package com.example.user.ewallpapers.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.ewallpapers.R;
import com.squareup.picasso.Picasso;


public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";
    ImageView googleImage;
    TextView jsonphText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        googleImage = (ImageView) findViewById(R.id.google_image);
        jsonphText = (TextView) findViewById(R.id.jsonph_text);
        Picasso.with(this).load(getIntent().getStringExtra("image_url")).into(googleImage);
        jsonphText.setText(getIntent().getStringExtra("text"));
    }
}
