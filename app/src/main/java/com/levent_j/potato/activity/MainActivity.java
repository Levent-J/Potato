package com.levent_j.potato.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.rv_task_list) RecyclerView taskRecyclerView;
    @Bind(R.id.mr_main_refresh_main) MaterialRefreshLayout refreshLayout;

    private TaskAdapter adapter;

    private final static int RESULT_CODE = 1;

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                queryTasks(0);
            }
        });
    }

    @Override
    protected void initData() {

        /**初始化adapter*/
        adapter = new TaskAdapter(this);
        taskRecyclerView.setAdapter(adapter);

        /**默认加载全部任务*/
        queryTasks(0);
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
    protected void setListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            queryTasks(0);
        }else if (id == R.id.action_finished){
            queryTasks(2);
        }else {
            queryTasks(1);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @OnClick(R.id.fab)
    public void create(View view){
        Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
        startActivityForResult(intent,RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            Task task = data.getParcelableExtra("Task");
            adapter.appendData(task);
            adapter.cleardatas();
            queryTasks(0);
        }

    }
}
