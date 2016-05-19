package com.levent_j.potato.activity;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.base.BaseActivity;

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

    private Timer timer;
    private TimerTask AfterStudyTask;
    private TimerTask AfterReviewTask;
    private TimerTask AfterRestTask;
    private android.os.Handler handler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void init() {
        timer = new Timer();
        handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {

                int type = msg.what;
                switch (type){
                    case 1:
                        T("学习完毕");
                        timer.schedule(AfterReviewTask, 3000);
                        break;
                    case 2:
                        T("复习完毕");
                        timer.schedule(AfterRestTask,3000);
                        break;
                    case 3:
                        T("休息完毕");
                        break;
                }

                super.handleMessage(msg);
            }
        };

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
        //getLayout获取数据并填充
    }

    @Override
    protected void setListener() {
        mStartTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_task_start:
                timer.schedule(AfterStudyTask,3000);
                break;
        }
    }
}
