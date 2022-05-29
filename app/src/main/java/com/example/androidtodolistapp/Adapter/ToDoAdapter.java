package com.example.androidtodolistapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolistapp.DAOTask;
import com.example.androidtodolistapp.Model.TaskData;
import com.example.androidtodolistapp.R;

//import com.google.android.gms.tasks.TaskData;


import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private ArrayList<TaskData> todoList;

    public ToDoAdapter()
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        TaskData item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DAOTask dao = new DAOTask();
                if (isChecked) {
//                    String k = item.getId();
//                    String updated_task =  item.getTask();
//                    int updated_status =  1;
//                    HashMap<>
//                    dao.updateTask(k,)

                } else {
                }
            }
        });

    }
    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(ArrayList<TaskData> taskList){
        this.todoList= taskList;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task=itemView.findViewById(R.id.todoCheckBox);
        }
    }
}
