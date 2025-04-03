package com.example.todolist.model;

import com.google.gson.annotations.SerializedName;

public class Todo {
    @SerializedName("id")
    private int id;

    @SerializedName("todo")
    private String todo;

    @SerializedName("status")
    private String status = "in progress";

    @SerializedName("is_complete")
    private int is_complete;

    // getter
    public int getId() {
        return id;
    }

    public String getList() {
        return todo;
    }

    public String getStatus() {
        return status;
    }

    public int getComplete() {
        return is_complete;
    }

    public boolean isCompleted() {
        if (is_complete == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setCompleted(int is_complete) {
        this.is_complete = is_complete;
    }

    @Override
    public String toString() {
        return "Todo{id : " + id + " todo : " + todo + " status : " + status + " complete : " + is_complete + "}";
    }
}
