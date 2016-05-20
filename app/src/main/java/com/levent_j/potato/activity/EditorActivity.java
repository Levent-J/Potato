package com.levent_j.potato.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.Bind;
import info.hoang8f.widget.FButton;

/**
 * Created by levent_j on 16-5-20.
 */
public class EditorActivity extends BaseActivity{
    @Bind(R.id.et_title)
    EditText mTitle;
    @Bind(R.id.et_content)
    EditText mContent;
    @Bind(R.id.et_study)
    EditText mDurationStudy;
    @Bind(R.id.et_review)
    EditText mDurationReview;
    @Bind(R.id.et_rest)
    EditText mDurationRest;
    @Bind(R.id.tv_toolbar_title)
    TextView mToolBar;
    @Bind(R.id.btn_confirm)
    FButton mConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor;
    }

    @Override
    protected void init() {
        mToolBar.setText("新建任务");
    }

    @Override
    protected void setListener() {
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNull()) {
                    Snackbar.make(v, "请填写完整", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                    intent.putExtra("tag", true);
                    intent.putExtra("title", mTitle.getText().toString().trim());
                    intent.putExtra("message", mContent.getText().toString().trim());
                    intent.putExtra("study", mDurationStudy.getText().toString().trim());
                    intent.putExtra("review", mDurationReview.getText().toString().trim());
                    intent.putExtra("rest", mDurationRest.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private boolean checkNull() {
        if (    TextUtils.isEmpty(mTitle.getText())
                ||TextUtils.isEmpty(mContent.getText())
                ||TextUtils.isEmpty(mDurationStudy.getText())
                ||TextUtils.isEmpty(mDurationReview.getText())
                ||TextUtils.isEmpty(mDurationRest.getText())){
            return true;
        }else {
            return false;
        }

    }
}
