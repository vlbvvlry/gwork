package com.example.iem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.network.NetworkService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        super(R.layout.fragment_auth);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        TextView alertText = view.findViewById(R.id.err_alert);
        EditText loginField = view.findViewById(R.id.login_input);
        EditText passwordField = view.findViewById(R.id.pass_input);
        Button btn = view.findViewById(R.id.sign_in_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                authTry(loginField, passwordField, alertText);
            }
        });
        return view;
    }

    private void authTry(EditText login, EditText password, TextView alertText) {
        (new NetworkService())
                .getApi()
                .login(login.getText().toString(),
                        password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call,
                                           @Nullable Response<ResponseBody> response) {
                        try {
                            if (response == null || response.body() == null) {
                                return;
                            }
                            String r = response.body().string();
                            switch (r) {
                                case "":
                                    break;
                                case "100":
                                    alertText.setText("Что-то не так");
                                    break;
                                case "101":
                                    alertText.setText("Пользователь не найден");
                                    break;
                                case "102":
                                    alertText.setText("Пароль неверный");
                                    break;
                                default:
                                    alertText.setTextColor(getResources().getColor(R.color.green));
                                    alertText.setText("Вы вошли");
                                    ((MainActivity)requireActivity())
                                            .onAuth(login.getText().toString(), r);
                            }
                            System.out.println(response.raw());
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call,
                                          @NonNull Throwable t) {
                        alertText.setText("Проблемы с подключением.");
                    }
                });
    }
}
