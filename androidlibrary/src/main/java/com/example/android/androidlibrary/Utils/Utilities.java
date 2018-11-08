package com.example.android.androidlibrary.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utilities {

    public static void loadImageProfile(Context context, String pathImage, ImageView imageView){
        Picasso.with(context).load(pathImage).into(imageView);
    }

}
