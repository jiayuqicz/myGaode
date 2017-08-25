package com.jiayuqicz.mygaode.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wzq on 2017/8/25.
 */

public class ToastUtil {

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    }


}
