package com.levent_j.potato.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.levent_j.potato.R;
import com.levent_j.potato.adapter.TaskAdapter;
import com.levent_j.potato.base.BaseActivity;
import com.levent_j.potato.bean.Task;
import com.levent_j.potato.utils.SGDecoration;
import com.levent_j.potato.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rv_task_list) RecyclerView taskRecyclerView;
    @Bind(R.id.mr_main_refresh_main) MaterialRefreshLayout refreshLayout;

    private TaskAdapter adapter;

    private final static int RESULT_CODE = 1;

    private static int TYPE_MODE = 0;

    @Override
    protected int setRootLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        taskRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        taskRecyclerView.addItemDecoration(new SGDecoration(getResources().getDimensionPixelSize(R.dimen.hero)));
        taskRecyclerView.setHasFixedSize(true);

        setSupportActionBar(toolbar);

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                queryTasks(TYPE_MODE);
            }
        });
    }

    @Override
    protected void initData() {

        /**初始化adapter*/
        adapter = new TaskAdapter(this);
        taskRecyclerView.setAdapter(adapter);

        /**默认加载全部任务*/
        queryTasks(TYPE_MODE);

//        ToastUtils.msg("测试版本，时间单位暂时为秒");
    }

    private void queryTasks(int state) {
        List<Task> list;
        switch (state){
            case 1:
                //加载未完成
                list = Task.find(Task.class,"state = ?","0");
                break;
            case 2:
                //加载已完成
                list = Task.find(Task.class,"state = ?","1");
                break;
            default:
                //默认加载全部
                list = Task.listAll(Task.class);
                break;
        }
        adapter.replaceData(list);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_all) {
            TYPE_MODE=0;
        }else if (id == R.id.action_finished){
            TYPE_MODE=2;
        }else {
            TYPE_MODE=1;
        }
        queryTasks(TYPE_MODE);
        return super.onOptionsItemSelected(item);
    }



    @OnClick(R.id.fab)
    public void create(View view){
        Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
        startActivityForResult(intent,RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main","加载了一遍数据");
        if (data!=null){
            Task task = data.getParcelableExtra("Task");
            adapter.appendData(task);
            adapter.cleardatas();
            queryTasks(0);
        }

    }
}
