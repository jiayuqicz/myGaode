package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    int[] images = new int[] {
            R.drawable.actionbar1,
            R.drawable.actionbar2,
    };
    int currentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView view = (ImageView) findViewById(R.id.image1);

        view.setImageResource(images[0]);
        view.setImageAlpha(128);


    }
}
