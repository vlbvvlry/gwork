package com.example.iem.network;

import com.example.iem.network.models.Event;
import com.example.iem.network.models.Task;
import com.example.iem.network.models.TaskList;
import com.example.iem.network.models.User;

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

    @GET("/get_all_user")
    Call<List<User>> getUsers();

    @GET("/get_all_the_task_list/{user_id}")
    Call<List<TaskList>> getAllTaskList(@Path("user_id") Integer userId);

    @GET("/get_task_list/{task_list_id}")
    Call<List<Task>> getAllTaskByTaskList(@Path("task_list_id") Integer taskListId);

    @GET("/users/userId={userId}")
    Call<User> getMyUser (@Path("userId") Integer userId);

    @GET("/tasks/items/clear/where/holderId={holderId}")
    Call<ResponseBody> clearTaskList(@Path("holderId") Integer taskListId);

    @POST("/events/add")
    @FormUrlEncoded
    Call<ResponseBody> addEvent(@Field("title") String title,
                                @Field("content") String content,
                                @Field("userId") Integer userId);

    @POST("/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("login") String login,
                             @Field("password") String password);

    @POST("/registration")
    @FormUrlEncoded
    Call<ResponseBody> registration(@Field("fullName") String fullName,
                                    @Field("login") String login,
                                    @Field("password") String password,
                                    @Field("level") Integer level);

    @POST("/add_task")
    @FormUrlEncoded
    Call<ResponseBody> addTask(@Field("content") String content,
                               @Field("checked") Boolean checked,
                               @Field("taskListId") Integer taskListId);
}
