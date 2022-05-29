package com.example.androidtodolistapp.Model;

public class TaskData {
    private int status;
    private String id, task;

    public TaskData(){}

    public TaskData(String id, String task) {
        this.id =id;
        this.task = task;
    }

    public TaskData(String id, int status, String task) {
        this.id =id;
        this.status = status;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "status=" + status +
                ", id='" + id + '\'' +
                ", task='" + task + '\'' +
                '}';
    }
}
