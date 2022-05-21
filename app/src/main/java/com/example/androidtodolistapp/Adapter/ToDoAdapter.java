package com.example.androidtodolistapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolistapp.MainActivity;
import com.example.androidtodolistapp.Model.ToDoModel;
import com.example.androidtodolistapp.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> toDoModelList;
    private MainActivity activity;

    public  ToDoAdapter(MainActivity activity){
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        ToDoModel item = toDoModelList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));

    }
    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTask(List<ToDoModel> toDoModelList){
        this.toDoModelList=toDoModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return toDoModelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task=itemView.findViewById(R.id.todoCheckBox);
        }
    }
}
