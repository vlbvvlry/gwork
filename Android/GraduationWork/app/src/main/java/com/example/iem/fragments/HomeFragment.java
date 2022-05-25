package com.example.iem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        loadMyUser(view);
        return view;
    }

    private void loadMyUser(View view) {
        (new NetworkService())
                .getApi()
                .getMyUser(MainActivity.MyUser.id)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() == null) {
                            return;
                        }
                        myUserInit(view, response.body());
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        //
                    }
                });
    }

    private void myUserInit(View view, User user) {
        ((TextView)view.findViewById(R.id.name))
                .setText(user.getFullName());
        String level;
         switch (user.getLevel()) {
             case 1:
                 level = "Н. Сотрудник";
                 break;
             case 2:
                 level = "Куратор";
                 break;
             case 3:
                 level = "Управление контентом";
                 ((Button)view.findViewById(R.id.addEventBtn))
                         .setVisibility(View.VISIBLE);
                 ((Button)view.findViewById(R.id.addEventBtn))
                         .setEnabled(true);
                 ((Button)view.findViewById(R.id.addEventBtn))
                         .setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 ((MainActivity)requireActivity())
                                         .changeFragment(new EventAddingFragment());
                                 ((MainActivity)requireActivity())
                                         .nv.setVisibility(View.GONE);
                             }
                         });
                 break;
             default:
                 level = "?";
                 break;
        }
        ((TextView)view.findViewById(R.id.level))
                .setText(level);
    }
}
