package com.example.iem.network.models;

import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("id")
    private Integer id;
    @SerializedName("content")
    private String content;
    @SerializedName("checked")
    private Boolean checked;
    @SerializedName("taskListId")
    private Integer taskListId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }
}
