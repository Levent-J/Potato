package com.levent_j.potato.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.bean.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-5-19.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.mViewHolder>{
    private Context context;
    private List<Task> taskList;
    private LayoutInflater layoutInflater;

    public TaskAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        taskList = new ArrayList<>();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_task,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.mTitle.setText(task.getTitle());
        holder.mMessage.setText(task.getMessage());
        ViewGroup.LayoutParams cardViewLayouParams = holder.cardView.getLayoutParams();
        cardViewLayouParams.width = getItemWidth();
        cardViewLayouParams.height = getItemWidth();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateTaskList(Task task){
        taskList.add(task);
        notifyDataSetChanged();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_task_item_title)
        TextView mTitle;
        @Bind(R.id.tv_task_item_message)
        TextView mMessage;
        @Bind(R.id.card_view)
        CardView cardView;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    
    private int getItemWidth(){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth()/2-80;
    }
}
