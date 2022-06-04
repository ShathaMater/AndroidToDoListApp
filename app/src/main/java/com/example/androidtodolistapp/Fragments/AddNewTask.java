package com.example.androidtodolistapp.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.androidtodolistapp.Adapter.ToDoAdapter;
import com.example.androidtodolistapp.DAOTask;
import com.example.androidtodolistapp.Interfaces.DialogCloseListener;
import com.example.androidtodolistapp.Model.TaskData;
import com.example.androidtodolistapp.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;
    public static List<TaskData> tasksList = new ArrayList<>();
    boolean isUpdate = false;

    private DAOTask dao;
    private FirebaseAuth firebaseAuth;
    private TaskData updatTask = null;


    public static AddNewTask newInstance(TaskData updatTask) {
        AddNewTask fragment = new AddNewTask();
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", updatTask);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = view.findViewById(R.id.newTaskText);
        newTaskSaveButton = view.findViewById(R.id.newTaskButton);

//
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            updatTask = (TaskData) bundle.getSerializable("task");
            if (updatTask != null) {
                newTaskText.setText(updatTask.getTask());
                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            }
        }
//

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addorupdateTask();
            }


            private void addorupdateTask() {
                if (finalIsUpdate && updatTask != null) {
                    updateTask(updatTask);
                } else {
                    addTask();
                }
            }

            private void updateTask(TaskData taskData) {
                dao = new DAOTask();
                String text = newTaskText.getText().toString();
                String id = firebaseAuth.getCurrentUser().getUid();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("update Task ", text);
                dao.databaseReference.child("Tasks").child(id)
                        .child(taskData.getId())
                        .updateChildren(hashMap).
                        addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        updatTask = null;
                                        //btn add
                                        Toast.makeText(getContext(), "Taskdata has been updated", Toast.LENGTH_LONG).show();
                                    } else {
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(getContext(), "Taskdata hasnt updated updated" + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
            }
        });
    }
    /*
     *  app
     *
     *    Tasks
     *       uId
     *         auto id (push key)
     *               object task
     *
     *     Users
     *       Uid
     *           object User bean email
     * هي الفكرة ي عبير هان
     *
     *
     * */

    public void addTask() {

        dao = new DAOTask();
        String text = newTaskText.getText().toString();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String mKey = dao.databaseReference.child("Tasks").child(userId).push().getKey();
        TaskData taskData = new TaskData(mKey, text);

        dao.databaseReference.child("Tasks").child(userId).push().setValue(taskData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "TaskData hase been added", Toast.LENGTH_LONG).show();


            } else {
                String errorMessage = task.getException().getMessage();
                Toast.makeText(getContext(), "TaskData hase been failed" + errorMessage, Toast.LENGTH_LONG).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

            }
        });
        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener)
            ((DialogCloseListener) activity).handleDialogClose(dialog);
    }

}

