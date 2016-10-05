package com.levent_j.potato.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-5-19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setRootLayout());
        ButterKnife.bind(this);
        initView();
        initData();
        TAG = this.getClass().getSimpleName();
    }

    protected abstract int setRootLayout();

    protected abstract void initView();

    protected abstract void initData();

    public static Context getContext(){
        return getContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}