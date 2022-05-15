package com.example.iem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.TaskList;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskManageFragment extends Fragment {

    private Integer userId;
    private String name;
    private Integer taskListId;
    private EditText content;

    public TaskManageFragment(Integer userId, String name) {
        super(R.layout.fragment_task_manage);
        this.userId = userId;
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_manage, container, false);
        Button cancelBtn = view.findViewById(R.id.cancel);
        content = view.findViewById(R.id.content);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity())
                        .changeFragment(new TasksLvl2Fragment());
                ((MainActivity)requireActivity())
                        .nv.setVisibility(View.VISIBLE);
            }
        });
        Button addBtn = view.findViewById(R.id.adder);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTaskList(view);
            }
        });
        return view;
    }

    private void getTaskList(View view) {
        (new NetworkService())
                .getApi()
                .getAllTaskList(userId)
                .enqueue(new Callback<List<TaskList>>() {
                    @Override
                    public void onResponse(Call<List<TaskList>> call, Response<List<TaskList>> response) {
                        if (response.body() == null || response.body().size() < 1) {
                            return;
                        }
                        taskListId = response.body().get(0).getId();
                        addTask(view);
                    }
                    @Override
                    public void onFailure(Call<List<TaskList>> call, Throwable t) {
                        //
                    }
                });
    }

    private void addTask(View view) {
        if (taskListId == null) {
            return;
        }
        (new NetworkService())
                .getApi()
                .addTask(content.getText().toString(), false, taskListId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ((Button)view.findViewById(R.id.adder))
                                .setText("УСПЕХ");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //
                    }
                });
    }

}
