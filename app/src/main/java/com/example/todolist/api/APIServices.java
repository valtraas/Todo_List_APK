package com.example.todolist.api;

import com.example.todolist.model.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServices {
    @GET("todo/get_todo.php")
    Call<List<Todo>> getAllTodo();

    @FormUrlEncoded
    @POST("todo/create.php")
    Call<Void> createTodo(
            @Field("todo") String todo,
            @Field("status") String status,
            @Field("is_complete") int isComplete
    );

    @FormUrlEncoded
    @POST("todo/updateTodoStatus.php")
    Call<Void> updateTodoStatus(
            @Field("id") int id,
            @Field("status") String status,
            @Field("is_complete") int isComplete
    );

    @FormUrlEncoded
    @POST("todo/delete.php")
    Call<Void> deleteTodo(
            @Field("id") int id
    );

}
