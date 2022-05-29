package com.example.androidtodolistapp;

import com.example.androidtodolistapp.Model.TaskData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOTask {

    public DatabaseReference databaseReference ;

    public DAOTask(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(TaskData.class.getSimpleName());
    }

}
