package com.thebigoceaan.smartagriculture;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.util.Date;

public class Utilities extends Binder {
    public Context context;

    public static void appBarColor(ActionBar actionBar, Context context){
        //action bar color
        ColorDrawable colorDrawable
                = new ColorDrawable(ContextCompat.getColor(context, R.color.splashColor ));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends
    }
}
