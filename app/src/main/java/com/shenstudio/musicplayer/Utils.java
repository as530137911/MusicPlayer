package com.shenstudio.musicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

/**
 * Created by 沈文斐 on 2016-09-21.
 * 包含项目中常用的工具方法
 */

public class Utils {
    private static Toast toast;

    //优化Toast显示，避免Toast长时间占据屏幕
    static void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    static int getRamdomFrom(int min, int max) {
        int temp = (int) (Math.random() * (max - min) + min);
        return temp;
    }
}
