package com.levent_j.potato.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.activity.TaskDetailActivity;
import com.levent_j.potato.base.BaseAdapter;
import com.levent_j.potato.bean.Task;
import com.levent_j.potato.widget.CustomDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-5-19.
 */
public class TaskAdapter extends BaseAdapter<Task,TaskAdapter.mViewHolder>{
    private LayoutInflater layoutInflater;

    public TaskAdapter(Context context) {
        super(context);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, final int position) {
        final Task task = datas.get(position);
        holder.bindViews(task,position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void updatedatas(Task task){
        datas.add(task);
        notifyDataSetChanged();
    }
    public void cleardatas(){
        datas.clear();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_task_item_title)
        TextView title;
        @Bind(R.id.tv_task_item_content)
        TextView content;
        @Bind(R.id.card_view)
        CardView layout;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindViews(final Task task, final int position){
            title.setText(task.getTitle());
            content.setText(task.getMessage());
            ViewGroup.LayoutParams cardViewLayouParams = layout.getLayoutParams();
            cardViewLayouParams.width = getItemWidth();
            cardViewLayouParams.height = getItemWidth();
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra("Task",task);
                    context.startActivity(intent);
                }
            });
            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CustomDialog.Builder mDialog = new CustomDialog.Builder(context);
                    mDialog.setTitle("提示")
                            .setMessage("要删除该任务吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    datas.remove(position);
                                    notifyDataSetChanged();


                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }
    }
    
    private int getItemWidth(){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth()/2-80;
    }
}
