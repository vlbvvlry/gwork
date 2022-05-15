package com.example.iem.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.WardListAdapter;
import com.example.iem.WardState;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksLvl2Fragment extends Fragment {

    private final List<WardState> wardStateList = new ArrayList<>();

    public TasksLvl2Fragment () {
        super(R.layout.fragment_tasks_lvl2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_lvl2, container, false);
        loadWards(view);
        return view;
    }

    private void loadWards(View view) {
        (new NetworkService())
                .getApi()
                .getUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        List<User> body = response.body();
                        if (body == null || body.size() < 1) {
                            System.out.println("TFLvl2 UserList is empty.");
                            return;
                        }
                        for (int i = 0; i < body.size(); i++) {
                            WardState ws = new WardState();
                            ws.setId(body.get(i).getId());
                            ws.setName(body.get(i).getFullName());
                            wardStateList.add(ws);
                        }
                        System.out.println("Response  " + wardStateList.get(0).getName());
                        wardListInit(view);
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        //
                    }
                });
    }

    private void wardListInit(View view) {
//        WardListAdapter adapter = new WardListAdapter(requireContext(), R.layout.ward_item, wardStateList);
//        adapter.notifyDataSetChanged();
//        ListView wardListView = view.findViewById(R.id.wardListView);
//        wardListView.setAdapter(adapter);
//        System.out.println("wardListInit " + wardStateList.get(0).getName());
//        System.out.println("wardListInit " + wardStateList.size());

        TextView tv1 = view.findViewById(R.id.profileName);
        TextView tv2 = view.findViewById(R.id.profileName2);

        tv1.setText(wardStateList.get(0).getName());
        tv2.setText(wardStateList.get(3).getName());

        view.findViewById(R.id.addButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)requireActivity())
                                .changeFragment(new TaskManageFragment(wardStateList.get(0).getId(),
                                        wardStateList.get(0).getName()));
                        ((MainActivity)requireActivity())
                                .nv.setVisibility(View.GONE);
                    }
                });
        view.findViewById(R.id.addButton2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)requireActivity())
                                .changeFragment(new TaskManageFragment(wardStateList.get(3).getId(),
                                        wardStateList.get(3).getName()));
                        ((MainActivity)requireActivity())
                                .nv.setVisibility(View.GONE);
                    }
                });
        //
    }

}
