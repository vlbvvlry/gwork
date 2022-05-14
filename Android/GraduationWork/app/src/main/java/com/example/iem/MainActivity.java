package com.example.iem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.iem.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView nv = null;

    public void onAuth(String login, String userCode) {
        String[] responseCode = userCode.split(" ");
        MyUser.login = login;
        MyUser.id = Integer.parseInt(responseCode[1]);
        MyUser.level = Integer.parseInt(responseCode[2]);
        onSuccessAuthentication();
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .commit();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new LoginFragment());
        nv = findViewById(R.id.bottom_navigation);
        nv.setOnNavigationItemSelectedListener(item -> displayFragment(item.getItemId()));
        nv.getMenu().findItem(R.id.tasks_page).setChecked(true);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean displayFragment(int id) {
        switch (id) {
            case R.id.events_page:
                changeFragment(new EventsFragment());
                return true;
            case R.id.tasks_page:
                changeFragment(new TasksFragment());
                return true;
            case R.id.home_page:
                changeFragment(new HomeFragment());
                return true;
        }
        return false;
    }

    private void onSuccessAuthentication() {
        changeFragment(new TasksFragment());
        nv.setVisibility(View.VISIBLE);
    }

    public static class MyUser {
        public static Integer id = null;
        public static String login = null;
        public static Integer level = null;
    }

}