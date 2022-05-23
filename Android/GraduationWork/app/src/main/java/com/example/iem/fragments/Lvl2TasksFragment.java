package com.example.iem.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.WardStateAdapter;
import com.example.iem.WardState;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lvl2TasksFragment extends Fragment
        implements WardStateAdapter.FragmentChanger {

    private final List<WardState> wardStateList = new ArrayList<>();

    public Lvl2TasksFragment() {
        super(R.layout.fragment_tasks_lvl2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_lvl2, container, false);
        loadWards(view);
        return view;
    }

    @Override
    public void taskAddingDisplay(int position) {
        ((MainActivity)requireActivity())
                .changeFragment
                        (new TaskAddingFragment(wardStateList.get(position).getId(),
                                wardStateList.get(position).getName()));
        ((MainActivity)requireActivity())
                .nv.setVisibility(View.GONE);
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
                            Log.d("Lvl2TasksFragment", "loadWards. response.body is empty");
                            return;
                        }
                        for (int i = 0; i < body.size(); i++) {
                            WardState ws = new WardState();
                            ws.setId(body.get(i).getId());
                            ws.setName(body.get(i).getFullName());
                            wardStateList.add(ws);
                        }
                        wardListInit(view);
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        //
                    }
                });
    }

    private void wardListInit(View view) {
        WardStateAdapter adapter = new WardStateAdapter(requireContext(), R.layout.item_ward, wardStateList);
        adapter.setFragmentChanger(this);
        ListView wardListView = view.findViewById(R.id.wardListView);
        wardListView.setAdapter(adapter);
//        view.findViewById(R.id.addButton)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ((MainActivity)requireActivity())
//                                .changeFragment(new TaskAddingFragment(wardStateList.get(0).getId(),
//                                        wardStateList.get(0).getName()));
//                        ((MainActivity)requireActivity())
//                                .nv.setVisibility(View.GONE);
//                    }
//                });
//        view.findViewById(R.id.addButton2)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ((MainActivity)requireActivity())
//                                .changeFragment(new TaskAddingFragment(wardStateList.get(3).getId(),
//                                        wardStateList.get(3).getName()));
//                        ((MainActivity)requireActivity())
//                                .nv.setVisibility(View.GONE);
//                    }
//                });
        //
    }

}
