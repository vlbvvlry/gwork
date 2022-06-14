package com.example.iem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.iem.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private final String USER_ID_KEY = "USER_ID";
    private final String USER_LOGIN_KEY = "USER_LOGIN";
    private final String USER_LEVEL_KEY = "USER_LEVEL";
    public BottomNavigationView nv = null;

    public void onAuth(String login, String userCode) {
        String[] responseCode = userCode.split(" ");
        MyUser.login = login;
        MyUser.id = Integer.parseInt(responseCode[1]);
        MyUser.level = Integer.parseInt(responseCode[2]);
        saveData();
        onSuccessAuthentication();
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .commit();
    }

    public void exit() {
        SharedPreferences authSet = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = authSet.edit();
        editor.clear();
        editor.apply();
        onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        nv = findViewById(R.id.bottom_navigation);
        nv.setOnNavigationItemSelectedListener(item -> displayFragment(item.getItemId()));
        nv.getMenu().findItem(R.id.tasks_page).setChecked(true);
        if (MyUser.id == null || MyUser.id == -1) {
            changeFragment(new LoginFragment());
        } else {
            taskFragmentChanging(MyUser.level);
            nv.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        SharedPreferences authSet = getSharedPreferences("userData", MODE_PRIVATE);
        MyUser.id = authSet.getInt(USER_ID_KEY, -1);
        MyUser.level = authSet.getInt(USER_LEVEL_KEY, -1);
        MyUser.login = authSet.getString(USER_LOGIN_KEY, "");
    }

    private void saveData() {
        SharedPreferences authSet = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = authSet.edit();
        editor.putInt(USER_ID_KEY, MyUser.id);
        editor.putInt(USER_LEVEL_KEY, MyUser.level);
        editor.putString(USER_LOGIN_KEY, MyUser.login);
        editor.apply();
    }

    @SuppressLint("NonConstantResourceId")
    private boolean displayFragment(int id) {
        switch (id) {
            case R.id.events_page:
                changeFragment(new EventsFragment());
                return true;
            case R.id.tasks_page:
                taskFragmentChanging(MyUser.level);
                return true;
            case R.id.home_page:
                changeFragment(new HomeFragment());
                return true;
        }
        return false;
    }

    private void taskFragmentChanging(int level) {
        switch (MyUser.level) {
            case 0:
            case 2:
                changeFragment(new Lvl2TasksFragment());
                break;
            case 1:
                changeFragment(new Lvl1TasksFragment());
                break;
            case 3:
                changeFragment(new HomeFragment());
                nv.getMenu().findItem(R.id.tasks_page).setVisible(false);
                break;
            default: break;
        }
    }

    private void onSuccessAuthentication() {
        taskFragmentChanging(MyUser.level);
        nv.setVisibility(View.VISIBLE);
    }

    public static class MyUser {
        public static Integer id = null;
        public static String login = null;
        public static Integer level = null;
        public static Integer taskListId = null;
    }

}