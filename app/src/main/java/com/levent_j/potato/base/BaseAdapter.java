package com.levent_j.potato.base;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by levent_j on 16-7-12.
 */
public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    public List<T> datas;
    public Activity context;

    public BaseAdapter(Activity context){
        this.context = context;
        datas = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void replaceData(List<T> list){
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void appendData(List<T> list){
        int start = datas.size();
        int count = list.size();
        datas.addAll(list);
        notifyItemRangeInserted(start, count);
    }

    public void appendData(T t){
        datas.add(t);
        notifyDataSetChanged();
    }

    public void clearData(){
        datas.clear();
        notifyDataSetChanged();
    }
}
