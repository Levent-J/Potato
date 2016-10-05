package com.levent_j.potato.activity;

import android.content.DialogInterface;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.base.BaseActivity;
import com.levent_j.potato.bean.Task;
import com.levent_j.potato.utils.Util;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by levent_j on 16-7-12.
 */
public class TaskDetailActivity extends BaseActivity{
    @Bind(R.id.tv_task_title) TextView title;
    @Bind(R.id.tv_task_message) TextView content;
    @Bind(R.id.tv_task_duration_study) TextView durationStudy;
    @Bind(R.id.tv_task_duration_review) TextView durationReview;
    @Bind(R.id.tv_task_duration_rest) TextView durationRest;
    @Bind(R.id.rl_show) RelativeLayout imgLayout;
    @Bind(R.id.ll_layout) LinearLayout taskLayout;
    @Bind(R.id.iv_img) ImageView showImg;

    private Timer timer;
    private TimerTask AfterStudyTask;
    private TimerTask AfterReviewTask;
    private TimerTask AfterRestTask;
    private TimerTask ImgChangeTask;
    private android.os.Handler handler;

    private double StudyDuration;
    private double ReviewDuration;
    private double RestDuration;

    private boolean canBack = true;

    /**振动器*/
    private Vibrator vibrator;

    /**振动时间*/
    private final static long[] VIBRATOR_TIMES = new long[]{1000,1000};
    /**图片变换时间*/
    private final static int CHANGE_IMG_DELAYS = 10000;
    private final static int CHANGE_IMG_PERIOD = 10000;

    private final static String MESSAGE_REVIEW="现在开始复习吧！";
    private final static String MESSAGE_REST="学习了这么久，该休息一下啦，别玩手机哦";
    private final static String MESSAGE_FINISH="任务完成！";

    /**图片资源*/
    private int[] imgs = {
            R.drawable.show_1,
            R.drawable.show_2,
            R.drawable.show_3,
            R.drawable.show_4,
            R.drawable.show_5,
            R.drawable.show_6,
    };

    private int imgIndex = 1;

    private Long id;

    @Override
    protected int setRootLayout() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void initView() {
        /**获取Task及id*/
        Task task = getIntent().getParcelableExtra("Task");
        id = getIntent().getLongExtra("id",0);

        title.setText(task.getTitle());
        content.setText(task.getMessage());
        durationStudy.setText(String.valueOf((int)task.getStudy()));
        durationReview.setText(String.valueOf((int)task.getReview()));
        durationRest.setText(String.valueOf((int)task.getRest()));

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
                        startVibrator(MESSAGE_REVIEW);
                        break;
                    case 2:
                        startVibrator(MESSAGE_REST);
                        break;
                    case 3:
                        startVibrator(MESSAGE_FINISH);
                        break;
                    case 4:
                        if (imgIndex>5){
                            imgIndex=0;
                        }
                        Log.e("IMG","index="+imgIndex);
                        if (showImg!=null){
                            showImg.setImageResource(imgs[imgIndex]);
                        }
                        imgIndex++;
                        break;
                }

            }
        };
    }

    private void startVibrator(final String s) {

        /**开始振动*/
        vibrator.vibrate(VIBRATOR_TIMES, 0);

        /**创建dialog并监听返回键*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意：")
                .setMessage(s)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        vibrator.cancel();
                        if (s.equals(MESSAGE_REVIEW)) {
                            timer.schedule(AfterReviewTask, Util.Minute2Second(ReviewDuration));
                        } else if (s.equals(MESSAGE_REST)) {
                            timer.schedule(AfterRestTask, Util.Minute2Second(RestDuration));
                        } else {
                            taskLayout.setVisibility(View.VISIBLE);
                            imgLayout.setVisibility(View.GONE);
                            canBack = true;
                        }
                    }
                })
                .setCancelable(false)
                .create()
                .setCanceledOnTouchOutside(false);
        builder.show();
    }


    /**初始化所有task*/
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

        ImgChangeTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what =4;
                handler.sendMessage(message);
            }
        };
    }


    /**开始所有计时任务*/
    @OnClick(R.id.btn_task_start)
    public void startTask(View view){
        initTask();
        imgLayout.setVisibility(View.VISIBLE);
        taskLayout.setVisibility(View.GONE);
        canBack=false;
        timer.schedule(AfterStudyTask,  Util.Minute2Second(StudyDuration));
        timer.schedule(ImgChangeTask, CHANGE_IMG_DELAYS,CHANGE_IMG_PERIOD);
        changeTaskState();
    }

    /**改变任务状态为1（已完成）*/
    private void changeTaskState() {
        Task task = Task.findById(Task.class,id);
        task.setState(1);
        task.save();
    }


    @Override
    public void onBackPressed() {
        if (canBack){
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
