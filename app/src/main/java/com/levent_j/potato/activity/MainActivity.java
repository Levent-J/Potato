package com.levent_j.potato.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.levent_j.potato.R;
import com.levent_j.potato.adapter.TaskAdapter;
import com.levent_j.potato.base.BaseActivity;
import com.levent_j.potato.bean.Task;
import com.levent_j.potato.utils.SGDecoration;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.rv_task_list) RecyclerView taskRecyclerView;

    private TaskAdapter taskAdapter;
    private int spacingInPixels;
    private static Realm realm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        realm = Realm.getInstance(
                new RealmConfiguration.Builder(this)
                .name("Test.realm")
                .build()
        );
        taskAdapter = new TaskAdapter(this);
        taskRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        spacingInPixels = getResources().getDimensionPixelSize(R.dimen.hero);
        taskRecyclerView.addItemDecoration(new SGDecoration(spacingInPixels));
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setAdapter(taskAdapter);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initData();
    }

    private void initData() {
        if (getIntent().getBooleanExtra("tag",false)){

            //写入realm
            realm.beginTransaction();

            Task task = realm.createObject(Task.class);

            task.setTitle(getIntent().getStringExtra("title"));
            task.setMessage(getIntent().getStringExtra("message"));
            task.setStudy(Integer.parseInt(getIntent().getStringExtra("study")));
            task.setReview(Integer.parseInt(getIntent().getStringExtra("review")));
            task.setRest(Integer.parseInt(getIntent().getStringExtra("rest")));

            realm.commitTransaction();

            taskAdapter.updateTaskList(task);
        }else {
            RealmResults<Task> realmResults = realm.where(Task.class).findAll();
            for (Task task:realmResults){
                taskAdapter.updateTaskList(task);
            }
        }
    }

    @Override
    protected void setListener() {
        fab.setOnClickListener(this);
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
        if (id == R.id.action_settings) {
            return true;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(MainActivity.this,EditorActivity.class));
                break;
        }
    }

    public static void deleteFromRealm(Task task,int pos){
        RealmResults<Task> realmResults = realm.where(Task.class).findAll();

        realm.beginTransaction();
        realmResults.deleteFromRealm(pos);
        realm.commitTransaction();
    }


}
