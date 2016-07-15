package com.levent_j.potato.utils;

import android.content.Context;
import android.widget.Toast;

import com.levent_j.potato.base.BaseActivity;

/**
 * Created by levent_j on 16-7-15.
 */
public class ToastUtils {
    private Context context;
    public static void msg(String s){
        Toast.makeText(BaseActivity.getContext(),s,Toast.LENGTH_SHORT).show();
    }
}
