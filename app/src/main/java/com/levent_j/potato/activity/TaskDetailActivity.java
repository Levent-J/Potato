package com.levent_j.potato.activity;

import android.content.DialogInterface;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.base.BaseActivity;
import com.levent_j.potato.bean.Task;
import com.levent_j.potato.utils.Util;
import com.levent_j.potato.widget.CustomDialog;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by levent_j on 16-7-12.
 */
public class TaskDetailActivity extends BaseActivity{
    @Bind(R.id.tv_task_title)
    TextView title;
    @Bind(R.id.tv_task_message) TextView content;
    @Bind(R.id.tv_task_duration_study) TextView durationStudy;
    @Bind(R.id.tv_task_duration_review) TextView durationReview;
    @Bind(R.id.tv_task_duration_rest) TextView durationRest;
    @Bind(R.id.rl_show)
    RelativeLayout imgLayout;
    @Bind(R.id.ll_layout)
    LinearLayout taskLayout;

    private Timer timer;
    private TimerTask AfterStudyTask;
    private TimerTask AfterReviewTask;
    private TimerTask AfterRestTask;
    private android.os.Handler handler;

    private int StudyDuration;
    private int ReviewDuration;
    private int RestDuration;

    private boolean canBack = true;

    //振动器
    private Vibrator vibrator;

    //振动时间
    private long[] vibratorTimes = new long[]{1000,1000};

    //
    private CustomDialog.Builder builder;

    @Override
    protected int setRootLayout() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void initView() {
        Task task = getIntent().getParcelableExtra("Task");
        title.setText(task.getTitle());
        content.setText(task.getMessage());
        durationStudy.setText(String.valueOf(task.getStudy()));
        durationReview.setText(String.valueOf(task.getReview()));
        durationRest.setText(String.valueOf(task.getRest()));

        StudyDuration = task.getStudy();
        ReviewDuration = task.getReview();
        RestDuration = task.getRest();
    }


    @Override
    protected void initData() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        timer = new Timer();
        handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        startVibrator("学习完毕");
                        break;
                    case 2:
                        startVibrator("复习完毕");
                        break;
                    case 3:
                        startVibrator("休息完毕");
                        break;
                }

            }
        };
    }

    private void startVibrator(final String s) {

        //开始振动
        vibrator.vibrate(vibratorTimes, 0);

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        CustomDialog customDialog = builder.setTitle("")
                .setMessage("关闭振动吧！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        vibrator.cancel();
                        if (s.equals("学习完毕")) {
                            timer.schedule(AfterReviewTask, Util.Minute2Second(ReviewDuration));
                        } else if (s.equals("复习完毕")) {
                            timer.schedule(AfterRestTask, Util.Minute2Second(RestDuration));
                        } else {
                            taskLayout.setVisibility(View.VISIBLE);
                            imgLayout.setVisibility(View.GONE);
                            canBack = true;
                        }
                    }
                })
                .create();

        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
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
    }

    @OnClick(R.id.btn_task_start)
    public void startTask(View view){
        initTask();
        imgLayout.setVisibility(View.VISIBLE);
        taskLayout.setVisibility(View.GONE);
        canBack=false;
        timer.schedule(AfterStudyTask, Util.Minute2Second(StudyDuration));
    }


    @Override
    public void onBackPressed() {
        if (canBack){
            super.onBackPressed();
        }
    }
}
