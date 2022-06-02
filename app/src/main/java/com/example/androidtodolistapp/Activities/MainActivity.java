package com.example.androidtodolistapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidtodolistapp.Adapter.ToDoAdapter;
import com.example.androidtodolistapp.DAOTask;
import com.example.androidtodolistapp.Fragments.AddNewTask;
import com.example.androidtodolistapp.Interfaces.DialogCloseListener;
import com.example.androidtodolistapp.Model.TaskData;
import com.example.androidtodolistapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private FloatingActionButton fab;
    DAOTask dao;
    ToDoAdapter tasksAdapter;
    ArrayList<TaskData> tasksList = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        viewsInflate();
        dao = new DAOTask();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

       tasksList.clear();
//        String id = firebaseUser.getUid();
          String id = firebaseAuth.getUid();

//       String id = firebaseAuth.getCurrentUser().getUid();
       Task<DataSnapshot> taskData =  dao.databaseReference.child(id).get();
       taskData.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DataSnapshot> taskData) {
               if(taskData.isSuccessful()){
                   Iterable<DataSnapshot> data = taskData.getResult().getChildren();
                   for(DataSnapshot snapshot : data){
                       TaskData t = snapshot.getValue(TaskData.class);
                       tasksList.add(t);
                   }
                   tasksAdapter= new ToDoAdapter();
                   tasksAdapter.setTasks(tasksList);
                   tasksRecyclerView.setAdapter(tasksAdapter);
                   tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
               }else{
                   String error_message = taskData.getException().getMessage();
                   Toast.makeText(MainActivity.this, "Error"+error_message, Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){

        Collections.reverse(tasksList);
        Log.d("tag","revers : "+tasksList);
        tasksAdapter.setTasks(tasksList);
        tasksAdapter.notifyDataSetChanged();
        loadData();
    }

    public void viewsInflate(){
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        fab = findViewById(R.id.fab);

    }

}