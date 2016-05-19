package com.levent_j.potato.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-5-19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
        setListener();
        TAG = this.getClass().getSimpleName();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void setListener();

    protected void msg(String s) {
        Log.d(TAG, s);
    }

    protected void T(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}