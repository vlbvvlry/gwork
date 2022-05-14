package com.example.iem.network;

import com.example.iem.network.models.Event;
import com.example.iem.network.models.TaskList;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @GET("/get_all_events")
    Call<List<Event>> getEvents();

    @GET("/get_all_the_task_list/{user_id}")
    Call<List<TaskList>> getAllTaskList(@Path("user_id") Integer userId);

    @POST("/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("login") String login,
                             @Field("password") String password);
}
