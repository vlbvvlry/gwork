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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpAddingFragment extends Fragment {

    public EmpAddingFragment() {
        super(R.layout.fragment_emp_adding);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emp_adding, container, false);
        (view.findViewById(R.id.adder))
                .setOnClickListener(view1 -> {
                    if (
                            ((EditText)view.findViewById(R.id.name)).getText().toString().length() > 1 &&
                            ((EditText)view.findViewById(R.id.login)).getText().toString().length() > 1 &&
                            ((EditText)view.findViewById(R.id.pass)).getText().toString().length() > 1
                    ) {
                        (view.findViewById(R.id.adder)).setEnabled(false);
                        register(view);
                    } else {
                        ((Button)view.findViewById(R.id.adder)).setText("Данные введены неверно");
                    }
                });
        (view.findViewById(R.id.cancel))
                .setOnClickListener(view1 -> {
                    ((MainActivity)requireActivity()).nv.setVisibility(View.VISIBLE);
                    ((MainActivity)requireActivity()).changeFragment(new HomeFragment());
                });
        return view;
    }

    private void register(View view) {
        (new NetworkService())
                .getApi()
                .registration(
                        ((EditText)view.findViewById(R.id.name)).getText().toString(),
                        ((EditText)view.findViewById(R.id.login)).getText().toString(),
                        ((EditText)view.findViewById(R.id.pass)).getText().toString(),
                        1
                )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //
                    }
                });
    }
}
