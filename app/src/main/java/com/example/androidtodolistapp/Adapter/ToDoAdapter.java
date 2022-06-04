package com.example.androidtodolistapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolistapp.Activities.MainActivity;
import com.example.androidtodolistapp.DAOTask;
import com.example.androidtodolistapp.Model.TaskData;
import com.example.androidtodolistapp.R;

//import com.google.android.gms.tasks.TaskData;


import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private ArrayList<TaskData> todoList;
    private MainActivity activity;
    DAOTask dao = new DAOTask();
    private OnItemClickListener listener;

    public ToDoAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaskData item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });

    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTasks(ArrayList<TaskData> taskList) {
        this.todoList = taskList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void deleteItem(int position) {
        TaskData item = todoList.get(position);
        dao.databaseReference.child(item.getId()).removeValue();
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        if (listener != null) listener.onItemClick(todoList.get(position), position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.todoCheckBox);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TaskData data, int positions);
    }
}
