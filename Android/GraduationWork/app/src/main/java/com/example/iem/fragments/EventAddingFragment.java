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

public class EventAddingFragment extends Fragment {

    public EventAddingFragment () {
        super(R.layout.fragment_event_adding);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_adding, container, false);
        ((Button)view.findViewById(R.id.cancel))
                .setOnClickListener(view1 -> {
                    ((MainActivity)requireActivity())
                            .changeFragment(new HomeFragment());
                    ((MainActivity)requireActivity())
                            .nv.setVisibility(View.VISIBLE);
                });
        ((Button)view.findViewById(R.id.adder))
                .setOnClickListener(view1 -> {
                    ((Button)view1.findViewById(R.id.adder))
                            .setEnabled(false);
                    ((Button)view1.findViewById(R.id.adder))
                            .setText("Загрузка..");
                    addEvent(view);
                });
        return view;
    }

    private void addEvent(View view) {
        (new NetworkService())
                .getApi()
                .addEvent(((EditText)view.findViewById(R.id.center)).getText().toString(),
                        ((EditText)view.findViewById(R.id.content)).getText().toString(),
                        MainActivity.MyUser.id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ((Button)view.findViewById(R.id.adder))
                                .setText("Объявлено!");
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ((Button)view.findViewById(R.id.adder))
                                .setText("Ошибка");
                    }
                });
    }

}
