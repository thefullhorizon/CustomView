package com.nanshanstudio.customview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by NANSHAN on 3/5/17.
 */

public class Utils {

    public static int dpToPx(Context context, float dpValue) {
        return dpToPx(context.getResources(),dpValue);
    }

    //代码中设置的宽高单位是px,所以我们需要在代码中先将数字转成px,后系统会在运行时转化为对应的dp
    public static int dpToPx(Resources resources,float dpValue) {
        float scale=resources.getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static int pxToDp(Context context,float pxValue) {
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    public static void toast(Context context ,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }

}
