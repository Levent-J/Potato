package com.levent_j.potato.activity;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.base.BaseActivity;
import com.levent_j.potato.utils.Util;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * Created by levent_j on 16-5-19.
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_task_title) TextView mTitle;
    @Bind(R.id.tv_task_message) TextView mMessage;
    @Bind(R.id.tv_task_duration_study) TextView mDurationStudy;
    @Bind(R.id.tv_task_duration_review) TextView mDurationReview;
    @Bind(R.id.tv_task_duration_rest) TextView mDurationRest;
    @Bind(R.id.btn_task_start) Button mStartTask;
    @Bind(R.id.tv_toolbar_title) TextView mToolBar;
    @Bind(R.id.rl_show) RelativeLayout mShowLayout;

    private Timer timer;
    private TimerTask AfterStudyTask;
    private TimerTask AfterReviewTask;
    private TimerTask AfterRestTask;
    private android.os.Handler handler;

    private int StudyDuration;
    private int ReviewDuration;
    private int RestDuration;

    private boolean canBack = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void init() {
        mToolBar.setText("任务详情");
        timer = new Timer();
        handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {

                int type = msg.what;
                switch (type){
                    case 1:
                        T("学习完毕");
                        timer.schedule(AfterReviewTask, Util.Minute2Second(ReviewDuration));
                        break;
                    case 2:
                        T("复习完毕");
                        timer.schedule(AfterRestTask,Util.Minute2Second(RestDuration));
                        break;
                    case 3:
                        T("休息完毕");
                        mShowLayout.setVisibility(View.GONE);
                        canBack = true;
                        break;
                }

                super.handleMessage(msg);
            }
        };


        //getLayout获取数据并填充
        initData();
    }

    private void initData() {
        if (getIntent().getBooleanExtra("tag",false)){
            mTitle.setText(getIntent().getStringExtra("title"));
            mMessage.setText(getIntent().getStringExtra("message"));
            mDurationStudy.setText(getIntent().getIntExtra("study", 0)+"分钟");
            mDurationReview.setText(getIntent().getIntExtra("review",0)+"分钟");
            mDurationRest.setText(getIntent().getIntExtra("rest",0)+"分钟");

            StudyDuration = getIntent().getIntExtra("study", 0);
            ReviewDuration = getIntent().getIntExtra("review",0);
            RestDuration = getIntent().getIntExtra("rest",0);
        }
    }

    private void initTask() {
        AfterStudyTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        };

        AfterReviewTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what=2;
                handler.sendMessage(message);
            }
        };

        AfterRestTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what=3;
                handler.sendMessage(message);
            }
        };
    }

    @Override
    protected void setListener() {
        mStartTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_task_start:
                initTask();
                mShowLayout.setVisibility(View.VISIBLE);
                canBack=false;
                timer.schedule(AfterStudyTask, Util.Minute2Second(StudyDuration));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (canBack){
            super.onBackPressed();
        }
    }
}
