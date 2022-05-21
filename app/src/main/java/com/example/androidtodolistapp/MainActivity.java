package com.example.androidtodolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidtodolistapp.Adapter.ToDoAdapter;
import com.example.androidtodolistapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
         private RecyclerView taksRecyclerView;
         private ToDoAdapter taskAdapter;
         private List<ToDoModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        taskList = new ArrayList<>();

        taksRecyclerView=findViewById(R.id.tasksRecyclerView);
        taksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter= new ToDoAdapter(this);
        taksRecyclerView.setAdapter(taskAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("This is a Test Task");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        taskAdapter.setTask(taskList);

    }
}