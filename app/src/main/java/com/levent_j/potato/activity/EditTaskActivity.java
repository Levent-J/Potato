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
import com.levent_j.potato.bean.Task;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

/**
 * Created by levent_j on 16-5-20.
 */
public class EditTaskActivity extends BaseActivity{
    @Bind(R.id.et_title)
    EditText title;
    @Bind(R.id.et_content)
    EditText content;
    @Bind(R.id.et_study)
    EditText durationStudy;
    @Bind(R.id.et_review)
    EditText durationReview;
    @Bind(R.id.et_rest)
    EditText durationRest;

    private final static int RESULT_CODE = 1;

    @Override
    protected int setRootLayout() {
        return R.layout.activity_editor;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_confirm)
    public void confirm(View view){
        if (checkNull()) {
            Snackbar.make(view, "请填写完整", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Task",getTask());
            setResult(RESULT_CODE,intent);
            finish();
        }
    }

    private Task getTask() {
        Task task = new Task();
        task.setTitle(title.getText().toString().trim());
        task.setMessage(content.getText().toString().trim());
        task.setStudy(Double.valueOf(durationStudy.getText().toString().trim()));
        task.setReview(Double.valueOf(durationReview.getText().toString().trim()));
        task.setRest(Double.valueOf(durationRest.getText().toString().trim()));
        return task;
    }


    private boolean checkNull() {
        if (    TextUtils.isEmpty(title.getText().toString().trim())
                ||TextUtils.isEmpty(content.getText().toString().trim())
                ||TextUtils.isEmpty(durationStudy.getText().toString().trim())
                ||TextUtils.isEmpty(durationReview.getText().toString().trim())
                ||TextUtils.isEmpty(durationRest.getText().toString().trim())){
            return true;
        }else {
            return false;
        }

    }
}
