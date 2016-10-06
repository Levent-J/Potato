package com.levent_j.potato.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.levent_j.potato.R;
import com.levent_j.potato.activity.EditTaskActivity;
import com.levent_j.potato.activity.TaskDetailActivity;
import com.levent_j.potato.base.BaseAdapter;
import com.levent_j.potato.bean.Task;
import com.levent_j.potato.widget.CustomDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-5-19.
 */
public class TaskAdapter extends BaseAdapter<Task,TaskAdapter.mViewHolder>{

    private String[] colors = new String[]{
        "#8c591c","#8F5E22","#c18a46"
};

    public TaskAdapter(Activity context) {
        super(context);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task,null,false);
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

        private static final int RESULT_CODE = 1;

        @Bind(R.id.tv_task_item_title)
        TextView title;
        @Bind(R.id.tv_task_item_content)
        TextView content;
        @Bind(R.id.card_view)
        CardView cardView;
        @Bind(R.id.card_layout)
        LinearLayout layout;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindViews(final Task task, final int position){
            title.setText(task.getTitle());
            content.setText(task.getMessage());

            /**生成不同颜色的背景*/
            Log.e("Color", "is" + task.getColor());
            layout.setBackgroundColor(Color.parseColor(colors[task.getColor()]));

            /**自适应屏幕布局*/
            ViewGroup.LayoutParams cardViewLayouParams = cardView.getLayoutParams();
            cardViewLayouParams.width = getItemWidth();
            cardViewLayouParams.height = getItemWidth();
            /**点击进入任务详情界面*/
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra("Task", task);
                    intent.putExtra("id", task.getId());
                    context.startActivity(intent);
                }
            });
            /**长按弹出任务操作提示框*/

            cardView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示")
                            .setMessage("你要对这颗土豆做什么?")
                            .setPositiveButton("扔掉", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //删除该数据
                                    task.delete();
                                    datas.remove(position);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, EditTaskActivity.class);
                                    intent.putExtra("Task", task);
                                    intent.putExtra("Edit", true);
                                    intent.putExtra("id", task.getId());
                                    Log.e("Edit", "id=" + task.getId());
                                    context.startActivityForResult(intent,RESULT_CODE);
//                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    builder.show();
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
